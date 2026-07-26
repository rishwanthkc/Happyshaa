package com.auraai.domain.model

data class JournalEntry(
    val journalId: String,
    val uid: String,
    val timestamp: Double,
    val title: String?,
    val content: String,
    val detectedEmotion: String,
    val emotionConfidence: Float,
    val audioUrl: String?,
    val reflection: String,
    val gratitudeHighlights: List<String>,
    val triggers: List<String>
)

data class WeeklyReport(
    val reportId: String,
    val uid: String,
    val timestamp: Double,
    val dominantMood: String,
    val averageStressLevel: Float,
    val gratitudeSummary: String,
    val identifiedTriggers: List<String>,
    val selfCarePlan: List<String>
)
