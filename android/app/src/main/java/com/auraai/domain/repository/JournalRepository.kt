package com.auraai.domain.repository

import com.auraai.domain.model.JournalEntry
import com.auraai.domain.model.WeeklyReport
import com.auraai.data.remote.api.VoiceJournalSummarizeResponse

interface JournalRepository {
    suspend fun createJournalEntry(token: String, title: String, content: String, audioUrl: String?): Result<JournalEntry>
    suspend fun getJournals(token: String): Result<List<JournalEntry>>
    suspend fun generateWeeklyReport(token: String): Result<WeeklyReport>
    suspend fun getWeeklyReports(token: String): Result<List<WeeklyReport>>
    suspend fun summarizeVoiceJournal(token: String, audioUrl: String): Result<VoiceJournalSummarizeResponse>
}
