import time
import logging
import asyncio
from fastapi import APIRouter, Depends, HTTPException, status
from fastapi.responses import StreamingResponse
from app.middleware.auth_middleware import get_current_user
from app.models.schemas import UserProfile, ChatMessageRequest, ChatHistoryResponse, ChatHistoryMessage
from app.repositories.chat_history import ChatHistoryRepository
from app.services.ai_companion import AICompanionService
from app.services.memory_manager import MemoryManagerService
from app.services.emotion_detector import EmotionDetectorService

logger = logging.getLogger(__name__)
router = APIRouter()

chat_repo = ChatHistoryRepository()
companion_service = AICompanionService()
memory_service = MemoryManagerService()
emotion_service = EmotionDetectorService()

async def save_chat_pair(uid: str, user_content: str, companion_content: str):
    """
    Background worker that saves chat pairs in the database.
    """
    try:
        user_msg = {
            "uid": uid,
            "timestamp": time.time() - 1, # Offset slightly for chronology
            "sender": "user",
            "content": user_content
        }
        companion_msg = {
            "uid": uid,
            "timestamp": time.time(),
            "sender": "companion",
            "content": companion_content
        }
        await chat_repo.add_message(user_msg)
        await chat_repo.add_message(companion_msg)
        logger.info(f"Chat pair saved to Firestore for user {uid}")
        
        # Check if we should trigger a background task to summarize facts (Memory AI)
        # We can extract facts asynchronously if the message contains self-disclosures (e.g. "I got a dog")
        if "i have" in user_content.lower() or "i am" in user_content.lower() or "my name" in user_content.lower():
            logger.info("Chat discloses user facts, compiling memory node...")
            await memory_service.save_new_fact(uid, user_content)
            
    except Exception as e:
        logger.error(f"Error saving chat log pair in background: {e}")

@router.post("/respond")
async def chat_respond(
    request: ChatMessageRequest,
    current_user: UserProfile = Depends(get_current_user)
) -> StreamingResponse:
    """
    Authenticated chat endpoint that retrieves relevant memories and past logs,
    and returns a streamed HTTP response.
    """
    try:
        uid = current_user.uid
        user_message = request.message
        
        # 1. Fetch recent chat history (limit to last 15 messages for prompt context window efficiency)
        history = await chat_repo.get_messages(uid, limit=15)
        
        # 2. Retrieve relevant past memories (Semantic Memory AI)
        memory_context = await memory_service.retrieve_memories(uid, query=user_message)
        
        # 3. Classify emotion of user input (Emotion-aware replies)
        emotion_tag = request.current_mood or "Neutral"
        try:
            # Perform quick local classification to determine secondary emotions
            analysis = await emotion_service.analyze_text(user_message)
            emotion_tag = analysis.get("primary_emotion", emotion_tag)
        except Exception:
            pass # Gracefully fall back to request current_mood if Gemini analysis fails
            
        # 4. Compile stream generator
        stream = companion_service.generate_response_stream(
            uid=uid,
            message=user_message,
            current_mood=request.current_mood,
            detected_emotion=emotion_tag,
            memory_context=memory_context,
            chat_history=history
        )
        
        # 5. Wrap stream to capture output text and save log in background
        async def response_stream_wrapper():
            full_reply = ""
            try:
                async for token in stream:
                    full_reply += token
                    yield token
            finally:
                # Trigger Firestore logging in a non-blocking background task
                if full_reply.strip():
                    asyncio.create_task(save_chat_pair(uid, user_message, full_reply.strip()))

        return StreamingResponse(response_stream_wrapper(), media_type="text/plain")

    except Exception as e:
        logger.error(f"Error starting chat respond stream: {e}")
        raise HTTPException(
            status_code=status.HTTP_500_INTERNAL_SERVER_ERROR,
            detail=f"Failed to generate streaming companion response: {str(e)}"
        )

@router.get("/history", response_model=ChatHistoryResponse)
async def get_chat_history(
    current_user: UserProfile = Depends(get_current_user)
) -> ChatHistoryResponse:
    """
    Retrieves conversational logs for the authenticated user.
    """
    try:
        messages = await chat_repo.get_messages(current_user.uid, limit=50)
        parsed_history = [ChatHistoryMessage(**msg) for msg in messages]
        return ChatHistoryResponse(history=parsed_history)
        
    except Exception as e:
        logger.error(f"Error fetching chat history logs: {e}")
        raise HTTPException(
            status_code=status.HTTP_500_INTERNAL_SERVER_ERROR,
            detail=f"Failed to fetch conversation history: {str(e)}"
        )
