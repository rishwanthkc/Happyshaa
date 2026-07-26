package com.auraai.data.repository

import com.auraai.data.remote.api.AuraApiService
import com.auraai.data.remote.api.ChatMessageRequest
import com.auraai.data.remote.api.NetworkChatMessage
import com.auraai.domain.model.ChatMessage
import com.auraai.domain.repository.ChatRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChatRepositoryImpl @Inject constructor(
    private val apiService: AuraApiService
) : ChatRepository {

    override suspend fun getChatHistory(token: String): Result<List<ChatMessage>> {
        return try {
            val response = apiService.getChatHistory("Bearer $token")
            val messages = response.history.map { it.toDomain() }
            Result.success(messages)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun getChatResponseStream(
        token: String,
        message: String,
        currentMood: String
    ): Flow<String> = flow {
        val responseBody = apiService.streamChatResponse(
            token = "Bearer $token",
            request = ChatMessageRequest(message, currentMood)
        )
        
        val reader = responseBody.charStream().buffered()
        val buffer = CharArray(256)
        
        var charsRead = reader.read(buffer)
        while (charsRead != -1) {
            if (charsRead > 0) {
                emit(String(buffer, 0, charsRead))
            }
            charsRead = reader.read(buffer)
        }
        reader.close()
    }.flowOn(Dispatchers.IO)

    private fun NetworkChatMessage.toDomain(): ChatMessage = ChatMessage(
        msgId = msg_id,
        uid = uid,
        timestamp = (timestamp * 1000).toLong(),
        sender = sender,
        content = content
    )
}
