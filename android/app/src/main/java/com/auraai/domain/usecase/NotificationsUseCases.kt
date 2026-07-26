package com.auraai.domain.usecase

import com.auraai.domain.model.NotificationItem
import com.auraai.domain.repository.NotificationsRepository
import javax.inject.Inject

data class NotificationsUseCases(
    val saveFcmToken: SaveFcmTokenUseCase,
    val getNotificationHistory: GetNotificationHistoryUseCase,
    val sendTestNotification: SendTestNotificationUseCase
)

class SaveFcmTokenUseCase @Inject constructor(private val repo: NotificationsRepository) {
    suspend operator fun invoke(token: String, fcmToken: String): Result<Unit> = repo.saveFcmToken(token, fcmToken)
}

class GetNotificationHistoryUseCase @Inject constructor(private val repo: NotificationsRepository) {
    suspend operator fun invoke(token: String): Result<List<NotificationItem>> = repo.getNotificationHistory(token)
}

class SendTestNotificationUseCase @Inject constructor(private val repo: NotificationsRepository) {
    suspend operator fun invoke(token: String): Result<Unit> = repo.sendTestNotification(token)
}
