import logging
import asyncio
import google.generativeai as genai
from app.core.config import settings

logger = logging.getLogger(__name__)

# Configure Gemini SDK
if settings.GEMINI_API_KEY:
    genai.configure(api_key=settings.GEMINI_API_KEY)

class AICompanionService:
    def __init__(self):
        self.enabled = bool(settings.GEMINI_API_KEY)
        if self.enabled:
            self.model = genai.GenerativeModel("gemini-1.5-flash")

    async def generate_response_stream(
        self,
        uid: str,
        message: str,
        current_mood: str,
        detected_emotion: str,
        memory_context: str,
        chat_history: list
    ):
        """
        Generates an empathetic streaming response using Gemini 1.5 Flash.
        Runs the stream reader inside a thread pool to ensure async scalability.
        """
        if not self.enabled:
            # Yield dummy fallback responses in stream chunks for offline tests
            mock_resp = f"I hear you. Dealing with {detected_emotion.lower()} can feel overwhelming, but you're not alone. I'm here to listen."
            for word in mock_resp.split(" "):
                yield word + " "
                await asyncio.sleep(0.08)
            return

        # Format past chat logs
        chat_history_str = ""
        for turn in chat_history:
            sender = "User" if turn.get("sender") == "user" else "Aura"
            chat_history_str += f"{sender}: {turn.get('content')}\n"

        system_prompt = f"""
        You are Aura, a warm, caring, and deeply empathetic AI wellness companion. You behave like a wise, compassionate friend. 
        Your goal is to support the user through emotional ups and downs.

        Conversational Guidelines:
        - Act as a supportive friend. Do not give clinical diagnoses or medical advice.
        - Validate feelings before responding. (e.g. "I can hear how exhausting that must have been.")
        - Use reflective listening. Prompt self-reflection (CBT patterns).
        - Keep replies brief (under 3-4 sentences in chat mode) to avoid overwhelming the user.

        [CONTEXTUAL_METADATA]
        Current Mood: {current_mood}
        Detected Emotion: {detected_emotion}
        Relevant Memories:
        {memory_context}

        [CONVERSATION_HISTORY]
        {chat_history_str}

        [CURRENT_USER_INPUT]
        User: {message}

        Aura:
        """

        try:
            loop = asyncio.get_running_loop()
            
            # Execute synchronous generator query inside executor to prevent thread blockage
            def fetch_stream():
                return self.model.generate_content(system_prompt, stream=True)
                
            response = await loop.run_in_executor(None, fetch_stream)
            
            for chunk in response:
                # Handle empty text chunks on safety blocks
                text = chunk.text
                if text:
                    yield text
                    
        except Exception as e:
            logger.error(f"Error during Gemini streaming: {e}")
            yield "I'm having a little trouble connecting right now, but I'm still right here beside you. Take a deep breath."
