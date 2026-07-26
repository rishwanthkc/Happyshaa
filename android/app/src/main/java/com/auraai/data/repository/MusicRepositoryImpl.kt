package com.auraai.data.repository

import com.auraai.data.remote.api.AuraApiService
import com.auraai.data.remote.api.FavoriteToggleRequest
import com.auraai.data.remote.api.HistoryLogRequest
import com.auraai.domain.model.MusicTrack
import com.auraai.domain.repository.MusicRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MusicRepositoryImpl @Inject constructor(
    private val apiService: AuraApiService
) : MusicRepository {

    // Simple offline local favorite caching
    private val offlineFavorites = mutableSetOf<String>()

    private val staticFallbackCatalog = listOf(
        MusicTrack("nature_rain", "Soft Rainfall", "Nature Sounds", "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3", "Nature", "Stress"),
        MusicTrack("lofi_focus", "Midnight Study", "Lofi Beats", "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-2.mp3", "Lofi", "Anxiety"),
        MusicTrack("binaural_relax", "Theta Meditation Waves", "Binaural Mind", "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-3.mp3", "Binaural Beats", "Sadness"),
        MusicTrack("calm_ocean", "Ocean Whispers", "Nature Sounds", "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-4.mp3", "Nature", "Anger")
    )

    override suspend fun getSongs(token: String): Result<List<MusicTrack>> {
        return try {
            val response = apiService.getSongs(token)
            val mapped = response.map {
                MusicTrack(
                    songId = it.song_id,
                    title = it.title,
                    artist = it.artist,
                    url = it.url,
                    category = it.category,
                    moodTag = it.mood_tag,
                    isFavorite = offlineFavorites.contains(it.song_id)
                )
            }
            Result.success(mapped)
        } catch (e: Exception) {
            // Offline fallback
            val items = staticFallbackCatalog.map {
                it.copy(isFavorite = offlineFavorites.contains(it.songId))
            }
            Result.success(items)
        }
    }

    override suspend fun toggleFavoriteSong(token: String, songId: String): Result<Boolean> {
        return try {
            apiService.toggleFavoriteSong(token, FavoriteToggleRequest(songId))
            if (offlineFavorites.contains(songId)) {
                offlineFavorites.remove(songId)
                Result.success(false)
            } else {
                offlineFavorites.add(songId)
                Result.success(true)
            }
        } catch (e: Exception) {
            if (offlineFavorites.contains(songId)) {
                offlineFavorites.remove(songId)
                Result.success(false)
            } else {
                offlineFavorites.add(songId)
                Result.success(true)
            }
        }
    }

    override suspend fun getFavoriteSongs(token: String): Result<List<MusicTrack>> {
        return try {
            val response = apiService.getFavoriteSongs(token)
            val mapped = response.map {
                MusicTrack(
                    songId = it.song_id,
                    title = it.title,
                    artist = it.artist,
                    url = it.url,
                    category = it.category,
                    moodTag = it.mood_tag,
                    isFavorite = true
                )
            }
            // Sync local favorite status
            offlineFavorites.addAll(mapped.map { it.songId })
            Result.success(mapped)
        } catch (e: Exception) {
            val favs = staticFallbackCatalog.filter { offlineFavorites.contains(it.songId) }.map {
                it.copy(isFavorite = true)
            }
            Result.success(favs)
        }
    }

    override suspend fun logPlaybackHistory(token: String, songId: String, durationSec: Int): Result<Unit> {
        return try {
            apiService.logPlaybackHistory(token, HistoryLogRequest(songId, durationSec))
            Result.success(Unit)
        } catch (e: Exception) {
            Result.success(Unit)
        }
    }

    override suspend fun getMusicRecommendations(token: String): Result<List<MusicTrack>> {
        return try {
            val response = apiService.getMusicRecommendations(token)
            val mapped = response.map {
                MusicTrack(
                    songId = it.song_id,
                    title = it.title,
                    artist = it.artist,
                    url = it.url,
                    category = it.category,
                    moodTag = it.mood_tag,
                    isFavorite = offlineFavorites.contains(it.song_id)
                )
            }
            Result.success(mapped)
        } catch (e: Exception) {
            Result.success(staticFallbackCatalog.take(2).map {
                it.copy(isFavorite = offlineFavorites.contains(it.songId))
            })
        }
    }
}
