package com.auraai.domain.repository

import com.auraai.domain.model.MeditationHistory
import com.auraai.domain.model.MeditationSession

interface MeditationRepository {
    suspend fun submitSession(
        token: String,
        breathingType: String,
        durationSeconds: Int,
        coinsReward: Int,
        xpReward: Int
    ): Result<MeditationSession>

    suspend fun getHistory(token: String): Result<MeditationHistory>
}
