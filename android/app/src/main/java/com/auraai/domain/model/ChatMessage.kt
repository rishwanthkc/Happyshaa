package com.auraai.domain.model

data class ChatMessage(
    val msgId: String,
    val uid: String,
    val timestamp: Long,
    val sender: String, // "user" or "companion"
    val content: String
)
