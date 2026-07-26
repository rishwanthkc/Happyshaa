package com.auraai.ui.music

import android.content.ComponentName
import android.content.Context
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.Player
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.auraai.domain.model.MusicTrack
import com.auraai.domain.usecase.GetSongsUseCase
import com.auraai.domain.usecase.ToggleFavoriteSongUseCase
import com.auraai.domain.usecase.GetFavoriteSongsUseCase
import com.auraai.domain.usecase.LogPlaybackHistoryUseCase
import com.auraai.domain.usecase.GetMusicRecommendationsUseCase
import com.auraai.domain.usecase.GetCurrentUserTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MusicViewModel @Inject constructor(
    private val getSongsUseCase: GetSongsUseCase,
    private val toggleFavoriteSongUseCase: ToggleFavoriteSongUseCase,
    private val getFavoriteSongsUseCase: GetFavoriteSongsUseCase,
    private val logPlaybackHistoryUseCase: LogPlaybackHistoryUseCase,
    private val getMusicRecommendationsUseCase: GetMusicRecommendationsUseCase,
    private val getTokenUseCase: GetCurrentUserTokenUseCase,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _songs = MutableStateFlow<List<MusicTrack>>(emptyList())
    val songs: StateFlow<List<MusicTrack>> = _songs.asStateFlow()

    private val _favorites = MutableStateFlow<List<MusicTrack>>(emptyList())
    val favorites: StateFlow<List<MusicTrack>> = _favorites.asStateFlow()

    private val _recommendations = MutableStateFlow<List<MusicTrack>>(emptyList())
    val recommendations: StateFlow<List<MusicTrack>> = _recommendations.asStateFlow()

    private val _currentTrack = MutableStateFlow<MusicTrack?>(null)
    val currentTrack: StateFlow<MusicTrack?> = _currentTrack.asStateFlow()

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying.asStateFlow()

    private val _playbackPosition = MutableStateFlow(0L)
    val playbackPosition: StateFlow<Long> = _playbackPosition.asStateFlow()

    private val _trackDuration = MutableStateFlow(180L) // Default 3 mins
    val trackDuration: StateFlow<Long> = _trackDuration.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    // Media3 controller instance
    private var mediaController: MediaController? = null

    // Sleep Timer
    private var sleepTimerJob: Job? = null
    private val _sleepTimerRemaining = MutableStateFlow(0)
    val sleepTimerRemaining: StateFlow<Int> = _sleepTimerRemaining.asStateFlow()

    init {
        initializeMediaController()
    }

    private fun initializeMediaController() {
        try {
            val sessionToken = SessionToken(context, ComponentName(context, AuraMusicService::class.java))
            val controllerFuture = MediaController.Builder(context, sessionToken).buildAsync()
            controllerFuture.addListener({
                try {
                    mediaController = controllerFuture.get()
                    setupControllerListener()
                } catch (e: Exception) {
                    _errorMessage.value = "Failed to link media service: ${e.localizedMessage}"
                }
            }, ContextCompat.getMainExecutor(context))
        } catch (e: Exception) {
            // Resilient fallback for unit tests
        }
    }

    private fun setupControllerListener() {
        val controller = mediaController ?: return
        
        _isPlaying.value = controller.isPlaying
        _currentTrack.value = getCurrentTrackFromMediaItem(controller.currentMediaItem)
        _trackDuration.value = if (controller.duration > 0) controller.duration / 1000 else 180L

        controller.addListener(object : Player.Listener {
            override fun onIsPlayingChanged(isPlaying: Boolean) {
                _isPlaying.value = isPlaying
            }

            override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
                _currentTrack.value = getCurrentTrackFromMediaItem(mediaItem)
                _trackDuration.value = if (controller.duration > 0) controller.duration / 1000 else 180L
                
                // Log playback history on track change
                mediaItem?.mediaId?.let { songId ->
                    logPlaybackFinished(songId, 30) // assume threshold playback elapsed
                }
            }

            override fun onPlaybackStateChanged(state: Int) {
                _trackDuration.value = if (controller.duration > 0) controller.duration / 1000 else 180L
            }
        })

        // Poll position
        viewModelScope.launch {
            while (true) {
                if (controller.isPlaying) {
                    _playbackPosition.value = controller.currentPosition / 1000
                }
                delay(1000)
            }
        }
    }

    private fun getCurrentTrackFromMediaItem(mediaItem: MediaItem?): MusicTrack? {
        val item = mediaItem ?: return null
        return _songs.value.find { it.songId == item.mediaId }
            ?: MusicTrack(
                songId = item.mediaId,
                title = item.mediaMetadata.title?.toString() ?: "Zen Frequency",
                artist = item.mediaMetadata.artist?.toString() ?: "Aura AI",
                url = "",
                category = "Sleep",
                moodTag = "Calm",
                isFavorite = false
            )
    }

    fun loadAllMusic() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            getTokenUseCase().onSuccess { token ->
                val songsResult = getSongsUseCase(token)
                val favsResult = getFavoriteSongsUseCase(token)
                val recsResult = getMusicRecommendationsUseCase(token)

                songsResult.onSuccess { _songs.value = it }
                favsResult.onSuccess { _favorites.value = it }
                recsResult.onSuccess { _recommendations.value = it }
            }.onFailure {
                _errorMessage.value = "Auth token lookup failed: ${it.localizedMessage}"
            }
            _isLoading.value = false
        }
    }

    fun toggleFavorite(track: MusicTrack) {
        viewModelScope.launch {
            getTokenUseCase().onSuccess { token ->
                toggleFavoriteSongUseCase(token, track.songId).onSuccess { isFav ->
                    _songs.value = _songs.value.map {
                        if (it.songId == track.songId) it.copy(isFavorite = isFav) else it
                    }
                    _recommendations.value = _recommendations.value.map {
                        if (it.songId == track.songId) it.copy(isFavorite = isFav) else it
                    }
                    getFavoriteSongsUseCase(token).onSuccess { _favorites.value = it }
                }
            }
        }
    }

    fun playTrack(track: MusicTrack) {
        val controller = mediaController ?: return
        val index = _songs.value.indexOfFirst { it.songId == track.songId }
        
        if (index != -1) {
            val mediaItems = _songs.value.map { song ->
                MediaItem.Builder()
                    .setMediaId(song.songId)
                    .setUri(song.url)
                    .setMediaMetadata(
                        MediaMetadata.Builder()
                            .setTitle(song.title)
                            .setArtist(song.artist)
                            .build()
                    )
                    .build()
            }
            controller.setMediaItems(mediaItems)
            controller.seekTo(index, 0L)
            controller.prepare()
            controller.play()
        } else {
            val mediaItem = MediaItem.Builder()
                .setMediaId(track.songId)
                .setUri(track.url)
                .setMediaMetadata(
                    MediaMetadata.Builder()
                        .setTitle(track.title)
                        .setArtist(track.artist)
                        .build()
                )
                .build()
            controller.setMediaItem(mediaItem)
            controller.prepare()
            controller.play()
        }
        _currentTrack.value = track
        _isPlaying.value = true
    }

    fun pauseTrack() {
        mediaController?.pause()
        _isPlaying.value = false
    }

    fun resumeTrack() {
        mediaController?.play()
        _isPlaying.value = true
    }

    fun seekTo(seconds: Long) {
        mediaController?.seekTo(seconds * 1000)
        _playbackPosition.value = seconds
    }

    fun playNext() {
        mediaController?.seekToNextMediaItem()
    }

    fun playPrevious() {
        mediaController?.seekToPreviousMediaItem()
    }

    fun toggleShuffle() {
        val controller = mediaController ?: return
        controller.shuffleModeEnabled = !controller.shuffleModeEnabled
    }

    fun toggleRepeat() {
        val controller = mediaController ?: return
        controller.repeatMode = if (controller.repeatMode == Player.REPEAT_MODE_OFF) {
            Player.REPEAT_MODE_ALL
        } else {
            Player.REPEAT_MODE_OFF
        }
    }

    fun startSleepTimer(minutes: Int) {
        sleepTimerJob?.cancel()
        _sleepTimerRemaining.value = minutes * 60
        sleepTimerJob = viewModelScope.launch {
            while (_sleepTimerRemaining.value > 0) {
                delay(1000)
                _sleepTimerRemaining.value = _sleepTimerRemaining.value - 1
            }
            pauseTrack()
        }
    }

    fun stopSleepTimer() {
        sleepTimerJob?.cancel()
        _sleepTimerRemaining.value = 0
    }

    fun setDuration(seconds: Long) {
        _trackDuration.value = seconds
    }

    fun logPlaybackFinished(songId: String, durationSec: Int) {
        viewModelScope.launch {
            getTokenUseCase().onSuccess { token ->
                logPlaybackHistoryUseCase(token, songId, durationSec)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        sleepTimerJob?.cancel()
        mediaController?.release()
    }
}
