from typing import List, Dict, Any
from google.cloud.firestore import Query
from app.core.firebase import get_db

# In-memory storage fallback for local testing
_mock_chat_history: List[Dict[str, Any]] = []

class ChatHistoryRepository:
    def __init__(self):
        self.db = get_db()
        self.collection_name = "chat_history"

    async def add_message(self, data: Dict[str, Any]) -> str:
        """
        Saves a chat message (user or bot) to Firestore.
        """
        if self.db is None:
            # Set mock document ID
            data["msg_id"] = data.get("msg_id", f"mock_msg_{len(_mock_chat_history)}")
            _mock_chat_history.append(data)
            return data["msg_id"]

        doc_ref = self.db.collection(self.collection_name).document()
        data["msg_id"] = doc_ref.id
        doc_ref.set(data)
        return doc_ref.id

    async def get_messages(self, uid: str, limit: int = 50) -> List[Dict[str, Any]]:
        """
        Retrieves chronological chat logs for a specific user.
        """
        if self.db is None:
            # Filter and sort in-memory by timestamp ascending for conversational flows
            user_chat = [msg for msg in _mock_chat_history if msg.get("uid") == uid]
            user_chat.sort(key=lambda x: x.get("timestamp", 0))
            return user_chat[-limit:]

        query = (
            self.db.collection(self.collection_name)
            .where("uid", "==", uid)
            .order_by("timestamp", direction=Query.ASCENDING)
        )
        docs = query.stream()
        results = [doc.to_dict() for doc in docs]
        return results[-limit:]
