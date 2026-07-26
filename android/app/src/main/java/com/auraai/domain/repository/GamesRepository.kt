package com.auraai.domain.repository

import com.auraai.domain.model.Game
import com.auraai.domain.model.Achievement
import com.auraai.domain.model.GameProgress

interface GamesRepository {
    suspend fun getGames(token: String): Result<List<Game>>
    suspend fun submitGameScore(token: String, gameId: String, score: Int): Result<GameProgress>
    suspend fun getAchievements(token: String): Result<List<Achievement>>
}
