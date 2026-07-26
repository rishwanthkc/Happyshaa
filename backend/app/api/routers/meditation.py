import time
import logging
from fastapi import APIRouter, Depends, HTTPException, status
from app.middleware.auth_middleware import get_current_user
from app.models.schemas import UserProfile, MeditationSessionRequest, MeditationSessionResponse, MeditationHistoryResponse
from app.repositories.meditation_repo import MeditationRepository
from app.repositories.coins import CoinsRepository

logger = logging.getLogger(__name__)
router = APIRouter()

meditation_repo = MeditationRepository()
coins_repo = CoinsRepository()

@router.post("/session", response_model=MeditationSessionResponse)
async def submit_meditation_session(
    request: MeditationSessionRequest,
    current_user: UserProfile = Depends(get_current_user)
) -> MeditationSessionResponse:
    """
    Submits a completed meditation session, awards XP/Coins, and saves it.
    """
    try:
        uid = current_user.uid
        
        # Save session logs
        data = {
            "breathing_type": request.breathing_type,
            "duration_seconds": request.duration_seconds,
            "timestamp": time.time(),
            "coins_reward": request.coins_reward,
            "xp_reward": request.xp_reward
        }
        session_id = await meditation_repo.save_session(uid, data)
        data["session_id"] = session_id
        data["uid"] = uid

        # Reward the user with coins
        if request.coins_reward > 0:
            await coins_repo.update_balance(uid, request.coins_reward)
            
        return MeditationSessionResponse(**data)
    except Exception as e:
        logger.error(f"Error submitting meditation session: {e}")
        raise HTTPException(
            status_code=status.HTTP_500_INTERNAL_SERVER_ERROR,
            detail=f"Failed to submit meditation session: {str(e)}"
        )

@router.get("/history", response_model=MeditationHistoryResponse)
async def get_meditation_history(
    current_user: UserProfile = Depends(get_current_user)
) -> MeditationHistoryResponse:
    """
    Retrieves meditation logs history, streaks, and accumulated stats.
    """
    try:
        uid = current_user.uid
        sessions = await meditation_repo.get_sessions(uid)
        
        total_xp = sum(s.get("xp_reward", 0) for s in sessions)
        total_coins = sum(s.get("coins_reward", 0) for s in sessions)
        
        # Basic streak calculator (sessions on consecutive calendar days)
        unique_days = set()
        for s in sessions:
            t = s.get("timestamp", 0)
            day = time.strftime("%Y-%m-%d", time.localtime(t))
            unique_days.add(day)
            
        streak = min(len(unique_days), 7)
        sessions_response = [MeditationSessionResponse(**s) for s in sessions]
        
        return MeditationHistoryResponse(
            sessions=sessions_response,
            streak=streak,
            total_xp=total_xp,
            total_coins=total_coins
        )
    except Exception as e:
        logger.error(f"Error fetching meditation history: {e}")
        raise HTTPException(
            status_code=status.HTTP_500_INTERNAL_SERVER_ERROR,
            detail=f"Failed to fetch meditation history: {str(e)}"
        )
