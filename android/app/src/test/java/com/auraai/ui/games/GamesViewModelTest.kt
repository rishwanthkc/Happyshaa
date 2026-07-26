package com.auraai.ui.games

import com.auraai.domain.model.Game
import com.auraai.domain.model.Achievement
import com.auraai.domain.model.GameProgress
import com.auraai.domain.usecase.GetGamesUseCase
import com.auraai.domain.usecase.SubmitGameScoreUseCase
import com.auraai.domain.usecase.GetAchievementsUseCase
import com.auraai.domain.usecase.GetCurrentUserTokenUseCase
import com.auraai.data.local.db.QuestDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
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
class GamesViewModelTest {

    @Mock
    private lateinit var getGamesUseCase: GetGamesUseCase
    @Mock
    private lateinit var submitGameScoreUseCase: SubmitGameScoreUseCase
    @Mock
    private lateinit var getAchievementsUseCase: GetAchievementsUseCase
    @Mock
    private lateinit var getTokenUseCase: GetCurrentUserTokenUseCase
    @Mock
    private lateinit var questDao: QuestDao

    private lateinit var viewModel: GamesViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)

        // Mock getAllQuests to prevent NullPointerException on initialization
        `when`(questDao.getAllQuests()).thenReturn(flowOf(emptyList()))

        viewModel = GamesViewModel(
            getGamesUseCase,
            submitGameScoreUseCase,
            getAchievementsUseCase,
            getTokenUseCase,
            questDao
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun loadGameCenter_success_updatesGamesState() = runTest {
        val token = "mock_token"
        val mockGames = listOf(Game("bubblepop", "Bubble Pop", "Desc", "Relax"))
        val mockAchievements = listOf(Achievement("ach1", "uid", "MINDFUL_GAMER", 123.0))

        `when`(getTokenUseCase()).thenReturn(Result.success(token))
        `when`(getGamesUseCase(token)).thenReturn(Result.success(mockGames))
        `when`(getAchievementsUseCase(token)).thenReturn(Result.success(mockAchievements))

        viewModel.loadGameCenter()
        advanceUntilIdle()

        verify(getGamesUseCase).invoke(token)
        verify(getAchievementsUseCase).invoke(token)
        assertEquals(mockGames, viewModel.games.value)
        assertEquals(mockAchievements, viewModel.achievements.value)
    }

    @Test
    fun submitScore_success_emitsProgressEvent() = runTest {
        val token = "mock_token"
        val progress = GameProgress("score_123", 10, 20, 100, "COIN_COLLECTOR")

        `when`(getTokenUseCase()).thenReturn(Result.success(token))
        `when`(submitGameScoreUseCase(token, "bubblepop", 10)).thenReturn(Result.success(progress))
        `when`(getAchievementsUseCase(token)).thenReturn(Result.success(emptyList()))

        viewModel.submitScore("bubblepop", 10)
        advanceUntilIdle()

        verify(submitGameScoreUseCase).invoke(token, "bubblepop", 10)
        assertEquals(progress, viewModel.progressEvent.value)
        assertEquals(100, viewModel.userCoins.value)
    }
}
