package com.auraai.ui.mood

import com.auraai.domain.model.MoodLog
import com.auraai.domain.usecase.AnalyzeMoodUseCase
import com.auraai.domain.usecase.CacheMoodLocallyUseCase
import com.auraai.domain.usecase.GetMoodHistoryUseCase
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
class MoodViewModelTest {

    @Mock
    private lateinit var analyzeMoodUseCase: AnalyzeMoodUseCase
    @Mock
    private lateinit var getMoodHistoryUseCase: GetMoodHistoryUseCase
    @Mock
    private lateinit var cacheMoodLocallyUseCase: CacheMoodLocallyUseCase
    @Mock
    private lateinit var getTokenUseCase: GetCurrentUserTokenUseCase

    private lateinit var viewModel: MoodViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)

        viewModel = MoodViewModel(
            analyzeMoodUseCase,
            getMoodHistoryUseCase,
            cacheMoodLocallyUseCase,
            getTokenUseCase
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun analyzeAndLogMood_success_updatesLastAnalysisResult() = runTest {
        val token = "mock_token"
        val text = "I feel happy today!"
        val mockMood = MoodLog(
            logId = "log123",
            uid = "user123",
            timestamp = 123456789L,
            primaryEmotion = "Happiness",
            confidenceScore = 0.9f,
            stressLevel = 0.1f,
            anxietyLevel = 0.1f,
            sadnessLevel = 0.1f,
            angerLevel = 0.1f,
            happinessLevel = 0.9f,
            confidenceLevel = 0.8f,
            suggestedActivities = listOf("Share joy with a buddy")
        )

        `when`(getTokenUseCase()).thenReturn(Result.success(token))
        `when`(analyzeMoodUseCase(token, text)).thenReturn(Result.success(mockMood))

        viewModel.analyzeAndLogMood(text, "user123")
        advanceUntilIdle()

        verify(getTokenUseCase).invoke()
        verify(analyzeMoodUseCase).invoke(token, text)
        assertEquals(mockMood, viewModel.lastAnalysisResult.value)
    }
}
