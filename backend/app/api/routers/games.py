import logging
from fastapi import APIRouter, Depends, HTTPException, status
from app.middleware.auth_middleware import get_current_user
from app.models.schemas import UserProfile, GameScoreSubmitRequest, GameProgressResponse, AchievementResponse
from app.repositories.game_scores import GameScoresRepository
from app.repositories.achievements import AchievementsRepository
from app.repositories.coins import CoinsRepository

logger = logging.getLogger(__name__)
router = APIRouter()

scores_repo = GameScoresRepository()
achievements_repo = AchievementsRepository()
coins_repo = CoinsRepository()

# Static catalog of the 7 wellness mini-games
GAMES_CATALOG = [
    {"game_id": "tictactoe", "title": "Tic Tac Toe", "description": "Relaxing match vs AI", "category": "Focus"},
    {"game_id": "puzzle2048", "title": "2048", "description": "Calming numbers match puzzle", "category": "Cognitive"},
    {"game_id": "wordpuzzle", "title": "Gratitude Word Anagrams", "description": "Unscramble positive emotions", "category": "Mindfulness"},
    {"game_id": "memorymatch", "title": "Memory Match Cards", "description": "Focus memory card flip match", "category": "Focus"},
    {"game_id": "sudoku", "title": "Wellness Sudoku", "description": "Calming numeric logical boards", "category": "Cognitive"},
    {"game_id": "bubblepop", "title": "Stress Bubble Pop", "description": "Calming float bubble popping", "category": "Relaxation"},
    {"game_id": "coloring", "title": "Coloring Canvas", "description": "Zen calming coloring outlines", "category": "Relaxation"}
]

@router.get("")
async def get_games(
    current_user: UserProfile = Depends(get_current_user)
):
    """
    Returns list of calming wellness mini-games.
    """
    return GAMES_CATALOG

@router.post("/scores", response_model=GameProgressResponse)
async def submit_game_score(
    request: GameScoreSubmitRequest,
    current_user: UserProfile = Depends(get_current_user)
) -> GameProgressResponse:
    """
    Submits score, awards XP and Coins, updates balance, and checks for milestone unlocks.
    """
    try:
        uid = current_user.uid
        game_id = request.game_id
        score = request.score

        # Calculate rewards: XP (10 points flat) and Coins (score * 2)
        xp_earned = 10
        coins_earned = score * 2

        # 1. Commit score log
        score_id = await scores_repo.add_score(uid, game_id, score)

        # 2. Update user's coin wallet
        coin_profile = await coins_repo.update_balance(uid, coins_earned)
        new_balance = coin_profile.get("balance", 0)

        # 3. Check for milestones achievements
        unlocked = None
        existing_achievements = await achievements_repo.get_achievements(uid)
        unlocked_types = [a.get("achievement_type") for a in existing_achievements]

        # Milestone 1: Mindful Gamer (completed 5 games)
        past_scores = await scores_repo.get_scores(uid)
        if len(past_scores) >= 5 and "MINDFUL_GAMER" not in unlocked_types:
            await achievements_repo.unlock_achievement(uid, "MINDFUL_GAMER")
            unlocked = "MINDFUL_GAMER"
        
        # Milestone 2: Coin Collector (balance reaches 100 coins)
        elif new_balance >= 100 and "COIN_COLLECTOR" not in unlocked_types:
            await achievements_repo.unlock_achievement(uid, "COIN_COLLECTOR")
            unlocked = "COIN_COLLECTOR"

        return GameProgressResponse(
            score_id=score_id,
            xp_earned=xp_earned,
            coins_earned=coins_earned,
            new_balance=new_balance,
            unlocked_achievement=unlocked
        )
    except Exception as e:
        logger.error(f"Error submitting game score: {e}")
        raise HTTPException(
            status_code=status.HTTP_500_INTERNAL_SERVER_ERROR,
            detail=f"Failed to log score progress: {str(e)}"
        )

@router.get("/achievements", response_model=list[AchievementResponse])
async def get_achievements(
    current_user: UserProfile = Depends(get_current_user)
) -> list[AchievementResponse]:
    """
    Retrieves user unlocked milestones.
    """
    try:
        achievements = await achievements_repo.get_achievements(current_user.uid)
        return [AchievementResponse(**a) for a in achievements]
    except Exception as e:
        logger.error(f"Error fetching achievements: {e}")
        raise HTTPException(
            status_code=status.HTTP_500_INTERNAL_SERVER_ERROR,
            detail=f"Failed to fetch achievements: {str(e)}"
        )
