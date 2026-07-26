package com.auraai.domain.usecase

import com.auraai.domain.model.MeditationHistory
import com.auraai.domain.model.MeditationSession
import com.auraai.domain.repository.MeditationRepository
import javax.inject.Inject

class MeditationUseCases @Inject constructor(
    val submitSession: SubmitMeditationSessionUseCase,
    val getHistory: GetMeditationHistoryUseCase
)

class SubmitMeditationSessionUseCase @Inject constructor(
    private val repository: MeditationRepository
) {
    suspend operator fun invoke(
        token: String,
        breathingType: String,
        durationSeconds: Int,
        coinsReward: Int,
        xpReward: Int
    ): Result<MeditationSession> {
        return repository.submitSession(token, breathingType, durationSeconds, coinsReward, xpReward)
    }
}

class GetMeditationHistoryUseCase @Inject constructor(
    private val repository: MeditationRepository
) {
    suspend operator fun invoke(token: String): Result<MeditationHistory> {
        return repository.getHistory(token)
    }
}
