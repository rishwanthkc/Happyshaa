package com.auraai.ui.journal

import com.auraai.domain.model.JournalEntry
import com.auraai.domain.model.WeeklyReport
import com.auraai.domain.usecase.CreateJournalEntryUseCase
import com.auraai.domain.usecase.GetJournalsUseCase
import com.auraai.domain.usecase.GenerateWeeklyReportUseCase
import com.auraai.domain.usecase.GetWeeklyReportsUseCase
import com.auraai.domain.usecase.GetCurrentUserTokenUseCase
import com.auraai.domain.usecase.SummarizeVoiceJournalUseCase
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
class JournalViewModelTest {

    @Mock
    private lateinit var createJournalEntryUseCase: CreateJournalEntryUseCase
    @Mock
    private lateinit var getJournalsUseCase: GetJournalsUseCase
    @Mock
    private lateinit var generateWeeklyReportUseCase: GenerateWeeklyReportUseCase
    @Mock
    private lateinit var getWeeklyReportsUseCase: GetWeeklyReportsUseCase
    @Mock
    private lateinit var getTokenUseCase: GetCurrentUserTokenUseCase
    @Mock
    private lateinit var summarizeVoiceJournalUseCase: SummarizeVoiceJournalUseCase

    private lateinit var viewModel: JournalViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)

        viewModel = JournalViewModel(
            createJournalEntryUseCase,
            getJournalsUseCase,
            generateWeeklyReportUseCase,
            getWeeklyReportsUseCase,
            summarizeVoiceJournalUseCase,
            getTokenUseCase
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun loadJournalData_success_updatesJournalLists() = runTest {
        val token = "mock_token"
        val mockJournals = listOf(JournalEntry("id1", "uid", 1.0, "Title", "Text", "Happiness", 0.9f, null, "Reflection", emptyList(), emptyList()))
        val mockWeekly = listOf(WeeklyReport("w1", "uid", 1.0, "Happiness", 0.2f, "Summary", emptyList(), emptyList()))

        `when`(getTokenUseCase()).thenReturn(Result.success(token))
        `when`(getJournalsUseCase(token)).thenReturn(Result.success(mockJournals))
        `when`(getWeeklyReportsUseCase(token)).thenReturn(Result.success(mockWeekly))

        viewModel.loadJournalData()
        advanceUntilIdle()

        verify(getJournalsUseCase).invoke(token)
        verify(getWeeklyReportsUseCase).invoke(token)
        assertEquals(mockJournals, viewModel.journals.value)
        assertEquals(mockWeekly, viewModel.weeklyReports.value)
    }

    @Test
    fun submitJournal_success_emitsRecentAnalysis() = runTest {
        val token = "mock_token"
        val mockJournal = JournalEntry("id1", "uid", 1.0, "Title", "Text", "Happiness", 0.9f, null, "Reflection", emptyList(), emptyList())

        `when`(getTokenUseCase()).thenReturn(Result.success(token))
        `when`(createJournalEntryUseCase(token, "Title", "Text", null)).thenReturn(Result.success(mockJournal))
        `when`(getJournalsUseCase(token)).thenReturn(Result.success(listOf(mockJournal)))
        `when`(getWeeklyReportsUseCase(token)).thenReturn(Result.success(emptyList()))

        viewModel.submitJournalEntry("Title", "Text", null)
        advanceUntilIdle()

        verify(createJournalEntryUseCase).invoke(token, "Title", "Text", null)
        assertEquals(mockJournal, viewModel.recentAnalysis.value)
    }
}
