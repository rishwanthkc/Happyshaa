import logging
from fastapi import APIRouter, Depends, HTTPException, status
from app.middleware.auth_middleware import get_current_user
from app.models.schemas import UserProfile, RecommendationCardResponse
from app.services.recommendation_service import RecommendationService

logger = logging.getLogger(__name__)
router = APIRouter()

recommend_service = RecommendationService()

@router.get("", response_model=list[RecommendationCardResponse])
async def get_daily_recommendations(
    current_user: UserProfile = Depends(get_current_user)
) -> list[RecommendationCardResponse]:
    """
    Fetches the customized deck of daily wellness recommendation cards.
    """
    try:
        cards = await recommend_service.get_recommendations(current_user.uid)
        return [RecommendationCardResponse(**card) for card in cards]
    except Exception as e:
        logger.error(f"Error fetching recommendations: {e}")
        raise HTTPException(
            status_code=status.HTTP_500_INTERNAL_SERVER_ERROR,
            detail=f"Failed to fetch daily recommendations: {str(e)}"
        )
