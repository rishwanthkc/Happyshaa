import time
import math
import logging
import numpy as np
import google.generativeai as genai
from app.core.config import settings
from app.repositories.ai_memory import AIMemoryRepository

logger = logging.getLogger(__name__)

class MemoryManagerService:
    def __init__(self):
        self.enabled = bool(settings.GEMINI_API_KEY)
        self.memory_repo = AIMemoryRepository()
        self.embedding_model = "models/text-embedding-004"

    async def get_embeddings(self, text: str) -> list:
        """
        Retrieves vector embeddings for a given text segment using the Gemini API.
        """
        if not self.enabled or not text.strip():
            # Return dummy 384-dimensional vector for offline tests
            return [0.1] * 384

        try:
            result = genai.embed_content(
                model=self.embedding_model,
                contents=text,
                task_type="retrieval_document"
            )
            return result["embedding"]
        except Exception as e:
            logger.error(f"Failed to generate embeddings via Gemini: {e}")
            return [0.1] * 384

    async def save_new_fact(self, uid: str, fact: str):
        """
        Saves a new fact node with its associated vector embeddings.
        """
        embedding = await self.get_embeddings(fact)
        memory_node = {
            "uid": uid,
            "timestamp": time.time(),
            "content": fact,
            "embedding": embedding
        }
        await self.memory_repo.save_memory(memory_node)
        logger.info(f"Successfully saved new memory node for user {uid}: {fact}")

    async def retrieve_memories(self, uid: str, query: str, limit: int = 3) -> str:
        """
        Perfaces a semantic memory search utilizing cosine similarity and recency decay.
        Returns a compiled context string.
        """
        memories = await self.memory_repo.get_memories(uid)
        if not memories:
            return "No previous memories recorded."

        query_embedding = await self.get_embeddings(query)
        scored_memories = []

        for mem in memories:
            mem_embedding = mem.get("embedding")
            if not mem_embedding:
                continue

            similarity = self._cosine_similarity(query_embedding, mem_embedding)
            timestamp = mem.get("timestamp", time.time())
            
            # Combined score: Cosine Similarity (70%) + Recency Decay (30%)
            decay = self._calculate_recency_decay(timestamp)
            combined_score = (similarity * 0.70) + (decay * 0.30)

            scored_memories.append((combined_score, mem.get("content", "")))

        # Sort descending by combined score
        scored_memories.sort(key=lambda x: x[0], reverse=True)
        top_matches = [content for score, content in scored_memories if score > 0.55][:limit]

        if not top_matches:
            return "No relevant past memories found."

        return "\n".join([f"- {fact}" for fact in top_matches])

    def _cosine_similarity(self, v1: list, v2: list) -> float:
        a = np.array(v1)
        b = np.array(v2)
        dot = np.dot(a, b)
        norm_a = np.linalg.norm(a)
        norm_b = np.linalg.norm(b)
        if norm_a == 0 or norm_b == 0:
            return 0.0
        return float(dot / (norm_a * norm_b))

    def _calculate_recency_decay(self, timestamp: float) -> float:
        elapsed_seconds = time.time() - timestamp
        elapsed_days = elapsed_seconds / (24 * 3600)
        # Halflife of 7 days
        return math.exp(-elapsed_days / 7.0)
