import time
import logging
from fastapi import APIRouter, Depends, HTTPException, status
from app.middleware.auth_middleware import get_current_user
from app.models.schemas import UserProfile, MoodAnalysisRequest, MoodLogEntry, MoodHistoryResponse
from app.repositories.mood_log import MoodLogRepository
from app.services.emotion_detector import EmotionDetectorService

logger = logging.getLogger(__name__)
router = APIRouter()

mood_repo = MoodLogRepository()
emotion_service = EmotionDetectorService()

@router.post("/analyze", response_model=MoodLogEntry)
async def analyze_mood(
    request: MoodAnalysisRequest,
    current_user: UserProfile = Depends(get_current_user)
) -> MoodLogEntry:
    """
    Performs emotion detection on input text, saves the log entry to Firestore,
    and returns the analyzed results along with suggested activities.
    """
    try:
        # Run generative emotion analyzer
        analysis = await emotion_service.analyze_text(request.text)
        
        # Prepare log model
        log_data = {
            "uid": current_user.uid,
            "timestamp": time.time(),
            "primary_emotion": analysis["primary_emotion"],
            "confidence_score": analysis["confidence_score"],
            "stress_level": analysis["stress_level"],
            "anxiety_level": analysis["anxiety_level"],
            "sadness_level": analysis["sadness_level"],
            "anger_level": analysis["anger_level"],
            "happiness_level": analysis["happiness_level"],
            "confidence_level": analysis["confidence_level"],
            "suggested_activities": analysis["suggested_activities"]
        }
        
        # Commit to Firestore
        log_id = await mood_repo.create_mood_log(log_data)
        log_data["log_id"] = log_id
        
        return MoodLogEntry(**log_data)
        
    except Exception as e:
        logger.error(f"Error during mood log analyze router: {e}")
        raise HTTPException(
            status_code=status.HTTP_500_INTERNAL_SERVER_ERROR,
            detail=f"Failed to analyze and save mood logs: {str(e)}"
        )

@router.get("/history", response_model=MoodHistoryResponse)
async def get_mood_history(
    current_user: UserProfile = Depends(get_current_user)
) -> MoodHistoryResponse:
    """
    Fetches past logged moods for the authenticated user.
    """
    try:
        logs = await mood_repo.get_mood_logs(current_user.uid)
        parsed_logs = [MoodLogEntry(**log) for log in logs]
        return MoodHistoryResponse(logs=parsed_logs)
        
    except Exception as e:
        logger.error(f"Error retrieving mood history: {e}")
        raise HTTPException(
            status_code=status.HTTP_500_INTERNAL_SERVER_ERROR,
            detail=f"Failed to fetch mood history logs: {str(e)}"
        )
