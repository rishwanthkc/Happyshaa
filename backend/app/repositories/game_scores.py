import time
from typing import List, Dict, Any
from google.cloud.firestore import Query
from app.core.firebase import get_db

_mock_game_scores: List[Dict[str, Any]] = []

class GameScoresRepository:
    def __init__(self):
        self.db = get_db()
        self.collection_name = "game_scores"

    async def add_score(self, uid: str, game_id: str, score: int) -> str:
        """
        Commits a game score document to Firestore.
        """
        data = {
            "uid": uid,
            "game_id": game_id,
            "score": score,
            "timestamp": time.time()
        }
        
        if self.db is None:
            data["score_id"] = data.get("score_id", f"mock_score_{len(_mock_game_scores)}")
            _mock_game_scores.append(data)
            return data["score_id"]

        doc_ref = self.db.collection(self.collection_name).document()
        data["score_id"] = doc_ref.id
        doc_ref.set(data)
        return doc_ref.id

    async def get_scores(self, uid: str, limit: int = 50) -> List[Dict[str, Any]]:
        """
        Retrieves play history and high-scores for a specific user.
        """
        if self.db is None:
            user_scores = [s for s in _mock_game_scores if s.get("uid") == uid]
            user_scores.sort(key=lambda x: x.get("timestamp", 0), reverse=True)
            return user_scores[:limit]

        query = (
            self.db.collection(self.collection_name)
            .where("uid", "==", uid)
            .order_by("timestamp", direction=Query.DESCENDING)
            .limit(limit)
        )
        docs = query.stream()
        return [doc.to_dict() for doc in docs]
