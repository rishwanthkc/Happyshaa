package com.auraai.ui.chat

import com.auraai.domain.model.ChatMessage
import com.auraai.domain.usecase.GetChatHistoryUseCase
import com.auraai.domain.usecase.GetChatResponseStreamUseCase
import com.auraai.domain.usecase.GetCurrentUserTokenUseCase
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
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

@OptIn(ExperimentalCoroutinesApi::class)
class ChatViewModelTest {

    @Mock
    private lateinit var getHistoryUseCase: GetChatHistoryUseCase
    @Mock
    private lateinit var getStreamUseCase: GetChatResponseStreamUseCase
    @Mock
    private lateinit var getTokenUseCase: GetCurrentUserTokenUseCase

    private lateinit var viewModel: ChatViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)

        viewModel = ChatViewModel(
            getHistoryUseCase,
            getStreamUseCase,
            getTokenUseCase
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun loadChatHistory_success_updatesMessagesList() = runTest {
        val token = "mock_token"
        val mockHistory = listOf(
            ChatMessage("1", "user123", 1000L, "user", "Hi"),
            ChatMessage("2", "user123", 1010L, "companion", "Hello!")
        )

        `when`(getTokenUseCase()).thenReturn(Result.success(token))
        `when`(getHistoryUseCase(token)).thenReturn(Result.success(mockHistory))

        viewModel.loadChatHistory()
        advanceUntilIdle()

        verify(getTokenUseCase).invoke()
        verify(getHistoryUseCase).invoke(token)
        assertEquals(mockHistory, viewModel.messages.value)
    }

    @Test
    fun sendMessage_success_streamsAndAppendsBotMessage() = runTest {
        val token = "mock_token"
        val userContent = "How are you?"
        val streamChunks = listOf("I am ", "doing ", "great!")

        `when`(getTokenUseCase()).thenReturn(Result.success(token))
        `when`(getStreamUseCase(token, userContent, "Happy"))
            .thenReturn(flowOf("I am ", "doing ", "great!"))

        viewModel.sendMessage(userContent, "Happy", "user123")
        advanceUntilIdle()

        verify(getTokenUseCase).invoke()
        verify(getStreamUseCase).invoke(token, userContent, "Happy")
        
        // Assertions checking that bot response was constructed and streamed into the list
        val msgList = viewModel.messages.value
        assertEquals(2, msgList.size)
        assertEquals("user", msgList[0].sender)
        assertEquals(userContent, msgList[0].content)
        assertEquals("companion", msgList[1].sender)
        assertEquals("I am doing great!", msgList[1].content)
    }
}
