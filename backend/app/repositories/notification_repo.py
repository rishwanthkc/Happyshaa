import time
from typing import List, Dict, Any
from google.cloud.firestore import Query
from app.core.firebase import get_db

_mock_fcm_tokens: Dict[str, str] = {}
_mock_notifications: List[Dict[str, Any]] = []

class NotificationRepository:
    def __init__(self):
        self.db = get_db()
        self.collection_name = "notifications"
        self.tokens_collection = "fcm_tokens"

    async def save_token(self, uid: str, token: str):
        """
        Saves FCM registration token.
        """
        if self.db is None:
            _mock_fcm_tokens[uid] = token
            return

        doc_ref = self.db.collection(self.tokens_collection).document(uid)
        doc_ref.set({"fcm_token": token, "updated_at": time.time()})

    async def get_token(self, uid: str) -> str:
        """
        Gets token for a user.
        """
        if self.db is None:
            return _mock_fcm_tokens.get(uid, "mock_fcm_token_123")

        doc_ref = self.db.collection(self.tokens_collection).document(uid)
        doc = doc_ref.get()
        if doc.exists:
            return doc.to_dict().get("fcm_token", "")
        return ""

    async def add_notification(self, uid: str, title: str, body: str) -> str:
        """
        Logs a notification dispatched history.
        """
        data = {
            "uid": uid,
            "title": title,
            "body": body,
            "timestamp": time.time(),
            "is_read": False
        }
        
        if self.db is None:
            data["notification_id"] = f"mock_notif_{len(_mock_notifications)}"
            _mock_notifications.append(data)
            return data["notification_id"]

        doc_ref = self.db.collection(self.collection_name).document()
        data["notification_id"] = doc_ref.id
        doc_ref.set(data)
        return doc_ref.id

    async def get_notifications(self, uid: str, limit: int = 50) -> List[Dict[str, Any]]:
        """
        Gets history list.
        """
        if self.db is None:
            user_notifs = [n for n in _mock_notifications if n.get("uid") == uid]
            user_notifs.sort(key=lambda x: x.get("timestamp", 0), reverse=True)
            return user_notifs[:limit]

        query = (
            self.db.collection(self.collection_name)
            .where("uid", "==", uid)
            .order_by("timestamp", direction=Query.DESCENDING)
            .limit(limit)
        )
        docs = query.stream()
        return [doc.to_dict() for doc in docs]
