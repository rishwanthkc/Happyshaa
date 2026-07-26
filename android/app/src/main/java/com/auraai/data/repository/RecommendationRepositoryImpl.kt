package com.auraai.data.repository

import com.auraai.data.remote.api.AuraApiService
import com.auraai.domain.model.RecommendationCard
import com.auraai.domain.repository.RecommendationRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RecommendationRepositoryImpl @Inject constructor(
    private val apiService: AuraApiService
) : RecommendationRepository {

    private val staticFallbackCards = listOf(
        RecommendationCard("rec_music_calm", "Unwind with Music", "Your stress indicators are slightly elevated. Listen to 'Soft Rainfall' to calm your mind.", "MUSIC", "music_player/nature_rain", "Easy", 5),
        RecommendationCard("rec_game_bubble", "Stress Bubble Pop", "Pop some soothing bubbles on screen to release physical tension and anxiety.", "GAME", "game_center/bubblepop", "Easy", 10),
        RecommendationCard("rec_journal_write", "Reflective Journaling", "Write down one thing that made you smile today to cultivate gratitude.", "JOURNAL", "journal/new", "Medium", 15)
    )

    override suspend fun getDailyRecommendations(token: String): Result<List<RecommendationCard>> {
        return try {
            val response = apiService.getDailyRecommendations(token)
            val mapped = response.map {
                RecommendationCard(
                    id = it.id,
                    title = it.title,
                    description = it.description,
                    activityType = it.activity_type,
                    targetRoute = it.target_route,
                    difficulty = it.difficulty,
                    coinsReward = it.coins_reward
                )
            }
            Result.success(mapped)
        } catch (e: Exception) {
            Result.success(staticFallbackCards)
        }
    }
}
