import logging
from fastapi import APIRouter, Depends, HTTPException, status
from app.middleware.auth_middleware import get_current_user
from app.models.schemas import UserProfile, MusicTrack, FavoriteToggleRequest, HistoryLogRequest
from app.repositories.favorite_songs import FavoriteSongsRepository
from app.repositories.music_history import MusicHistoryRepository
from app.repositories.mood_log import MoodLogRepository

logger = logging.getLogger(__name__)
router = APIRouter()

fav_repo = FavoriteSongsRepository()
hist_repo = MusicHistoryRepository()
mood_repo = MoodLogRepository()

# Calming track catalog
WELLNESS_CATALOG = [
    MusicTrack(
        song_id="nature_rain",
        title="Soft Rainfall",
        artist="Nature Sounds",
        url="https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3",
        category="Nature",
        mood_tag="Stress"
    ),
    MusicTrack(
        song_id="lofi_focus",
        title="Midnight Study",
        artist="Lofi Beats",
        url="https://www.soundhelix.com/examples/mp3/SoundHelix-Song-2.mp3",
        category="Lofi",
        mood_tag="Anxiety"
    ),
    MusicTrack(
        song_id="binaural_relax",
        title="Theta Meditation Waves",
        artist="Binaural Mind",
        url="https://www.soundhelix.com/examples/mp3/SoundHelix-Song-3.mp3",
        category="Binaural Beats",
        mood_tag="Sadness"
    ),
    MusicTrack(
        song_id="calm_ocean",
        title="Ocean Whispers",
        artist="Nature Sounds",
        url="https://www.soundhelix.com/examples/mp3/SoundHelix-Song-4.mp3",
        category="Nature",
        mood_tag="Anger"
    )
]

@router.get("/songs", response_model=list[MusicTrack])
async def get_songs(
    current_user: UserProfile = Depends(get_current_user)
) -> list[MusicTrack]:
    """
    Fetches the lists of all available wellness tracks.
    """
    return WELLNESS_CATALOG

@router.post("/favorites/toggle")
async def toggle_favorite_song(
    request: FavoriteToggleRequest,
    current_user: UserProfile = Depends(get_current_user)
):
    """
    Toggles a song's favorite status. Returns whether it is currently favorited.
    """
    try:
        is_fav = await fav_repo.toggle_favorite(current_user.uid, request.song_id)
        return {"status": "success", "song_id": request.song_id, "is_favorite": is_fav}
    except Exception as e:
        logger.error(f"Error toggling favorite song: {e}")
        raise HTTPException(
            status_code=status.HTTP_500_INTERNAL_SERVER_ERROR,
            detail=f"Failed to toggle favorite: {str(e)}"
        )

@router.get("/favorites", response_model=list[MusicTrack])
async def get_favorites(
    current_user: UserProfile = Depends(get_current_user)
) -> list[MusicTrack]:
    """
    Retrieves user's favorited tracks.
    """
    try:
        fav_ids = await fav_repo.get_favorites(current_user.uid)
        return [track for track in WELLNESS_CATALOG if track.song_id in fav_ids]
    except Exception as e:
        logger.error(f"Error getting favorite songs: {e}")
        raise HTTPException(
            status_code=status.HTTP_500_INTERNAL_SERVER_ERROR,
            detail=f"Failed to fetch favorites: {str(e)}"
        )

@router.post("/history")
async def log_playback_history(
    request: HistoryLogRequest,
    current_user: UserProfile = Depends(get_current_user)
):
    """
    Appends playback completion log.
    """
    try:
        hist_id = await hist_repo.add_history_entry(current_user.uid, request.song_id, request.duration_sec)
        return {"status": "success", "history_id": hist_id}
    except Exception as e:
        logger.error(f"Error logging playback history: {e}")
        raise HTTPException(
            status_code=status.HTTP_500_INTERNAL_SERVER_ERROR,
            detail=f"Failed to log history: {str(e)}"
        )

@router.get("/recommend", response_model=list[MusicTrack])
async def get_recommendations(
    current_user: UserProfile = Depends(get_current_user)
) -> list[MusicTrack]:
    """
    Recommends calming music based on the user's latest mood check-in.
    """
    try:
        recent_moods = await mood_repo.get_mood_logs(current_user.uid, limit=1)
        if not recent_moods:
            # Default to nature & lofi
            return WELLNESS_CATALOG[:2]
            
        primary = recent_moods[0].get("primary_emotion", "Neutral")
        
        # Filter tracks matching mood tag
        recommended = [track for track in WELLNESS_CATALOG if track.mood_tag.lower() == primary.lower()]
        
        if not recommended:
            return WELLNESS_CATALOG[:2]
            
        return recommended
    except Exception as e:
        logger.error(f"Error resolving music recommendations: {e}")
        return WELLNESS_CATALOG[:2]
