import time
from typing import List, Dict, Any
from google.cloud.firestore import Query
from app.core.firebase import get_db

_mock_music_history: List[Dict[str, Any]] = []

class MusicHistoryRepository:
    def __init__(self):
        self.db = get_db()
        self.collection_name = "music_history"

    async def add_history_entry(self, uid: str, song_id: str, duration_sec: int) -> str:
        """
        Adds a playback history entry.
        """
        data = {
            "uid": uid,
            "timestamp": time.time(),
            "song_id": song_id,
            "playback_duration_sec": duration_sec
        }
        
        if self.db is None:
            _mock_music_history.append(data)
            return "mock_hist_id"

        doc_ref = self.db.collection(self.collection_name).document()
        data["history_id"] = doc_ref.id
        doc_ref.set(data)
        return doc_ref.id

    async def get_history(self, uid: str, limit: int = 25) -> List[Dict[str, Any]]:
        """
        Retrieves user playback histories.
        """
        if self.db is None:
            user_hist = [h for h in _mock_music_history if h.get("uid") == uid]
            user_hist.sort(key=lambda x: x.get("timestamp", 0), reverse=True)
            return user_hist[:limit]

        query = (
            self.db.collection(self.collection_name)
            .where("uid", "==", uid)
            .order_by("timestamp", direction=Query.DESCENDING)
            .limit(limit)
        )
        docs = query.stream()
        return [doc.to_dict() for doc in docs]
