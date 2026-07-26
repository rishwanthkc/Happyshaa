from typing import List, Dict, Any
from google.cloud.firestore import Query
from app.core.firebase import get_db

_mock_journals: List[Dict[str, Any]] = []
_mock_weekly_reports: List[Dict[str, Any]] = []

class JournalRepository:
    def __init__(self):
        self.db = get_db()
        self.collection_name = "journal_entries"
        self.reports_collection = "weekly_reports"

    async def create_journal(self, uid: str, data: Dict[str, Any]) -> str:
        """
        Saves a journal entry to Firestore.
        """
        data["uid"] = uid
        if self.db is None:
            data["journal_id"] = data.get("journal_id", f"mock_journal_{len(_mock_journals)}")
            _mock_journals.append(data)
            return data["journal_id"]

        doc_ref = self.db.collection(self.collection_name).document()
        data["journal_id"] = doc_ref.id
        doc_ref.set(data)
        return doc_ref.id

    async def get_journals(self, uid: str, limit: int = 50) -> List[Dict[str, Any]]:
        """
        Retrieves user journal entries.
        """
        if self.db is None:
            user_journals = [j for j in _mock_journals if j.get("uid") == uid]
            user_journals.sort(key=lambda x: x.get("timestamp", 0), reverse=True)
            return user_journals[:limit]

        query = (
            self.db.collection(self.collection_name)
            .where("uid", "==", uid)
            .order_by("timestamp", direction=Query.DESCENDING)
            .limit(limit)
        )
        docs = query.stream()
        return [doc.to_dict() for doc in docs]

    async def create_weekly_report(self, uid: str, data: Dict[str, Any]) -> str:
        """
        Saves a weekly CBT summary report to Firestore.
        """
        data["uid"] = uid
        if self.db is None:
            data["report_id"] = data.get("report_id", f"mock_report_{len(_mock_weekly_reports)}")
            _mock_weekly_reports.append(data)
            return data["report_id"]

        doc_ref = self.db.collection(self.reports_collection).document()
        data["report_id"] = doc_ref.id
        doc_ref.set(data)
        return doc_ref.id

    async def get_weekly_reports(self, uid: str, limit: int = 15) -> List[Dict[str, Any]]:
        """
        Retrieves user weekly progress reports.
        """
        if self.db is None:
            user_reports = [r for r in _mock_weekly_reports if r.get("uid") == uid]
            user_reports.sort(key=lambda x: x.get("timestamp", 0), reverse=True)
            return user_reports[:limit]

        query = (
            self.db.collection(self.reports_collection)
            .where("uid", "==", uid)
            .order_by("timestamp", direction=Query.DESCENDING)
            .limit(limit)
        )
        docs = query.stream()
        return [doc.to_dict() for doc in docs]
