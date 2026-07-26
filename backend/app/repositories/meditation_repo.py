from typing import List, Dict, Any
from google.cloud.firestore import Query
from app.core.firebase import get_db

_mock_meditation_sessions: List[Dict[str, Any]] = []

class MeditationRepository:
    def __init__(self):
        self.db = get_db()
        self.collection_name = "meditation_sessions"

    async def save_session(self, uid: str, data: Dict[str, Any]) -> str:
        data["uid"] = uid
        if self.db is None:
            data["session_id"] = data.get("session_id", f"mock_session_{len(_mock_meditation_sessions)}")
            _mock_meditation_sessions.append(data)
            return data["session_id"]

        doc_ref = self.db.collection(self.collection_name).document()
        data["session_id"] = doc_ref.id
        doc_ref.set(data)
        return doc_ref.id

    async def get_sessions(self, uid: str, limit: int = 100) -> List[Dict[str, Any]]:
        if self.db is None:
            user_sessions = [s for s in _mock_meditation_sessions if s.get("uid") == uid]
            user_sessions.sort(key=lambda x: x.get("timestamp", 0), reverse=True)
            return user_sessions[:limit]

        query = (
            self.db.collection(self.collection_name)
            .where("uid", "==", uid)
            .order_by("timestamp", direction=Query.DESCENDING)
            .limit(limit)
        )
        docs = query.stream()
        return [doc.to_dict() for doc in docs]
