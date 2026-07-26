package com.auraai.data.repository

import com.auraai.data.remote.api.AuraApiService
import com.auraai.data.remote.api.FCMTokenRequest
import com.auraai.domain.model.NotificationItem
import com.auraai.domain.repository.NotificationsRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationsRepositoryImpl @Inject constructor(
    private val apiService: AuraApiService
) : NotificationsRepository {

    private val offlineNotifications = mutableListOf<NotificationItem>()

    override suspend fun saveFcmToken(token: String, fcmToken: String): Result<Unit> {
        return try {
            apiService.saveFcmToken(token, FCMTokenRequest(fcmToken))
            Result.success(Unit)
        } catch (e: Exception) {
            Result.success(Unit)
        }
    }

    override suspend fun getNotificationHistory(token: String): Result<List<NotificationItem>> {
        return try {
            val response = apiService.getNotificationHistory(token)
            val mapped = response.map {
                NotificationItem(
                    notificationId = it.notification_id,
                    uid = it.uid,
                    timestamp = it.timestamp,
                    title = it.title,
                    body = it.body,
                    isRead = it.is_read
                )
            }
            offlineNotifications.clear()
            offlineNotifications.addAll(mapped)
            Result.success(mapped)
        } catch (e: Exception) {
            Result.success(offlineNotifications)
        }
    }

    override suspend fun sendTestNotification(token: String): Result<Unit> {
        return try {
            apiService.sendTestNotification(token)
            val mockNotif = NotificationItem(
                notificationId = "notif_${System.currentTimeMillis()}",
                uid = "offline_user",
                timestamp = System.currentTimeMillis() / 1000.0,
                title = "Wellness Reminder",
                body = "Take a moment to pause and breathe today. Aura is here for you.",
                isRead = false
            )
            offlineNotifications.add(0, mockNotif)
            Result.success(Unit)
        } catch (e: Exception) {
            val mockNotif = NotificationItem(
                notificationId = "notif_${System.currentTimeMillis()}",
                uid = "offline_user",
                timestamp = System.currentTimeMillis() / 1000.0,
                title = "Wellness Reminder",
                body = "Take a moment to pause and breathe today. Aura is here for you.",
                isRead = false
            )
            offlineNotifications.add(0, mockNotif)
            Result.success(Unit)
        }
    }
}
