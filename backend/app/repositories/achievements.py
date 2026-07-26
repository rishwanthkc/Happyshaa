import time
from typing import List, Dict, Any
from app.core.firebase import get_db

_mock_achievements: List[Dict[str, Any]] = []

class AchievementsRepository:
    def __init__(self):
        self.db = get_db()
        self.collection_name = "achievements"

    async def unlock_achievement(self, uid: str, achievement_type: str) -> str:
        """
        Locks/saves an unlocked achievement milestone.
        """
        data = {
            "uid": uid,
            "achievement_type": achievement_type,
            "timestamp": time.time()
        }
        
        if self.db is None:
            data["achievement_id"] = f"mock_ach_{len(_mock_achievements)}"
            _mock_achievements.append(data)
            return data["achievement_id"]

        doc_ref = self.db.collection(self.collection_name).document()
        data["achievement_id"] = doc_ref.id
        doc_ref.set(data)
        return doc_ref.id

    async def get_achievements(self, uid: str) -> List[Dict[str, Any]]:
        """
        Gets unlocked milestones.
        """
        if self.db is None:
            return [a for a in _mock_achievements if a.get("uid") == uid]

        query = self.db.collection(self.collection_name).where("uid", "==", uid)
        docs = query.stream()
        return [doc.to_dict() for doc in docs]
