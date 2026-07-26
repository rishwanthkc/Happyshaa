package com.auraai.domain.model

data class RecommendationCard(
    val id: String,
    val title: String,
    val description: String,
    val activityType: String,
    val targetRoute: String,
    val difficulty: String,
    val coinsReward: Int
)
