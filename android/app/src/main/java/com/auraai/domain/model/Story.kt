package com.auraai.domain.model

data class Story(
    val storyId: String,
    val uid: String,
    val title: String,
    val content: String,
    val category: String,
    val length: String,
    val timestamp: Long,
    val isFavorite: Boolean
)
