package com.auraai.domain.model

data class MeditationSession(
    val sessionId: String,
    val uid: String,
    val breathingType: String,
    val durationSeconds: Int,
    val timestamp: Long,
    val coinsReward: Int,
    val xpReward: Int
)

data class MeditationHistory(
    val sessions: List<MeditationSession>,
    val streak: Int,
    val totalXp: Int,
    val totalCoins: Int
)
