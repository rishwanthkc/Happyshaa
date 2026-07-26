package com.auraai.domain.usecase

import com.auraai.domain.model.MusicTrack
import com.auraai.domain.repository.MusicRepository
import javax.inject.Inject

data class MusicUseCases(
    val getSongs: GetSongsUseCase,
    val toggleFavoriteSong: ToggleFavoriteSongUseCase,
    val getFavoriteSongs: GetFavoriteSongsUseCase,
    val logPlaybackHistory: LogPlaybackHistoryUseCase,
    val getMusicRecommendations: GetMusicRecommendationsUseCase
)

class GetSongsUseCase @Inject constructor(private val repo: MusicRepository) {
    suspend operator fun invoke(token: String): Result<List<MusicTrack>> = repo.getSongs(token)
}

class ToggleFavoriteSongUseCase @Inject constructor(private val repo: MusicRepository) {
    suspend operator fun invoke(token: String, songId: String): Result<Boolean> = repo.toggleFavoriteSong(token, songId)
}

class GetFavoriteSongsUseCase @Inject constructor(private val repo: MusicRepository) {
    suspend operator fun invoke(token: String): Result<List<MusicTrack>> = repo.getFavoriteSongs(token)
}

class LogPlaybackHistoryUseCase @Inject constructor(private val repo: MusicRepository) {
    suspend operator fun invoke(token: String, songId: String, durationSec: Int): Result<Unit> = repo.logPlaybackHistory(token, songId, durationSec)
}

class GetMusicRecommendationsUseCase @Inject constructor(private val repo: MusicRepository) {
    suspend operator fun invoke(token: String): Result<List<MusicTrack>> = repo.getMusicRecommendations(token)
}
