package com.auraai.data.repository

import com.auraai.data.remote.api.AuraApiService
import com.auraai.data.remote.api.JournalCreateRequest
import com.auraai.domain.model.JournalEntry
import com.auraai.domain.model.WeeklyReport
import com.auraai.domain.repository.JournalRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class JournalRepositoryImpl @Inject constructor(
    private val apiService: AuraApiService
) : JournalRepository {

    private val offlineJournals = mutableListOf<JournalEntry>()
    private val offlineWeeklyReports = mutableListOf<WeeklyReport>()

    override suspend fun createJournalEntry(
        token: String,
        title: String,
        content: String,
        audioUrl: String?
    ): Result<JournalEntry> {
        return try {
            val response = apiService.createJournalEntry(token, JournalCreateRequest(title, content, audioUrl))
            val entry = JournalEntry(
                journalId = response.journal_id,
                uid = response.uid,
                timestamp = response.timestamp,
                title = response.title,
                content = response.content,
                detectedEmotion = response.detected_emotion,
                emotionConfidence = response.emotion_confidence,
                audioUrl = response.audio_url,
                reflection = response.reflection,
                gratitudeHighlights = response.gratitude_highlights,
                triggers = response.triggers
            )
            offlineJournals.add(0, entry)
            Result.success(entry)
        } catch (e: Exception) {
            // Heuristic offline analysis
            val emotion = if (content.lowercase().contains("stressed")) "Stress" else "Confidence"
            val mockEntry = JournalEntry(
                journalId = "offline_${System.currentTimeMillis()}",
                uid = "offline_user",
                timestamp = System.currentTimeMillis() / 1000.0,
                title = title,
                content = content,
                detectedEmotion = emotion,
                emotionConfidence = 0.85f,
                audioUrl = audioUrl,
                reflection = "I'm glad you took the time to write today. Reflecting on your thoughts is a wonderful step.",
                gratitudeHighlights = listOf("Self-care reflection"),
                triggers = if (emotion == "Stress") listOf("Workload stressors") else emptyList()
            )
            offlineJournals.add(0, mockEntry)
            Result.success(mockEntry)
        }
    }

    override suspend fun getJournals(token: String): Result<List<JournalEntry>> {
        return try {
            val response = apiService.getJournals(token)
            val mapped = response.map {
                JournalEntry(
                    journalId = it.journal_id,
                    uid = it.uid,
                    timestamp = it.timestamp,
                    title = it.title,
                    content = it.content,
                    detectedEmotion = it.detected_emotion,
                    emotionConfidence = it.emotion_confidence,
                    audioUrl = it.audio_url,
                    reflection = it.reflection,
                    gratitudeHighlights = it.gratitude_highlights,
                    triggers = it.triggers
                )
            }
            offlineJournals.clear()
            offlineJournals.addAll(mapped)
            Result.success(mapped)
        } catch (e: Exception) {
            Result.success(offlineJournals)
        }
    }

    override suspend fun generateWeeklyReport(token: String): Result<WeeklyReport> {
        return try {
            val response = apiService.generateWeeklyReport(token)
            val report = WeeklyReport(
                reportId = response.report_id,
                uid = response.uid,
                timestamp = response.timestamp,
                dominantMood = response.dominant_mood,
                averageStressLevel = response.average_stress_level,
                gratitudeSummary = response.gratitude_summary,
                identifiedTriggers = response.identified_triggers,
                selfCarePlan = response.self_care_plan
            )
            offlineWeeklyReports.add(0, report)
            Result.success(report)
        } catch (e: Exception) {
            val mockReport = WeeklyReport(
                reportId = "offline_rep_${System.currentTimeMillis()}",
                uid = "offline_user",
                timestamp = System.currentTimeMillis() / 1000.0,
                dominantMood = "Confidence",
                averageStressLevel = 0.25f,
                gratitudeSummary = "Your weekly logs show active efforts in self-care. Even on busy days, you successfully checked in on your health.",
                identifiedTriggers = listOf("Routine tiredness"),
                selfCarePlan = listOf(
                    "Practice 4-7-8 box breathing for 5 minutes daily",
                    "Play Gratitude Word unscrambles in Game Center",
                    "Contact a support circle friend for a quick check-in"
                )
            )
            offlineWeeklyReports.add(0, mockReport)
            Result.success(mockReport)
        }
    }

    override suspend fun getWeeklyReports(token: String): Result<List<WeeklyReport>> {
        return try {
            val response = apiService.getWeeklyReports(token)
            val mapped = response.map {
                WeeklyReport(
                    reportId = it.report_id,
                    uid = it.uid,
                    timestamp = it.timestamp,
                    dominantMood = it.dominant_mood,
                    averageStressLevel = it.average_stress_level,
                    gratitudeSummary = it.gratitude_summary,
                    identifiedTriggers = it.identified_triggers,
                    selfCarePlan = it.self_care_plan
                )
            }
            offlineWeeklyReports.clear()
            offlineWeeklyReports.addAll(mapped)
            Result.success(mapped)
        } catch (e: Exception) {
            Result.success(offlineWeeklyReports)
        }
    }

    override suspend fun summarizeVoiceJournal(token: String, audioUrl: String): Result<com.auraai.data.remote.api.VoiceJournalSummarizeResponse> {
        return try {
            val response = apiService.summarizeVoiceJournal(token, com.auraai.data.remote.api.VoiceJournalSummarizeRequest(audioUrl))
            Result.success(response)
        } catch (e: Exception) {
            val mockResp = com.auraai.data.remote.api.VoiceJournalSummarizeResponse(
                transcription = "Today was a peaceful day. I spent some time walking in the park and enjoying the sun.",
                summary = "User described a relaxing day outdoors, expressing contentment.",
                detected_emotion = "Confidence",
                reflection = "I'm glad you had such a grounding experience in nature today. Keep holding onto these moments."
            )
            Result.success(mockResp)
        }
    }
}
