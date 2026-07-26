package com.auraai.ui.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.auraai.domain.model.ChatMessage
import com.auraai.domain.usecase.GetChatHistoryUseCase
import com.auraai.domain.usecase.GetChatResponseStreamUseCase
import com.auraai.domain.usecase.GetCurrentUserTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val getHistoryUseCase: GetChatHistoryUseCase,
    private val getStreamUseCase: GetChatResponseStreamUseCase,
    private val getTokenUseCase: GetCurrentUserTokenUseCase
) : ViewModel() {

    private val _messages = MutableStateFlow<List<ChatMessage>>(emptyList())
    val messages: StateFlow<List<ChatMessage>> = _messages.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _isStreaming = MutableStateFlow(false)
    val isStreaming: StateFlow<Boolean> = _isStreaming.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    fun loadChatHistory() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            getTokenUseCase().onSuccess { token ->
                getHistoryUseCase(token)
                    .onSuccess { history ->
                        _messages.value = history
                    }
                    .onFailure { exception ->
                        _errorMessage.value = "Failed to load chat history: ${exception.localizedMessage}"
                    }
            }.onFailure {
                _errorMessage.value = "Auth token invalid. Please log in again."
            }
            _isLoading.value = false
        }
    }

    fun sendMessage(content: String, currentMood: String, uid: String) {
        if (content.isBlank()) return

        // Append user message immediately to the screen list
        val userMsg = ChatMessage(
            msgId = "local_${System.currentTimeMillis()}",
            uid = uid,
            timestamp = System.currentTimeMillis(),
            sender = "user",
            content = content
        )
        _messages.value = _messages.value + userMsg

        viewModelScope.launch {
            _isStreaming.value = true
            _errorMessage.value = null
            
            // Append an empty bot message structure that we will stream text tokens into
            val botMsgId = "local_bot_${System.currentTimeMillis()}"
            var botContent = ""
            val botMsg = ChatMessage(
                msgId = botMsgId,
                uid = uid,
                timestamp = System.currentTimeMillis() + 10,
                sender = "companion",
                content = ""
            )
            _messages.value = _messages.value + botMsg

            getTokenUseCase().onSuccess { token ->
                getStreamUseCase(token, content, currentMood)
                    .catch { exception ->
                        _errorMessage.value = exception.localizedMessage
                        updateBotMessage(botMsgId, "Offline: I'm here beside you. Take a slow, deep breath.")
                        _isStreaming.value = false
                    }
                    .collect { chunk ->
                        botContent += chunk
                        updateBotMessage(botMsgId, botContent)
                    }
            }.onFailure {
                updateBotMessage(botMsgId, "Session expired. Please log in again.")
            }
            
            _isStreaming.value = false
        }
    }

    private fun updateBotMessage(msgId: String, newContent: String) {
        _messages.value = _messages.value.map { msg ->
            if (msg.msgId == msgId) {
                msg.copy(content = newContent)
            } else {
                msg
            }
        }
    }

    fun clearError() {
        _errorMessage.value = null
    }
}
