package com.auraai.data.repository

import com.auraai.data.remote.api.AuraApiService
import com.auraai.data.remote.api.MeditationSessionRequest
import com.auraai.domain.model.MeditationHistory
import com.auraai.domain.model.MeditationSession
import com.auraai.domain.repository.MeditationRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MeditationRepositoryImpl @Inject constructor(
    private val apiService: AuraApiService
) : MeditationRepository {

    private val offlineSessions = mutableListOf<MeditationSession>()

    override suspend fun submitSession(
        token: String,
        breathingType: String,
        durationSeconds: Int,
        coinsReward: Int,
        xpReward: Int
    ): Result<MeditationSession> {
        return try {
            val response = apiService.submitMeditationSession(
                token = "Bearer $token",
                request = MeditationSessionRequest(breathingType, durationSeconds, coinsReward, xpReward)
            )
            val session = MeditationSession(
                sessionId = response.session_id,
                uid = response.uid,
                breathingType = response.breathing_type,
                durationSeconds = response.duration_seconds,
                timestamp = response.timestamp.toLong(),
                coinsReward = response.coins_reward,
                xpReward = response.xp_reward
            )
            offlineSessions.add(0, session)
            Result.success(session)
        } catch (e: Exception) {
            val mockSession = MeditationSession(
                sessionId = "offline_med_${System.currentTimeMillis()}",
                uid = "offline_user",
                breathingType = breathingType,
                durationSeconds = durationSeconds,
                timestamp = System.currentTimeMillis() / 1000,
                coinsReward = coinsReward,
                xpReward = xpReward
            )
            offlineSessions.add(0, mockSession)
            Result.success(mockSession)
        }
    }

    override suspend fun getHistory(token: String): Result<MeditationHistory> {
        return try {
            val response = apiService.getMeditationHistory("Bearer $token")
            val sessions = response.sessions.map {
                MeditationSession(
                    sessionId = it.session_id,
                    uid = it.uid,
                    breathingType = it.breathing_type,
                    durationSeconds = it.duration_seconds,
                    timestamp = it.timestamp.toLong(),
                    coinsReward = it.coins_reward,
                    xpReward = it.xp_reward
                )
            }
            Result.success(
                MeditationHistory(
                    sessions = sessions,
                    streak = response.streak,
                    totalXp = response.total_xp,
                    totalCoins = response.total_coins
                )
            )
        } catch (e: Exception) {
            val totalXp = offlineSessions.sumOf { it.xpReward }
            val totalCoins = offlineSessions.sumOf { it.coinsReward }
            Result.success(
                MeditationHistory(
                    sessions = offlineSessions,
                    streak = minOf(offlineSessions.size, 7),
                    totalXp = totalXp,
                    totalCoins = totalCoins
                )
            )
        }
    }
}
