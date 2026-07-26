package com.auraai.domain.usecase

import com.auraai.domain.model.JournalEntry
import com.auraai.domain.model.WeeklyReport
import com.auraai.domain.repository.JournalRepository
import javax.inject.Inject

data class JournalUseCases(
    val createJournalEntry: CreateJournalEntryUseCase,
    val getJournals: GetJournalsUseCase,
    val generateWeeklyReport: GenerateWeeklyReportUseCase,
    val getWeeklyReports: GetWeeklyReportsUseCase
)

class CreateJournalEntryUseCase @Inject constructor(private val repo: JournalRepository) {
    suspend operator fun invoke(token: String, title: String, content: String, audioUrl: String?): Result<JournalEntry> = repo.createJournalEntry(token, title, content, audioUrl)
}

class GetJournalsUseCase @Inject constructor(private val repo: JournalRepository) {
    suspend operator fun invoke(token: String): Result<List<JournalEntry>> = repo.getJournals(token)
}

class GenerateWeeklyReportUseCase @Inject constructor(private val repo: JournalRepository) {
    suspend operator fun invoke(token: String): Result<WeeklyReport> = repo.generateWeeklyReport(token)
}

class GetWeeklyReportsUseCase @Inject constructor(private val repo: JournalRepository) {
    suspend operator fun invoke(token: String): Result<List<WeeklyReport>> = repo.getWeeklyReports(token)
}

class SummarizeVoiceJournalUseCase @Inject constructor(private val repo: JournalRepository) {
    suspend operator fun invoke(token: String, audioUrl: String): Result<com.auraai.data.remote.api.VoiceJournalSummarizeResponse> = repo.summarizeVoiceJournal(token, audioUrl)
}
