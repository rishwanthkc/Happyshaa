import time
import logging
from fastapi import APIRouter, Depends, HTTPException, status
from app.middleware.auth_middleware import get_current_user
from app.models.schemas import UserProfile, JournalCreateRequest, JournalResponse, WeeklyReportResponse, VoiceJournalSummarizeRequest, VoiceJournalSummarizeResponse
from app.repositories.journal_repo import JournalRepository
from app.repositories.mood_log import MoodLogRepository
from app.services.journal_analyser import JournalAnalyserService
from app.services.weekly_compiler import WeeklyCompilerService

logger = logging.getLogger(__name__)
router = APIRouter()

journal_repo = JournalRepository()
mood_repo = MoodLogRepository()
analyser_service = JournalAnalyserService()
compiler_service = WeeklyCompilerService()

@router.post("", response_model=JournalResponse)
async def create_journal_entry(
    request: JournalCreateRequest,
    current_user: UserProfile = Depends(get_current_user)
) -> JournalResponse:
    """
    Creates a journal entry, triggers Gemini analysis, and commits it.
    """
    try:
        uid = current_user.uid
        
        # 1. Run LLM Analysis on Journal entry
        analysis = await analyser_service.analyze_journal(request.title, request.content)
        
        # 2. Compile model document
        data = {
            "title": request.title,
            "content": request.content,
            "audio_url": request.audio_url,
            "timestamp": time.time(),
            "detected_emotion": analysis["detected_emotion"],
            "emotion_confidence": analysis["emotion_confidence"],
            "reflection": analysis["reflection"],
            "gratitude_highlights": analysis["gratitude_highlights"],
            "triggers": analysis["triggers"]
        }
        
        # 3. Write to database
        journal_id = await journal_repo.create_journal(uid, data)
        data["journal_id"] = journal_id
        
        return JournalResponse(**data)
    except Exception as e:
        logger.error(f"Error creating journal entry: {e}")
        raise HTTPException(
            status_code=status.HTTP_500_INTERNAL_SERVER_ERROR,
            detail=f"Failed to analyze and save journal entry: {str(e)}"
        )

@router.get("", response_model=list[JournalResponse])
async def get_journals(
    current_user: UserProfile = Depends(get_current_user)
) -> list[JournalResponse]:
    """
    Retrieves user journal logs.
    """
    try:
        entries = await journal_repo.get_journals(current_user.uid)
        return [JournalResponse(**e) for e in entries]
    except Exception as e:
        logger.error(f"Error fetching journals list: {e}")
        raise HTTPException(
            status_code=status.HTTP_500_INTERNAL_SERVER_ERROR,
            detail=f"Failed to fetch journal logs: {str(e)}"
        )

@router.post("/weekly/generate", response_model=WeeklyReportResponse)
async def generate_weekly_report(
    current_user: UserProfile = Depends(get_current_user)
) -> WeeklyReportResponse:
    """
    Compiles recent 7-day journal entries and mood logs, generates a CBT report, and commits it.
    """
    try:
        uid = current_user.uid
        
        # 1. Fetch past 7 days of journals and moods
        journals = await journal_repo.get_journals(uid, limit=30)
        moods = await mood_repo.get_mood_logs(uid, limit=30)
        
        # Filter for last 7 days (604800 seconds)
        now = time.time()
        journals_7d = [j for j in journals if now - j.get("timestamp", 0) <= 604800]
        moods_7d = [m for m in moods if now - m.get("timestamp", 0) <= 604800]

        # 2. Run Compiler Synthesis service
        report = await compiler_service.compile_report(uid, journals_7d, moods_7d)
        
        # 3. Create document
        report_data = {
            "timestamp": time.time(),
            "dominant_mood": report["dominant_mood"],
            "average_stress_level": report["average_stress_level"],
            "gratitude_summary": report["gratitude_summary"],
            "identified_triggers": report["identified_triggers"],
            "self_care_plan": report["self_care_plan"]
        }
        
        # 4. Save
        report_id = await journal_repo.create_weekly_report(uid, report_data)
        report_data["report_id"] = report_id
        
        return WeeklyReportResponse(**report_data)
    except Exception as e:
        logger.error(f"Error generating weekly report: {e}")
        raise HTTPException(
            status_code=status.HTTP_500_INTERNAL_SERVER_ERROR,
            detail=f"Failed to compile weekly report: {str(e)}"
        )

@router.get("/weekly", response_model=list[WeeklyReportResponse])
async def get_weekly_reports(
    current_user: UserProfile = Depends(get_current_user)
) -> list[WeeklyReportResponse]:
    """
    Retrieves user's historical weekly progress reports.
    """
    try:
        reports = await journal_repo.get_weekly_reports(current_user.uid)
        return [WeeklyReportResponse(**r) for r in reports]
    except Exception as e:
        logger.error(f"Error retrieving weekly reports: {e}")
        raise HTTPException(
            status_code=status.HTTP_500_INTERNAL_SERVER_ERROR,
            detail=f"Failed to fetch reports: {str(e)}"
        )

@router.post("/voice/summarize", response_model=VoiceJournalSummarizeResponse)
async def summarize_voice_entry(
    request: VoiceJournalSummarizeRequest,
    current_user: UserProfile = Depends(get_current_user)
) -> VoiceJournalSummarizeResponse:
    """
    Analyzes an audio journal entry, transcribing it and providing summary/reflection metadata.
    """
    try:
        analysis = await analyser_service.summarize_voice_audio(request.audio_url)
        return VoiceJournalSummarizeResponse(**analysis)
    except Exception as e:
        logger.error(f"Error summarizing voice journal: {e}")
        raise HTTPException(
            status_code=status.HTTP_500_INTERNAL_SERVER_ERROR,
            detail=f"Failed to summarize voice journal entry: {str(e)}"
        )
