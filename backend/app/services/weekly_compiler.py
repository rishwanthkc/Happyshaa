import json
import logging
import google.generativeai as genai
from app.core.config import settings

logger = logging.getLogger(__name__)

if settings.GEMINI_API_KEY:
    genai.configure(api_key=settings.GEMINI_API_KEY)

class WeeklyCompilerService:
    def __init__(self):
        self.enabled = bool(settings.GEMINI_API_KEY)
        if self.enabled:
            self.model = genai.GenerativeModel("gemini-1.5-flash")

    async def compile_report(self, uid: str, journals: list, moods: list) -> dict:
        """
        Synthesizes 7-day journal entries and mood logs into a weekly progress report.
        """
        if not self.enabled or (not journals and not moods):
            return self._get_fallback_report(journals, moods)

        # Format details for prompt context
        journals_str = "\n".join([f"- Title: {j.get('title')}, Emotion: {j.get('detected_emotion')}, Text: {j.get('content')}" for j in journals])
        moods_str = "\n".join([f"- Emotion: {m.get('primary_emotion')}, Stress Level: {m.get('stress_level')}, Note: {m.get('note')}" for m in moods])

        prompt = f"""
        Summarize the user's weekly emotional state based on:
        
        [WEEKLY JOURNAL LOGS]
        {journals_str}
        
        [WEEKLY MOOD LOGS]
        {moods_str}

        Provide a structured JSON output containing:
        1. "dominant_mood": String representing the primary weekly mood.
        2. "average_stress_level": Float between 0.0 and 1.0.
        3. "gratitude_summary": A warm paragraph summarizing elements of gratitude they expressed.
        4. "identified_triggers": A list of up to 3 stress-related triggers identified.
        5. "self_care_plan": A list of 3 actionable mindfulness exercises or coping tips (e.g. "Practice 4-7-8 breathing on Tuesday").

        Format the output strictly as a valid JSON object. Do not include any markdown backticks, explanations, or wrappers.
        """

        try:
            response = self.model.generate_content(prompt)
            clean_text = response.text.replace("```json", "").replace("```", "").strip()
            result = json.loads(clean_text)
            
            # Ensure required keys exist
            required_keys = ["dominant_mood", "average_stress_level", "gratitude_summary", "identified_triggers", "self_care_plan"]
            for key in required_keys:
                if key not in result:
                    raise KeyError(f"Missing key: {key}")
            return result
        except Exception as e:
            logger.error(f"Error compiling weekly report with Gemini: {e}")
            return self._get_fallback_report(journals, moods)

    def _get_fallback_report(self, journals: list, moods: list) -> dict:
        # Calculate a basic stress score dynamically based on moods
        stress_levels = [m.get("stress_level", 0.3) for m in moods]
        avg_stress = sum(stress_levels) / len(stress_levels) if stress_levels else 0.25

        # Heuristic dominant mood
        dominant = "Happiness"
        if moods:
            emotions = [m.get("primary_emotion") for m in moods]
            dominant = max(set(emotions), key=emotions.count)

        return {
            "dominant_mood": dominant,
            "average_stress_level": avg_stress,
            "gratitude_summary": "Your weekly logs show active efforts in self-care. Even on busy days, you successfully checked in on your health.",
            "identified_triggers": ["Daily workload pressure" if avg_stress > 0.4 else "Routine tiredness"],
            "self_care_plan": [
                "Practice 4-7-8 box breathing for 5 minutes daily",
                "Play Gratitude Word unscrambles in Game Center",
                "Contact a support circle friend for a quick check-in"
            ]
        }
