package com.auraai.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cached_stories")
data class CachedStoryEntity(
    @PrimaryKey
    val storyId: String,
    val uid: String,
    val title: String,
    val content: String,
    val category: String,
    val length: String,
    val timestamp: Long,
    val isFavorite: Boolean
)
