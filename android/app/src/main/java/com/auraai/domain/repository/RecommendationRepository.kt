package com.auraai.domain.repository

import com.auraai.domain.model.RecommendationCard

interface RecommendationRepository {
    suspend fun getDailyRecommendations(token: String): Result<List<RecommendationCard>>
}
