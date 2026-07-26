package com.auraai.domain.repository

import com.auraai.domain.model.ChatMessage
import kotlinx.coroutines.flow.Flow

interface ChatRepository {
    
    suspend fun getChatHistory(token: String): Result<List<ChatMessage>>
    
    fun getChatResponseStream(token: String, message: String, currentMood: String): Flow<String>
}
