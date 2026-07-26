import logging
from fastapi import APIRouter, Depends, HTTPException, status
from app.middleware.auth_middleware import get_current_user
from app.models.schemas import UserProfile, ContactCreateRequest, ContactUpdateRequest, ContactResponse
from app.repositories.contacts_repo import ContactsRepository

logger = logging.getLogger(__name__)
router = APIRouter()

contacts_repo = ContactsRepository()

@router.get("", response_model=list[ContactResponse])
async def get_contacts(
    current_user: UserProfile = Depends(get_current_user)
) -> list[ContactResponse]:
    """
    Retrieves all support contacts for the logged-in user.
    """
    try:
        contacts = await contacts_repo.get_contacts(current_user.uid)
        return [ContactResponse(**c) for c in contacts]
    except Exception as e:
        logger.error(f"Error fetching contacts: {e}")
        raise HTTPException(
            status_code=status.HTTP_500_INTERNAL_SERVER_ERROR,
            detail=f"Failed to fetch contacts: {str(e)}"
        )

@router.post("", response_model=ContactResponse)
async def create_contact(
    request: ContactCreateRequest,
    current_user: UserProfile = Depends(get_current_user)
) -> ContactResponse:
    """
    Creates a new support contact.
    """
    try:
        data = request.model_dump()
        data["uid"] = current_user.uid
        contact_id = await contacts_repo.create_contact(data)
        data["contact_id"] = contact_id
        return ContactResponse(**data)
    except Exception as e:
        logger.error(f"Error creating contact: {e}")
        raise HTTPException(
            status_code=status.HTTP_500_INTERNAL_SERVER_ERROR,
            detail=f"Failed to create contact: {str(e)}"
        )

@router.put("/{contact_id}")
async def update_contact(
    contact_id: str,
    request: ContactUpdateRequest,
    current_user: UserProfile = Depends(get_current_user)
):
    """
    Updates details of a support contact.
    """
    try:
        update_data = {k: v for k, v in request.model_dump().items() if v is not None}
        success = await contacts_repo.update_contact(contact_id, update_data)
        if not success:
            raise HTTPException(
                status_code=status.HTTP_404_NOT_FOUND,
                detail="Contact not found."
            )
        return {"status": "success", "contact_id": contact_id}
    except HTTPException:
        raise
    except Exception as e:
        logger.error(f"Error updating contact: {e}")
        raise HTTPException(
            status_code=status.HTTP_500_INTERNAL_SERVER_ERROR,
            detail=f"Failed to update contact: {str(e)}"
        )

@router.delete("/{contact_id}")
async def delete_contact(
    contact_id: str,
    current_user: UserProfile = Depends(get_current_user)
):
    """
    Deletes a support contact.
    """
    try:
        success = await contacts_repo.delete_contact(contact_id)
        if not success:
            raise HTTPException(
                status_code=status.HTTP_404_NOT_FOUND,
                detail="Contact not found."
            )
        return {"status": "success", "contact_id": contact_id}
    except HTTPException:
        raise
    except Exception as e:
        logger.error(f"Error deleting contact: {e}")
        raise HTTPException(
            status_code=status.HTTP_500_INTERNAL_SERVER_ERROR,
            detail=f"Failed to delete contact: {str(e)}"
        )

@router.post("/{contact_id}/favorite")
async def toggle_favorite_contact(
    contact_id: str,
    current_user: UserProfile = Depends(get_current_user)
):
    """
    Toggles favorite status.
    """
    try:
        contacts = await contacts_repo.get_contacts(current_user.uid)
        target = next((c for c in contacts if c.get("contact_id") == contact_id), None)
        if not target:
            raise HTTPException(
                status_code=status.HTTP_404_NOT_FOUND,
                detail="Contact not found."
            )
        
        new_fav = not target.get("is_favorite", False)
        await contacts_repo.update_contact(contact_id, {"is_favorite": new_fav})
        return {"status": "success", "contact_id": contact_id, "is_favorite": new_fav}
    except HTTPException:
        raise
    except Exception as e:
        logger.error(f"Error toggling favorite contact: {e}")
        raise HTTPException(
            status_code=status.HTTP_500_INTERNAL_SERVER_ERROR,
            detail=f"Failed to toggle favorite status: {str(e)}"
        )
