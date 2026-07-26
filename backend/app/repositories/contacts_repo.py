from typing import List, Dict, Any
from app.core.firebase import get_db

_mock_contacts: List[Dict[str, Any]] = []

class ContactsRepository:
    def __init__(self):
        self.db = get_db()
        self.collection_name = "contacts"

    async def get_contacts(self, uid: str) -> List[Dict[str, Any]]:
        """
        Fetches all contacts for a specific user.
        """
        if self.db is None:
            return [c for c in _mock_contacts if c.get("uid") == uid]

        query = self.db.collection(self.collection_name).where("uid", "==", uid)
        docs = query.stream()
        return [doc.to_dict() for doc in docs]

    async def create_contact(self, data: Dict[str, Any]) -> str:
        """
        Creates a new support contact doc.
        """
        if self.db is None:
            data["contact_id"] = data.get("contact_id", f"mock_contact_{len(_mock_contacts)}")
            _mock_contacts.append(data)
            return data["contact_id"]

        doc_ref = self.db.collection(self.collection_name).document()
        data["contact_id"] = doc_ref.id
        doc_ref.set(data)
        return doc_ref.id

    async def update_contact(self, contact_id: str, data: Dict[str, Any]) -> bool:
        """
        Updates fields of an existing contact.
        """
        if self.db is None:
            for c in _mock_contacts:
                if c.get("contact_id") == contact_id:
                    c.update(data)
                    return True
            return False

        doc_ref = self.db.collection(self.collection_name).document(contact_id)
        if not doc_ref.get().exists:
            return False
        doc_ref.update(data)
        return True

    async def delete_contact(self, contact_id: str) -> bool:
        """
        Deletes a support contact.
        """
        if self.db is None:
            global _mock_contacts
            initial_len = len(_mock_contacts)
            _mock_contacts = [c for c in _mock_contacts if c.get("contact_id") != contact_id]
            return len(_mock_contacts) < initial_len

        doc_ref = self.db.collection(self.collection_name).document(contact_id)
        if not doc_ref.get().exists:
            return False
        doc_ref.delete()
        return True
