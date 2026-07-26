import time
from typing import Dict, Any
from app.core.firebase import get_db

_mock_coins: Dict[str, Dict[str, Any]] = {}

class CoinsRepository:
    def __init__(self):
        self.db = get_db()
        self.collection_name = "coins"

    async def get_balance(self, uid: str) -> Dict[str, Any]:
        """
        Gets current coin balance profile.
        """
        if self.db is None:
            if uid not in _mock_coins:
                _mock_coins[uid] = {"balance": 0, "total_earned": 0, "last_transaction": time.time()}
            return _mock_coins[uid]

        doc_ref = self.db.collection(self.collection_name).document(uid)
        doc = doc_ref.get()
        if doc.exists:
            return doc.to_dict()
        
        # Initialize
        init_data = {"balance": 0, "total_earned": 0, "last_transaction": time.time()}
        doc_ref.set(init_data)
        return init_data

    async def update_balance(self, uid: str, increment: int) -> Dict[str, Any]:
        """
        Increments user coin balance.
        """
        if self.db is None:
            profile = await self.get_balance(uid)
            profile["balance"] += increment
            if increment > 0:
                profile["total_earned"] += increment
            profile["last_transaction"] = time.time()
            return profile

        doc_ref = self.db.collection(self.collection_name).document(uid)
        
        # Run inside a Firestore Transaction to prevent race conditions
        db_client = get_db()
        transaction = db_client.transaction()
        
        @firestore_transactional
        def update_in_transaction(tx, ref):
            snapshot = ref.get(transaction=tx)
            balance = 0
            total_earned = 0
            if snapshot.exists:
                data = snapshot.to_dict()
                balance = data.get("balance", 0)
                total_earned = data.get("total_earned", 0)
            
            balance += increment
            if increment > 0:
                total_earned += increment
                
            updated = {
                "balance": balance,
                "total_earned": total_earned,
                "last_transaction": time.time()
            }
            tx.set(ref, updated)
            return updated

        # For simple offline mock support if firestore package transaction throws
        try:
            from google.cloud.firestore import transactional as firestore_transactional
            return update_in_transaction(transaction, doc_ref)
        except Exception:
            # Fallback simple write if transactional imports fail
            doc = doc_ref.get()
            balance = 0
            total_earned = 0
            if doc.exists:
                data = doc.to_dict()
                balance = data.get("balance", 0)
                total_earned = data.get("total_earned", 0)
            balance += increment
            if increment > 0:
                total_earned += increment
            updated = {
                "balance": balance,
                "total_earned": total_earned,
                "last_transaction": time.time()
            }
            doc_ref.set(updated)
            return updated
