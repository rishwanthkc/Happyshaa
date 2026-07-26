package com.auraai.ui.music

import com.auraai.domain.model.MusicTrack
import com.auraai.domain.usecase.GetSongsUseCase
import com.auraai.domain.usecase.ToggleFavoriteSongUseCase
import com.auraai.domain.usecase.GetFavoriteSongsUseCase
import com.auraai.domain.usecase.LogPlaybackHistoryUseCase
import com.auraai.domain.usecase.GetMusicRecommendationsUseCase
import com.auraai.domain.usecase.GetCurrentUserTokenUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import android.content.Context

@OptIn(ExperimentalCoroutinesApi::class)
class MusicViewModelTest {

    @Mock
    private lateinit var getSongsUseCase: GetSongsUseCase
    @Mock
    private lateinit var toggleFavoriteSongUseCase: ToggleFavoriteSongUseCase
    @Mock
    private lateinit var getFavoriteSongsUseCase: GetFavoriteSongsUseCase
    @Mock
    private lateinit var logPlaybackHistoryUseCase: LogPlaybackHistoryUseCase
    @Mock
    private lateinit var getMusicRecommendationsUseCase: GetMusicRecommendationsUseCase
    @Mock
    private lateinit var getTokenUseCase: GetCurrentUserTokenUseCase
    @Mock
    private lateinit var context: Context

    private lateinit var viewModel: MusicViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)

        `when`(context.packageName).thenReturn("com.auraai")

        viewModel = MusicViewModel(
            getSongsUseCase,
            toggleFavoriteSongUseCase,
            getFavoriteSongsUseCase,
            logPlaybackHistoryUseCase,
            getMusicRecommendationsUseCase,
            getTokenUseCase,
            context
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun loadAllMusic_success_populatesSongsAndFavorites() = runTest {
        val token = "mock_token"
        val songsList = listOf(MusicTrack("track1", "Calm Rain", "Artist", "url", "Nature", "Stress"))
        val favoritesList = listOf(MusicTrack("track1", "Calm Rain", "Artist", "url", "Nature", "Stress", true))

        `when`(getTokenUseCase()).thenReturn(Result.success(token))
        `when`(getSongsUseCase(token)).thenReturn(Result.success(songsList))
        `when`(getFavoriteSongsUseCase(token)).thenReturn(Result.success(favoritesList))
        `when`(getMusicRecommendationsUseCase(token)).thenReturn(Result.success(songsList))

        viewModel.loadAllMusic()
        advanceUntilIdle()

        verify(getTokenUseCase).invoke()
        verify(getSongsUseCase).invoke(token)
        assertEquals(songsList, viewModel.songs.value)
        assertEquals(favoritesList, viewModel.favorites.value)
    }

    @Test
    fun toggleFavorite_success_callsUseCase() = runTest {
        val token = "mock_token"
        val track = MusicTrack("track1", "Calm Rain", "Artist", "url", "Nature", "Stress")

        `when`(getTokenUseCase()).thenReturn(Result.success(token))
        `when`(toggleFavoriteSongUseCase(token, "track1")).thenReturn(Result.success(true))
        `when`(getFavoriteSongsUseCase(token)).thenReturn(Result.success(listOf(track.copy(isFavorite = true))))

        viewModel.toggleFavorite(track)
        advanceUntilIdle()

        verify(toggleFavoriteSongUseCase).invoke(token, "track1")
    }
}
