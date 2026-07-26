from typing import List, Dict, Any
from app.core.firebase import get_db

_mock_favorites: Dict[str, List[str]] = {}

class FavoriteSongsRepository:
    def __init__(self):
        self.db = get_db()
        self.collection_name = "favorite_songs"

    async def toggle_favorite(self, uid: str, song_id: str) -> bool:
        """
        Toggles a song in the user's favorites list. Returns true if favorited, false if removed.
        """
        if self.db is None:
            if uid not in _mock_favorites:
                _mock_favorites[uid] = []
            if song_id in _mock_favorites[uid]:
                _mock_favorites[uid].remove(song_id)
                return False
            else:
                _mock_favorites[uid].append(song_id)
                return True

        doc_ref = self.db.collection(self.collection_name).document(uid)
        doc = doc_ref.get()
        
        song_ids = []
        if doc.exists:
            song_ids = doc.to_dict().get("song_ids", [])

        if song_id in song_ids:
            song_ids.remove(song_id)
            is_fav = False
        else:
            song_ids.append(song_id)
            is_fav = True

        doc_ref.set({"song_ids": song_ids})
        return is_fav

    async def get_favorites(self, uid: str) -> List[str]:
        """
        Retrieves user's favorited song IDs list.
        """
        if self.db is None:
            return _mock_favorites.get(uid, [])

        doc_ref = self.db.collection(self.collection_name).document(uid)
        doc = doc_ref.get()
        if doc.exists:
            return doc.to_dict().get("song_ids", [])
        return []
