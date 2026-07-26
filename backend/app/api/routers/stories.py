import time
import logging
import asyncio
from fastapi import APIRouter, Depends, HTTPException, status
from fastapi.responses import StreamingResponse
from app.middleware.auth_middleware import get_current_user
from app.models.schemas import UserProfile, StoryGenerateRequest, StoryResponse, StoryFavoriteRequest
from app.repositories.story_repo import StoryRepository
from app.services.story_generator import StoryGeneratorService

logger = logging.getLogger(__name__)
router = APIRouter()

story_repo = StoryRepository()
generator_service = StoryGeneratorService()

async def commit_story_to_db(uid: str, category: str, length: str, full_text: str):
    """
    Background worker that extracts title and saves the completed story to Firestore.
    """
    try:
        lines = [line.strip() for line in full_text.split("\n") if line.strip()]
        title = "Calming Journey"
        body_content = full_text
        
        # Try to parse 'Title: [Title]'
        for line in lines[:2]:
            if line.lower().startswith("title:"):
                title = line[6:].strip()
                break

        story_data = {
            "category": category,
            "length": length,
            "title": title,
            "content": body_content,
            "timestamp": time.time(),
            "is_favorite": False
        }
        story_id = await story_repo.save_story(uid, story_data)
        logger.info(f"Story {story_id} saved to database for user {uid}")
    except Exception as e:
        logger.error(f"Error saving story to database in background: {e}")

@router.post("/generate")
async def generate_story(
    request: StoryGenerateRequest,
    current_user: UserProfile = Depends(get_current_user)
) -> StreamingResponse:
    """
    Authenticated story generator endpoint. Returns a streaming text response,
    and commits the finished story log to Firestore in the background.
    """
    try:
        uid = current_user.uid
        category = request.category
        length = request.length

        stream = generator_service.generate_story_stream(category, length)

        async def response_stream_wrapper():
            full_story = ""
            try:
                async for token in stream:
                    full_story += token
                    yield token
            finally:
                if full_story.strip():
                    asyncio.create_task(commit_story_to_db(uid, category, length, full_story.strip()))

        return StreamingResponse(response_stream_wrapper(), media_type="text/plain")
    except Exception as e:
        logger.error(f"Error initializing story stream: {e}")
        raise HTTPException(
            status_code=status.HTTP_500_INTERNAL_SERVER_ERROR,
            detail=f"Failed to generate story stream: {str(e)}"
        )

@router.get("/history", response_model=list[StoryResponse])
async def get_stories_history(
    current_user: UserProfile = Depends(get_current_user)
) -> list[StoryResponse]:
    """
    Retrieves all generated stories for the logged-in user.
    """
    try:
        records = await story_repo.get_stories(current_user.uid)
        return [StoryResponse(**r) for r in records]
    except Exception as e:
        logger.error(f"Error fetching stories history: {e}")
        raise HTTPException(
            status_code=status.HTTP_500_INTERNAL_SERVER_ERROR,
            detail=f"Failed to fetch story history logs: {str(e)}"
        )

@router.post("/favorite", response_model=StoryResponse)
async def favorite_story(
    request: StoryFavoriteRequest,
    current_user: UserProfile = Depends(get_current_user)
) -> StoryResponse:
    """
    Toggles the favorite flag of a story document.
    """
    try:
        updated = await story_repo.toggle_favorite(current_user.uid, request.story_id)
        return StoryResponse(**updated)
    except KeyError:
        raise HTTPException(status_code=404, detail="Story record not found")
    except PermissionError:
        raise HTTPException(status_code=403, detail="Forbidden")
    except Exception as e:
        logger.error(f"Error toggling favorite on story: {e}")
        raise HTTPException(
            status_code=status.HTTP_500_INTERNAL_SERVER_ERROR,
            detail=f"Failed to favorite story: {str(e)}"
        )
