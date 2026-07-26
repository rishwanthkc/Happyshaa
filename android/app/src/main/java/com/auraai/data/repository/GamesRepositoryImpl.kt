package com.auraai.data.repository

import com.auraai.data.remote.api.AuraApiService
import com.auraai.data.remote.api.GameScoreSubmitRequest
import com.auraai.domain.model.Game
import com.auraai.domain.model.Achievement
import com.auraai.domain.model.GameProgress
import com.auraai.domain.repository.GamesRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GamesRepositoryImpl @Inject constructor(
    private val apiService: AuraApiService
) : GamesRepository {

    private var localBalance = 0
    private val achievementsLog = mutableListOf<Achievement>()

    private val staticFallbackGames = listOf(
        Game("tictactoe", "Tic Tac Toe", "Relaxing match vs AI", "Focus"),
        Game("puzzle2048", "2048", "Calming numbers match puzzle", "Cognitive"),
        Game("wordpuzzle", "Gratitude Word Anagrams", "Unscramble positive emotions", "Mindfulness"),
        Game("memorymatch", "Memory Match Cards", "Focus memory card flip match", "Focus"),
        Game("sudoku", "Wellness Sudoku", "Calming numeric logical boards", "Cognitive"),
        Game("bubblepop", "Stress Bubble Pop", "Calming float bubble popping", "Relaxation"),
        Game("coloring", "Coloring Canvas", "Zen calming coloring outlines", "Relaxation")
    )

    override suspend fun getGames(token: String): Result<List<Game>> {
        return try {
            val response = apiService.getGames(token)
            val mapped = response.map {
                Game(it.game_id, it.title, it.description, it.category)
            }
            Result.success(mapped)
        } catch (e: Exception) {
            Result.success(staticFallbackGames)
        }
    }

    override suspend fun submitGameScore(token: String, gameId: String, score: Int): Result<GameProgress> {
        return try {
            val response = apiService.submitGameScore(token, GameScoreSubmitRequest(gameId, score))
            val progress = GameProgress(
                scoreId = response.score_id,
                xpEarned = response.xp_earned,
                coinsEarned = response.coins_earned,
                newBalance = response.new_balance,
                unlockedAchievement = response.unlocked_achievement
            )
            localBalance = response.new_balance
            response.unlocked_achievement?.let {
                achievementsLog.add(Achievement("ach_${System.currentTimeMillis()}", "uid", it, System.currentTimeMillis() / 1000.0))
            }
            Result.success(progress)
        } catch (e: Exception) {
            val earnedCoins = score * 2
            localBalance += earnedCoins
            
            // Check achievement rules locally
            var unlock: String? = null
            if (localBalance >= 100 && achievementsLog.none { it.achievementType == "COIN_COLLECTOR" }) {
                unlock = "COIN_COLLECTOR"
                achievementsLog.add(Achievement("ach_${System.currentTimeMillis()}", "uid", "COIN_COLLECTOR", System.currentTimeMillis() / 1000.0))
            }

            Result.success(
                GameProgress(
                    scoreId = "offline_score_${System.currentTimeMillis()}",
                    xpEarned = 10,
                    coinsEarned = earnedCoins,
                    newBalance = localBalance,
                    unlockedAchievement = unlock
                )
            )
        }
    }

    override suspend fun getAchievements(token: String): Result<List<Achievement>> {
        return try {
            val response = apiService.getAchievements(token)
            val mapped = response.map {
                Achievement(it.achievement_id, it.uid, it.achievement_type, it.timestamp)
            }
            Result.success(mapped)
        } catch (e: Exception) {
            Result.success(achievementsLog)
        }
    }
}
