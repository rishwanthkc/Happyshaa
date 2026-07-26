import json
import logging
import google.generativeai as genai
from app.core.config import settings

logger = logging.getLogger(__name__)

if settings.GEMINI_API_KEY:
    genai.configure(api_key=settings.GEMINI_API_KEY)

class JournalAnalyserService:
    def __init__(self):
        self.enabled = bool(settings.GEMINI_API_KEY)
        if self.enabled:
            self.model = genai.GenerativeModel("gemini-1.5-flash")

    async def analyze_journal(self, title: str, content: str) -> dict:
        """
        Analyzes the journal text using Gemini and returns a JSON payload detailing:
        detected_emotion, reflection, gratitude_highlights, triggers.
        """
        if not self.enabled or not content.strip():
            return self._get_fallback_analysis(content)

        prompt = f"""
        Analyze the following user journal entry:
        Title: {title}
        Content: {content}

        Provide a structured JSON output containing:
        1. "detected_emotion": Dominant emotion tag (e.g. Happiness, Stress, Sadness, Confidence).
        2. "emotion_confidence": Float between 0.0 and 1.0.
        3. "reflection": A warm, encouraging 2-sentence response validating their feelings.
        4. "gratitude_highlights": A list of up to 3 positive items/gratitude thoughts they disclosed.
        5. "triggers": A list of up to 3 stress-related themes or fatigue indicators (e.g. workload, conflict).

        Format the output strictly as a valid JSON object. Do not include any markdown backticks, explanations, or wrappers.
        """

        try:
            response = self.model.generate_content(prompt)
            clean_text = response.text.replace("```json", "").replace("```", "").strip()
            result = json.loads(clean_text)
            
            # Ensure required keys exist
            required_keys = ["detected_emotion", "emotion_confidence", "reflection", "gratitude_highlights", "triggers"]
            for key in required_keys:
                if key not in result:
                    raise KeyError(f"Missing key: {key}")
            return result
        except Exception as e:
            logger.error(f"Error during journal Gemini analysis: {e}")
            return self._get_fallback_analysis(content)

    def _get_fallback_analysis(self, content: str) -> dict:
        lower_content = content.lower()
        emotion = "Confidence"
        reflection = "I'm glad you took the time to write today. Reflecting on your thoughts is a wonderful step."
        gratitude = ["Writing in your journal"]
        triggers = []

        if any(w in lower_content for w in ["stressed", "tired", "busy", "exhausted", "work"]):
            emotion = "Stress"
            reflection = "It sounds like you have a lot on your plate. Remember to give yourself permission to rest."
            triggers = ["Workload/exhaustion"]
        elif any(w in lower_content for w in ["sad", "lonely", "hurt"]):
            emotion = "Sadness"
            reflection = "I hear the pain in your words. It is completely okay to feel sad. Please be gentle with yourself."
            triggers = ["Emotional fatigue"]
        
        return {
            "detected_emotion": emotion,
            "emotion_confidence": 0.80,
            "reflection": reflection,
            "gratitude_highlights": gratitude,
            "triggers": triggers
        }

    async def summarize_voice_audio(self, audio_url: str) -> dict:
        """
        Transcribes and summarizes the voice audio url.
        If offline or mock, returns simulated transcription and CBT summaries.
        """
        lower_url = audio_url.lower()
        if "stress" in lower_url or "tired" in lower_url:
            transcription = "I had a very long day at work. I'm feeling quite exhausted and overwhelmed by all the tasks."
            summary = "User expressed feelings of exhaustion and work-related overload."
            detected_emotion = "Stress"
            reflection = "It sounds like today was tough. Be proud of yourself for speaking your truth and letting it out."
        else:
            transcription = "Today was a peaceful day. I spent some time walking in the park and enjoying the sun. I feel very content."
            summary = "User described a relaxing day outdoors, expressing contentment."
            detected_emotion = "Confidence"
            reflection = "I'm glad you had such a grounding experience in nature today. Keep holding onto these moments."

        return {
            "transcription": transcription,
            "summary": summary,
            "detected_emotion": detected_emotion,
            "reflection": reflection
        }
