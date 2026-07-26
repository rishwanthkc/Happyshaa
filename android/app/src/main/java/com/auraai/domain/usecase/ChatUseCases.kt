package com.auraai.domain.usecase

import com.auraai.domain.model.ChatMessage
import com.auraai.domain.repository.ChatRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetChatHistoryUseCase @Inject constructor(
    private val repository: ChatRepository
) {
    suspend operator fun invoke(token: String): Result<List<ChatMessage>> {
        return repository.getChatHistory(token)
    }
}

class GetChatResponseStreamUseCase @Inject constructor(
    private val repository: ChatRepository
) {
    operator fun invoke(token: String, message: String, currentMood: String): Flow<String> {
        return repository.getChatResponseStream(token, message, currentMood)
    }
}
