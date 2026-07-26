package com.auraai.ui.notifications

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.auraai.domain.model.NotificationItem
import com.auraai.domain.usecase.SaveFcmTokenUseCase
import com.auraai.domain.usecase.GetNotificationHistoryUseCase
import com.auraai.domain.usecase.SendTestNotificationUseCase
import com.auraai.domain.usecase.GetCurrentUserTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationsViewModel @Inject constructor(
    private val saveFcmTokenUseCase: SaveFcmTokenUseCase,
    private val getNotificationHistoryUseCase: GetNotificationHistoryUseCase,
    private val sendTestNotificationUseCase: SendTestNotificationUseCase,
    private val getTokenUseCase: GetCurrentUserTokenUseCase
) : ViewModel() {

    private val _notifications = MutableStateFlow<List<NotificationItem>>(emptyList())
    val notifications: StateFlow<List<NotificationItem>> = _notifications.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    fun loadNotifications() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            getTokenUseCase().onSuccess { token ->
                getNotificationHistoryUseCase(token).onSuccess {
                    _notifications.value = it
                }.onFailure {
                    _errorMessage.value = "Failed to load notification history: ${it.localizedMessage}"
                }
            }.onFailure {
                _errorMessage.value = "Auth token lookup failed: ${it.localizedMessage}"
            }
            _isLoading.value = false
        }
    }

    fun registerToken(fcmToken: String) {
        viewModelScope.launch {
            getTokenUseCase().onSuccess { token ->
                saveFcmTokenUseCase(token, fcmToken)
            }
        }
    }

    fun triggerTestNotification() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            getTokenUseCase().onSuccess { token ->
                sendTestNotificationUseCase(token).onSuccess {
                    loadNotifications() // Refresh list
                }.onFailure {
                    _errorMessage.value = "Failed to trigger test alert: ${it.localizedMessage}"
                }
            }
            _isLoading.value = false
        }
    }
}
