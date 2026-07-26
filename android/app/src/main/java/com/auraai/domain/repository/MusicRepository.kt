package com.auraai.domain.repository

import com.auraai.domain.model.MusicTrack

interface MusicRepository {
    suspend fun getSongs(token: String): Result<List<MusicTrack>>
    suspend fun toggleFavoriteSong(token: String, songId: String): Result<Boolean>
    suspend fun getFavoriteSongs(token: String): Result<List<MusicTrack>>
    suspend fun logPlaybackHistory(token: String, songId: String, durationSec: Int): Result<Unit>
    suspend fun getMusicRecommendations(token: String): Result<List<MusicTrack>>
}
