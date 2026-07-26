import json
import logging
import google.generativeai as genai
from app.core.config import settings

logger = logging.getLogger(__name__)

# Configure Gemini SDK
if settings.GEMINI_API_KEY:
    genai.configure(api_key=settings.GEMINI_API_KEY)
else:
    logger.warning("GEMINI_API_KEY is not configured in settings. Emotion Detection will fall back to mock classification.")

class EmotionDetectorService:
    def __init__(self):
        self.enabled = bool(settings.GEMINI_API_KEY)
        if self.enabled:
            self.model = genai.GenerativeModel("gemini-1.5-flash")

    async def analyze_text(self, text: str) -> dict:
        """
        Analyzes the input text using Gemini 1.5 Flash to classify secondary emotion metrics
        and returns a structured emotion profile payload.
        """
        if not self.enabled or not text.strip():
            logger.info("Emotion Detection: Using fallback mock analysis.")
            return self._get_fallback_analysis(text)

        prompt = f"""
        You are an advanced empathetic emotion detection model. Analyze the following user text:
        "{text}"

        Evaluate and return a JSON object with:
        1. "primary_emotion": The dominant emotional state. Select from: Stress, Anxiety, Sadness, Anger, Happiness, Confidence.
        2. "confidence_score": Float between 0.0 and 1.0 representing classification confidence.
        3. "stress_level": Float between 0.0 and 1.0 representing stress intensity.
        4. "anxiety_level": Float between 0.0 and 1.0 representing anxiety intensity.
        5. "sadness_level": Float between 0.0 and 1.0 representing sadness intensity.
        6. "anger_level": Float between 0.0 and 1.0 representing anger intensity.
        7. "happiness_level": Float between 0.0 and 1.0 representing happiness intensity.
        8. "confidence_level": Float between 0.0 and 1.0 representing self-assurance intensity.
        9. "suggested_activities": A list of 2 or 3 specific wellness tasks matching this profile (e.g. "Do 4-7-8 box breathing", "Play the Bubble Pop game", "Write a gratitude note").

        Format the response strictly as a valid JSON object. Do not include any markdown backticks, explanations, or wrappers.
        """

        try:
            response = self.model.generate_content(prompt)
            clean_text = response.text.replace("```json", "").replace("```", "").strip()
            result = json.loads(clean_text)
            
            # Ensure required keys exist
            required_keys = [
                "primary_emotion", "confidence_score", "stress_level",
                "anxiety_level", "sadness_level", "anger_level",
                "happiness_level", "confidence_level", "suggested_activities"
            ]
            for key in required_keys:
                if key not in result:
                    raise KeyError(f"Missing key: {key}")
                    
            logger.info(f"Emotion Detection succeeded: Dominant = {result['primary_emotion']}")
            return result
            
        except Exception as e:
            logger.error(f"Failed to analyze emotion with Gemini API: {e}. Falling back to mock values.")
            return self._get_fallback_analysis(text)

    def _get_fallback_analysis(self, text: str) -> dict:
        """
        Static offline heuristic fallback analyzer.
        """
        lower_text = text.lower()
        primary = "Confidence"
        suggested = ["Write a gratitude note", "Listen to upbeat Lo-Fi"]
        
        # Simple keywords mapping
        if any(w in lower_text for w in ["stressed", "overwhelmed", "tired", "busy", "pressure"]):
            primary = "Stress"
            suggested = ["Do 4-7-8 box breathing", "Listen to binaural calm beats"]
        elif any(w in lower_text for w in ["anxious", "scared", "fear", "worry", "panic", "nervous"]):
            primary = "Anxiety"
            suggested = ["Do box breathing guide", "Play Bubble Pop game"]
        elif any(w in lower_text for w in ["sad", "depressed", "lonely", "hurt", "cry", "gloomy"]):
            primary = "Sadness"
            suggested = ["Write a gratitude note", "Contact a supportive friend"]
        elif any(w in lower_text for w in ["angry", "mad", "hate", "furious", "annoyed", "pissed"]):
            primary = "Anger"
            suggested = ["Do Box Breathing", "Play Gratitude Tree game"]
        elif any(w in lower_text for w in ["happy", "glad", "great", "joy", "excited", "wonderful"]):
            primary = "Happiness"
            suggested = ["Share joy with a buddy", "Record a wellness milestone"]

        return {
            "primary_emotion": primary,
            "confidence_score": 0.85,
            "stress_level": 0.70 if primary == "Stress" else (0.40 if primary == "Anxiety" else 0.15),
            "anxiety_level": 0.75 if primary == "Anxiety" else (0.45 if primary == "Stress" else 0.20),
            "sadness_level": 0.80 if primary == "Sadness" else 0.10,
            "anger_level": 0.80 if primary == "Anger" else 0.05,
            "happiness_level": 0.90 if primary == "Happiness" else 0.20,
            "confidence_level": 0.85 if primary == "Confidence" else 0.30,
            "suggested_activities": suggested
        }
