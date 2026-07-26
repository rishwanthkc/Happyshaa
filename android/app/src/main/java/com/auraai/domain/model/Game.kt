package com.auraai.domain.model

data class Game(
    val gameId: String,
    val title: String,
    val description: String,
    val category: String
)

data class Achievement(
    val achievementId: String,
    val uid: String,
    val achievementType: String,
    val timestamp: Double
)

data class GameProgress(
    val scoreId: String,
    val xpEarned: Int,
    val coinsEarned: Int,
    val newBalance: Int,
    val unlockedAchievement: String?
)
