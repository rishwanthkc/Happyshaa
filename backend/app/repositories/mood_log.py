from typing import List, Dict, Any
from google.cloud.firestore import Query
from app.core.firebase import get_db

# In-memory storage fallback for local testing
_mock_mood_logs: List[Dict[str, Any]] = []

class MoodLogRepository:
    def __init__(self):
        self.db = get_db()
        self.collection_name = "mood_logs"

    async def create_mood_log(self, data: Dict[str, Any]) -> str:
        """
        Commits a mood log document to Firestore.
        """
        if self.db is None:
            _mock_mood_logs.append(data)
            return data.get("log_id", "mock_log_id")
            
        doc_ref = self.db.collection(self.collection_name).document()
        data["log_id"] = doc_ref.id
        doc_ref.set(data)
        return doc_ref.id

    async def get_mood_logs(self, uid: str, limit: int = 50) -> List[Dict[str, Any]]:
        """
        Fetches mood log documents for a specific user.
        """
        if self.db is None:
            # Filter and sort in memory for testing
            user_logs = [log for log in _mock_mood_logs if log.get("uid") == uid]
            user_logs.sort(key=lambda x: x.get("timestamp", 0), reverse=True)
            return user_logs[:limit]

        query = (
            self.db.collection(self.collection_name)
            .where("uid", "==", uid)
            .order_by("timestamp", direction=Query.DESCENDING)
            .limit(limit)
        )
        docs = query.stream()
        return [doc.to_dict() for doc in docs]
