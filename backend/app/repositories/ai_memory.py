from typing import List, Dict, Any
from app.core.firebase import get_db

# In-memory storage fallback for local testing
_mock_ai_memory: List[Dict[str, Any]] = []

class AIMemoryRepository:
    def __init__(self):
        self.db = get_db()
        self.collection_name = "ai_memory"

    async def save_memory(self, data: Dict[str, Any]) -> str:
        """
        Saves a compiled fact-memory node to Firestore.
        """
        if self.db is None:
            _mock_ai_memory.append(data)
            return data.get("memory_id", "mock_mem_id")

        doc_ref = self.db.collection(self.collection_name).document()
        data["memory_id"] = doc_ref.id
        doc_ref.set(data)
        return doc_ref.id

    async def get_memories(self, uid: str) -> List[Dict[str, Any]]:
        """
        Retrieves all memory nodes for similarity comparisons.
        """
        if self.db is None:
            return [mem for mem in _mock_ai_memory if mem.get("uid") == uid]

        query = self.db.collection(self.collection_name).where("uid", "==", uid)
        docs = query.stream()
        return [doc.to_dict() for doc in docs]
