import logging
from datetime import datetime, timezone
from fastapi import APIRouter, Depends, HTTPException, status
from app.core.firebase import get_db
from app.middleware.auth_middleware import get_current_user
from app.models.schemas import UserProfile, UserProfileResponse, UserSyncRequest

logger = logging.getLogger(__name__)
router = APIRouter()

@router.post("/sync", response_model=UserProfileResponse)
async def sync_user(
    sync_data: UserSyncRequest,
    current_user: UserProfile = Depends(get_current_user)
) -> UserProfileResponse:
    """
    Synchronizes the authenticated Firebase user with the Firestore database.
    Creates a new user profile document if it doesn't exist, otherwise updates last_login.
    """
    db = get_db()
    if not db:
        # If firestore client is not running (e.g. mock environment/tests), return user directly
        logger.warning("Firestore client not available. Returning user profile directly without saving.")
        profile = UserProfile(
            uid=current_user.uid,
            email=current_user.email,
            display_name=sync_data.display_name or current_user.display_name,
            photo_url=sync_data.photo_url or current_user.photo_url,
            created_at=current_user.created_at,
            last_login=datetime.now(timezone.utc),
            is_active=current_user.is_active
        )
        return UserProfileResponse(status="success_mock", user=profile)

    try:
        user_ref = db.collection("users").document(current_user.uid)
        doc = user_ref.get()
        
        # Prepare data to save/update
        now = datetime.now(timezone.utc)
        
        # Keep track of updated profile data
        display_name = sync_data.display_name or current_user.display_name
        photo_url = sync_data.photo_url or current_user.photo_url

        if not doc.exists:
            # First time logging in, create user document
            user_data = {
                "uid": current_user.uid,
                "email": current_user.email,
                "display_name": display_name,
                "photo_url": photo_url,
                "created_at": now,
                "last_login": now,
                "is_active": True
            }
            user_ref.set(user_data)
            logger.info(f"Created new user profile in Firestore: {current_user.uid}")
            
            profile = UserProfile(
                uid=current_user.uid,
                email=current_user.email,
                display_name=display_name,
                photo_url=photo_url,
                created_at=now,
                last_login=now,
                is_active=True
            )
            return UserProfileResponse(status="created", user=profile)
        
        else:
            # Existing user, update last login and details if provided
            update_data = {
                "last_login": now
            }
            if display_name:
                update_data["display_name"] = display_name
            if photo_url:
                update_data["photo_url"] = photo_url
                
            user_ref.update(update_data)
            logger.info(f"Updated last_login for user: {current_user.uid}")
            
            existing_data = doc.to_dict()
            # Parse dates safely
            created_at = existing_data.get("created_at", now)
            if isinstance(created_at, str):
                created_at = datetime.fromisoformat(created_at)
            
            profile = UserProfile(
                uid=current_user.uid,
                email=existing_data.get("email", current_user.email),
                display_name=display_name or existing_data.get("display_name"),
                photo_url=photo_url or existing_data.get("photo_url"),
                created_at=created_at,
                last_login=now,
                is_active=existing_data.get("is_active", True)
            )
            return UserProfileResponse(status="synchronized", user=profile)

    except Exception as e:
        logger.error(f"Error syncing user: {str(e)}")
        raise HTTPException(
            status_code=status.HTTP_500_INTERNAL_SERVER_ERROR,
            detail=f"Failed to synchronize user data: {str(e)}"
        )
