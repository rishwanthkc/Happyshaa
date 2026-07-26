package com.auraai.domain.model

data class MusicTrack(
    val songId: String,
    val title: String,
    val artist: String,
    val url: String,
    val category: String,
    val moodTag: String,
    val isFavorite: Boolean = false
)
