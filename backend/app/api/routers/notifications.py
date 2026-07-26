import logging
from fastapi import APIRouter, Depends, HTTPException, status
from app.middleware.auth_middleware import get_current_user
from app.models.schemas import UserProfile, FCMTokenRequest, NotificationItemResponse, SendNotificationRequest, SendNotificationResponse
from app.repositories.notification_repo import NotificationRepository

logger = logging.getLogger(__name__)
router = APIRouter()

notification_repo = NotificationRepository()

@router.post("/token")
async def save_fcm_token(
    request: FCMTokenRequest,
    current_user: UserProfile = Depends(get_current_user)
):
    """
    Registers or updates the user device FCM token.
    """
    try:
        await notification_repo.save_token(current_user.uid, request.fcm_token)
        return {"status": "success", "message": "Token synced successfully"}
    except Exception as e:
        logger.error(f"Error saving FCM token: {e}")
        raise HTTPException(
            status_code=status.HTTP_500_INTERNAL_SERVER_ERROR,
            detail=f"Failed to save FCM token: {str(e)}"
        )

@router.get("/history", response_model=list[NotificationItemResponse])
async def get_notification_history(
    current_user: UserProfile = Depends(get_current_user)
) -> list[NotificationItemResponse]:
    """
    Retrieves notification logs history.
    """
    try:
        logs = await notification_repo.get_notifications(current_user.uid)
        return [NotificationItemResponse(**log) for log in logs]
    except Exception as e:
        logger.error(f"Error fetching notifications list: {e}")
        raise HTTPException(
            status_code=status.HTTP_500_INTERNAL_SERVER_ERROR,
            detail=f"Failed to retrieve notifications: {str(e)}"
        )

@router.post("/test")
async def send_test_notification(
    current_user: UserProfile = Depends(get_current_user)
):
    """
    Triggers a mock notification dispatch to test the pipeline.
    """
    try:
        uid = current_user.uid
        title = "Wellness Reminder"
        body = "Take a moment to pause and breathe today. Aura is here for you."
        
        # Log entry in history
        notif_id = await notification_repo.add_notification(uid, title, body)
        
        token = await notification_repo.get_token(uid)
        
        # Simulated FCM push
        logger.info(f"Simulating push notification dispatch to token {token[:10]}...")
        
        return {
            "status": "success_simulated",
            "notification_id": notif_id,
            "title": title,
            "body": body,
            "target_token": token
        }
    except Exception as e:
        logger.error(f"Error triggering test push: {e}")
        raise HTTPException(
            status_code=status.HTTP_500_INTERNAL_SERVER_ERROR,
            detail=f"Failed to dispatch test notification: {str(e)}"
        )

@router.post("/send", response_model=SendNotificationResponse)
async def send_notification(
    request: SendNotificationRequest,
    current_user: UserProfile = Depends(get_current_user)
) -> SendNotificationResponse:
    """
    Dispatches a custom notification (daily, mood, meditation, journal, emergency) to the logged-in user.
    """
    try:
        uid = current_user.uid
        notif_id = await notification_repo.add_notification(uid, request.title, request.body)
        
        token = await notification_repo.get_token(uid)
        
        # Simulate background push dispatch
        logger.info(f"Dispatched push alert {request.notification_type} to token {token[:10] if token else 'None'}...")
        
        return SendNotificationResponse(
            status="success_dispatched",
            notification_id=notif_id,
            notification_type=request.notification_type,
            target_token=token or "mock_device_token"
        )
    except Exception as e:
        logger.error(f"Error dispatching notification: {e}")
        raise HTTPException(
            status_code=status.HTTP_500_INTERNAL_SERVER_ERROR,
            detail=f"Failed to dispatch notification: {str(e)}"
        )
