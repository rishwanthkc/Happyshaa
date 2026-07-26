package com.auraai.domain.model

data class MoodLog(
    val logId: String,
    val uid: String,
    val timestamp: Long,
    val primaryEmotion: String,
    val confidenceScore: Float,
    val stressLevel: Float,
    val anxietyLevel: Float,
    val sadnessLevel: Float,
    val angerLevel: Float,
    val happinessLevel: Float,
    val confidenceLevel: Float,
    val suggestedActivities: List<String>
)
