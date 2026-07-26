from typing import List, Dict, Any
from google.cloud.firestore import Query
from app.core.firebase import get_db

_mock_stories: List[Dict[str, Any]] = []

class StoryRepository:
    def __init__(self):
        self.db = get_db()
        self.collection_name = "story_records"

    async def save_story(self, uid: str, data: Dict[str, Any]) -> str:
        data["uid"] = uid
        if self.db is None:
            data["story_id"] = data.get("story_id", f"mock_story_{len(_mock_stories)}")
            _mock_stories.append(data)
            return data["story_id"]

        doc_ref = self.db.collection(self.collection_name).document()
        data["story_id"] = doc_ref.id
        doc_ref.set(data)
        return doc_ref.id

    async def get_stories(self, uid: str, limit: int = 50) -> List[Dict[str, Any]]:
        if self.db is None:
            user_stories = [s for s in _mock_stories if s.get("uid") == uid]
            user_stories.sort(key=lambda x: x.get("timestamp", 0), reverse=True)
            return user_stories[:limit]

        query = (
            self.db.collection(self.collection_name)
            .where("uid", "==", uid)
            .order_by("timestamp", direction=Query.DESCENDING)
            .limit(limit)
        )
        docs = query.stream()
        return [doc.to_dict() for doc in docs]

    async def toggle_favorite(self, uid: str, story_id: str) -> Dict[str, Any]:
        if self.db is None:
            for s in _mock_stories:
                if s.get("story_id") == story_id and s.get("uid") == uid:
                    s["is_favorite"] = not s.get("is_favorite", False)
                    return s
            raise KeyError("Story not found")

        doc_ref = self.db.collection(self.collection_name).document(story_id)
        doc = doc_ref.get()
        if not doc.exists:
            raise KeyError("Story not found")
        
        story_data = doc.to_dict()
        if story_data.get("uid") != uid:
            raise PermissionError("Access denied")

        new_favorite = not story_data.get("is_favorite", False)
        doc_ref.update({"is_favorite": new_favorite})
        story_data["is_favorite"] = new_favorite
        return story_data
