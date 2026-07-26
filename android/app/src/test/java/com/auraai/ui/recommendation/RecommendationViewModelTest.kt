package com.auraai.ui.recommendation

import com.auraai.domain.model.RecommendationCard
import com.auraai.domain.usecase.GetDailyRecommendationsUseCase
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

@OptIn(ExperimentalCoroutinesApi::class)
class RecommendationViewModelTest {

    @Mock
    private lateinit var getRecommendationsUseCase: GetDailyRecommendationsUseCase
    @Mock
    private lateinit var getTokenUseCase: GetCurrentUserTokenUseCase

    private lateinit var viewModel: RecommendationViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)

        viewModel = RecommendationViewModel(
            getRecommendationsUseCase,
            getTokenUseCase
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun loadRecommendations_success_updatesRecommendationsState() = runTest {
        val token = "mock_token"
        val mockCards = listOf(RecommendationCard("card1", "Title", "Desc", "MUSIC", "music_player", "Easy", 10))

        `when`(getTokenUseCase()).thenReturn(Result.success(token))
        `when`(getRecommendationsUseCase(token)).thenReturn(Result.success(mockCards))

        viewModel.loadRecommendations()
        advanceUntilIdle()

        verify(getRecommendationsUseCase).invoke(token)
        assertEquals(mockCards, viewModel.recommendations.value)
    }
}
