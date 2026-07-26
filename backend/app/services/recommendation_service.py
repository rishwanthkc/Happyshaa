import time
import logging
from typing import List, Dict, Any
from app.repositories.mood_log import MoodLogRepository

logger = logging.getLogger(__name__)

class RecommendationService:
    def __init__(self):
        self.mood_repo = MoodLogRepository()

    async def get_recommendations(self, uid: str) -> List[Dict[str, Any]]:
        """
        Calculates and selects 3 personalized wellness recommendation cards.
        """
        # Heuristics rules matching user mood
        recent_moods = await self.mood_repo.get_mood_logs(uid, limit=1)
        
        primary_emotion = "Confidence"
        stress_level = 0.20
        if recent_moods:
            primary_emotion = recent_moods[0].get("primary_emotion", "Confidence")
            stress_level = recent_moods[0].get("stress_level", 0.20)

        recommendations = []

        # Card 1: Music recommendation based on stress
        if stress_level > 0.50 or primary_emotion in ["Stress", "Anxiety", "Anger"]:
            recommendations.append({
                "id": "rec_music_calm",
                "title": "Unwind with Music",
                "description": "Your stress indicators are slightly elevated. Listen to 'Soft Rainfall' to calm your mind.",
                "activity_type": "MUSIC",
                "target_route": "music_player/nature_rain",
                "difficulty": "Easy",
                "coins_reward": 5
            })
        else:
            recommendations.append({
                "id": "rec_music_lofi",
                "title": "Relaxing Lofi",
                "description": "Keep up the good energy! Tune in to 'Midnight Study' to flow with focus.",
                "activity_type": "MUSIC",
                "target_route": "music_player/lofi_focus",
                "difficulty": "Easy",
                "coins_reward": 5
            })

        # Card 2: Interactive distraction game
        if primary_emotion in ["Sadness", "Anxiety", "Stress"]:
            recommendations.append({
                "id": "rec_game_bubble",
                "title": "Stress Bubble Pop",
                "description": "Pop some soothing bubbles on screen to release physical tension and anxiety.",
                "activity_type": "GAME",
                "target_route": "game_center/bubblepop",
                "difficulty": "Easy",
                "coins_reward": 10
            })
        else:
            recommendations.append({
                "id": "rec_game_word",
                "title": "Gratitude Word unscramble",
                "description": "Train your mind to locate positive adjectives and exercise vocabulary.",
                "activity_type": "GAME",
                "target_route": "game_center/wordpuzzle",
                "difficulty": "Medium",
                "coins_reward": 10
            })

        # Card 3: Deep breathing or Social check-in
        if stress_level > 0.60:
            recommendations.append({
                "id": "rec_breathing_calm",
                "title": "4-7-8 Box Breathing",
                "description": "Release physical anxiety immediately. Perform a structured box breathing session.",
                "activity_type": "BREATHING",
                "target_route": "breathing_guide",
                "difficulty": "Easy",
                "coins_reward": 15
            })
        else:
            recommendations.append({
                "id": "rec_journal_write",
                "title": "Reflective Journaling",
                "description": "Write down one thing that made you smile today to cultivate gratitude.",
                "activity_type": "JOURNAL",
                "target_route": "journal/new",
                "difficulty": "Medium",
                "coins_reward": 15
            })

        return recommendations
