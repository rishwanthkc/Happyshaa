package com.auraai.domain.usecase

import com.auraai.domain.model.RecommendationCard
import com.auraai.domain.repository.RecommendationRepository
import javax.inject.Inject

data class RecommendationUseCases(
    val getDailyRecommendations: GetDailyRecommendationsUseCase
)

class GetDailyRecommendationsUseCase @Inject constructor(private val repo: RecommendationRepository) {
    suspend operator fun invoke(token: String): Result<List<RecommendationCard>> = repo.getDailyRecommendations(token)
}
