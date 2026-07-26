import logging
from fastapi import HTTPException, Security, status
from fastapi.security import HTTPAuthorizationCredentials, HTTPBearer
from firebase_admin import auth as firebase_auth
from app.core.config import settings
from app.models.schemas import UserProfile

logger = logging.getLogger(__name__)

# Security scheme for extract token
security_scheme = HTTPBearer(auto_error=False)

async def get_current_user(
    credentials: HTTPAuthorizationCredentials = Security(security_scheme)
) -> UserProfile:
    """
    FastAPI dependency to extract and verify the Firebase ID Token.
    Returns the user profile if validation is successful.
    """
    if not credentials:
        raise HTTPException(
            status_code=status.HTTP_401_UNAUTHORIZED,
            detail="Authorization header missing or invalid",
            headers={"WWW-Authenticate": "Bearer"},
        )
        
    token = credentials.credentials
    
    # Check if we are running in testing environment with mock tokens
    if settings.ENV == "testing" and token.startswith("mock_token_"):
        # Extract fields from mock token (e.g. mock_token_user123_test@auraai.com)
        parts = token.split("_")
        mock_uid = parts[2] if len(parts) > 2 else "mock_user_123"
        mock_email = parts[3] if len(parts) > 3 else "mock@auraai.com"
        
        from datetime import datetime, timezone
        return UserProfile(
            uid=mock_uid,
            email=mock_email,
            display_name="Mock User",
            photo_url=None,
            created_at=datetime.now(timezone.utc),
            last_login=datetime.now(timezone.utc),
            is_active=True
        )

    try:
        # Verify the ID token using firebase-admin SDK
        decoded_token = firebase_auth.verify_id_token(token)
        
        # Extract user profile parameters
        uid = decoded_token.get("uid")
        email = decoded_token.get("email")
        name = decoded_token.get("name")
        picture = decoded_token.get("picture")
        
        if not email:
            # Fallback if email is not in claim (sometimes custom auth)
            email = decoded_token.get("email", f"{uid}@auraai.com")

        from datetime import datetime, timezone
        # Create user profile record from JWT metadata
        user = UserProfile(
            uid=uid,
            email=email,
            display_name=name,
            photo_url=picture,
            created_at=datetime.fromtimestamp(decoded_token.get("auth_time", datetime.now().timestamp()), tz=timezone.utc),
            last_login=datetime.now(timezone.utc),
            is_active=True
        )
        return user

    except Exception as e:
        logger.error(f"Token validation failed: {str(e)}")
        raise HTTPException(
            status_code=status.HTTP_401_UNAUTHORIZED,
            detail=f"Invalid or expired credentials: {str(e)}",
            headers={"WWW-Authenticate": "Bearer"},
        )
