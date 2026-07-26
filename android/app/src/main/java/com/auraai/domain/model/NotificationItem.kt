package com.auraai.domain.model

data class NotificationItem(
    val notificationId: String,
    val uid: String,
    val timestamp: Double,
    val title: String,
    val body: String,
    val isRead: Boolean
)
