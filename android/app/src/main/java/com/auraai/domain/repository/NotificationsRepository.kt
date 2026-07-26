package com.auraai.domain.repository

import com.auraai.domain.model.NotificationItem

interface NotificationsRepository {
    suspend fun saveFcmToken(token: String, fcmToken: String): Result<Unit>
    suspend fun getNotificationHistory(token: String): Result<List<NotificationItem>>
    suspend fun sendTestNotification(token: String): Result<Unit>
}
