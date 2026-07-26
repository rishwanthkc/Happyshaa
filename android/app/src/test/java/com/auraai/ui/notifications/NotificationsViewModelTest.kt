package com.auraai.ui.notifications

import com.auraai.domain.model.NotificationItem
import com.auraai.domain.usecase.SaveFcmTokenUseCase
import com.auraai.domain.usecase.GetNotificationHistoryUseCase
import com.auraai.domain.usecase.SendTestNotificationUseCase
import com.auraai.domain.usecase.GetCurrentUserTokenUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

@OptIn(ExperimentalCoroutinesApi::class)
class NotificationsViewModelTest {

    @Mock
    private lateinit var saveFcmTokenUseCase: SaveFcmTokenUseCase
    @Mock
    private lateinit var getNotificationHistoryUseCase: GetNotificationHistoryUseCase
    @Mock
    private lateinit var sendTestNotificationUseCase: SendTestNotificationUseCase
    @Mock
    private lateinit var getTokenUseCase: GetCurrentUserTokenUseCase

    private lateinit var viewModel: NotificationsViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)

        viewModel = NotificationsViewModel(
            saveFcmTokenUseCase,
            getNotificationHistoryUseCase,
            sendTestNotificationUseCase,
            getTokenUseCase
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun loadNotifications_success_updatesNotificationList() = runTest {
        val token = "mock_token"
        val mockNotifications = listOf(NotificationItem("notif1", "uid", 123.0, "Title", "Body", false))

        `when`(getTokenUseCase()).thenReturn(Result.success(token))
        `when`(getNotificationHistoryUseCase(token)).thenReturn(Result.success(mockNotifications))

        viewModel.loadNotifications()
        advanceUntilIdle()

        verify(getNotificationHistoryUseCase).invoke(token)
        assertEquals(mockNotifications, viewModel.notifications.value)
    }

    @Test
    fun triggerTestNotification_success_reloadsHistory() = runTest {
        val token = "mock_token"

        `when`(getTokenUseCase()).thenReturn(Result.success(token))
        `when`(sendTestNotificationUseCase(token)).thenReturn(Result.success(Unit))
        `when`(getNotificationHistoryUseCase(token)).thenReturn(Result.success(emptyList()))

        viewModel.triggerTestNotification()
        advanceUntilIdle()

        verify(sendTestNotificationUseCase).invoke(token)
    }
}
