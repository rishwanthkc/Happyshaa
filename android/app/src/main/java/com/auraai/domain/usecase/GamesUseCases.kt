package com.auraai.domain.usecase

import com.auraai.domain.model.Game
import com.auraai.domain.model.Achievement
import com.auraai.domain.model.GameProgress
import com.auraai.domain.repository.GamesRepository
import javax.inject.Inject

data class GamesUseCases(
    val getGames: GetGamesUseCase,
    val submitGameScore: SubmitGameScoreUseCase,
    val getAchievements: GetAchievementsUseCase
)

class GetGamesUseCase @Inject constructor(private val repo: GamesRepository) {
    suspend operator fun invoke(token: String): Result<List<Game>> = repo.getGames(token)
}

class SubmitGameScoreUseCase @Inject constructor(private val repo: GamesRepository) {
    suspend operator fun invoke(token: String, gameId: String, score: Int): Result<GameProgress> = repo.submitGameScore(token, gameId, score)
}

class GetAchievementsUseCase @Inject constructor(private val repo: GamesRepository) {
    suspend operator fun invoke(token: String): Result<List<Achievement>> = repo.getAchievements(token)
}
