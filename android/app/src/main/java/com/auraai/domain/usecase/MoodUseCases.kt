package com.auraai.domain.usecase

import com.auraai.domain.model.MoodLog
import com.auraai.domain.repository.MoodRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AnalyzeMoodUseCase @Inject constructor(
    private val repository: MoodRepository
) {
    suspend operator fun invoke(token: String, text: String): Result<MoodLog> {
        return repository.analyzeMood(token, text)
    }
}

class GetMoodHistoryUseCase @Inject constructor(
    private val repository: MoodRepository
) {
    operator fun invoke(uid: String): Flow<List<MoodLog>> {
        return repository.getMoodHistory(uid)
    }
}

class CacheMoodLocallyUseCase @Inject constructor(
    private val repository: MoodRepository
) {
    suspend operator fun invoke(uid: String, score: Int, note: String) {
        repository.cacheMoodLocally(uid, score, note)
    }
}
