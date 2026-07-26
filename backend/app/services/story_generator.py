import logging
import asyncio
import google.generativeai as genai
from app.core.config import settings

logger = logging.getLogger(__name__)

if settings.GEMINI_API_KEY:
    genai.configure(api_key=settings.GEMINI_API_KEY)

class StoryGeneratorService:
    def __init__(self):
        self.enabled = bool(settings.GEMINI_API_KEY)
        if self.enabled:
            self.model = genai.GenerativeModel("gemini-1.5-flash")

    async def generate_story_stream(self, category: str, length: str):
        """
        Generates a calming, premium story based on category and length using Gemini 1.5 Flash.
        Streams paragraphs and lines chunk-by-chunk.
        """
        if not self.enabled:
            # Fallback mock stories for offline testing
            mock_title = f"The Meadow of {category}"
            mock_body = (
                f"Title: {mock_title}\n\n"
                f"Imagine a beautiful, expansive meadow stretching out under a soft twilight sky. "
                f"A gentle breeze brushes across the green grass, carrying with it a profound feeling of {category.lower()}. "
                f"Every worry or stress you carried during the day begins to fade, dissolving into the quiet evening air.\n\n"
                f"As you walk slowly along the winding path, you feel grounded, safe, and deeply peaceful. "
                f"You take a deep, slow breath in, and let it go. The calming atmosphere reminds you that you are "
                f"exactly where you need to be. You feel a warm sense of motivation and self-alignment returning.\n\n"
                f"Now, the stars are beginning to sparkle overhead like tiny lanterns. "
                f"You lie down on the soft grass, feeling completely supported by the earth. "
                f"You close your eyes and let the peaceful whispers of the meadow soothe you into a deep, restful state."
            )
            for word in mock_body.split(" "):
                yield word + " "
                await asyncio.sleep(0.04)
            return

        prompt = f"""
        Generate a calming, soothing, and premium story for wellness, sleep, and mental health.
        
        [STORY_METADATA]
        Category: {category}
        Length requested: {length}

        Length details:
        - "Short": A relaxing 3-paragraph story (approx. 200 words).
        - "Medium": A detailed 5-paragraph story (approx. 400 words).
        - "Long": An immersive 8-paragraph journey story (approx. 700 words).

        Storytelling Guidelines:
        - Style: Peaceful, imagery-rich, supportive, and mindfulness-focused.
        - Structure: Give the story a title at the very beginning starting with 'Title: [Title]'.
        - Tone: Grounding, gentle, and empathetic.
        """

        try:
            loop = asyncio.get_running_loop()
            
            def fetch_stream():
                return self.model.generate_content(prompt, stream=True)
                
            response = await loop.run_in_executor(None, fetch_stream)
            
            for chunk in response:
                text = chunk.text
                if text:
                    yield text
        except Exception as e:
            logger.error(f"Error during Gemini story streaming: {e}")
            yield "Title: Whispers of Peace\n\nJust take a deep breath. Focus on the gentle rise and fall of your chest. The night is quiet, and you are safe. Let go of all thoughts, and welcome peace."
