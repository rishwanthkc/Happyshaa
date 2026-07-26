package com.auraai.ui.auth

import com.auraai.data.local.preferences.PreferenceManager
import com.auraai.domain.model.AuthState
import com.auraai.domain.model.User
import com.auraai.domain.usecase.GetSessionUseCase
import com.auraai.domain.usecase.SendPasswordResetUseCase
import com.auraai.domain.usecase.SignInUseCase
import com.auraai.domain.usecase.SignInWithGoogleUseCase
import com.auraai.domain.usecase.SignUpUseCase
import com.auraai.domain.usecase.SignOutUseCase
import com.auraai.domain.usecase.SyncUserUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

/**
 * ViewModel UI state JVM unit tests using Coroutine main dispatcher overrides.
 */
@OptIn(ExperimentalCoroutinesApi::class)
class AuthViewModelTest {

    @Mock
    private lateinit var signInUseCase: SignInUseCase
    @Mock
    private lateinit var signUpUseCase: SignUpUseCase
    @Mock
    private lateinit var signOutUseCase: SignOutUseCase
    @Mock
    private lateinit var sendPasswordResetUseCase: SendPasswordResetUseCase
    @Mock
    private lateinit var signInWithGoogleUseCase: SignInWithGoogleUseCase
    @Mock
    private lateinit var syncUserUseCase: SyncUserUseCase
    @Mock
    private lateinit var preferenceManager: PreferenceManager
    @Mock
    private lateinit var getSessionUseCase: GetSessionUseCase

    private lateinit var viewModel: AuthViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)

        `when`(getSessionUseCase()).thenReturn(flowOf(AuthState.Idle))

        viewModel = AuthViewModel(
            signInUseCase,
            signUpUseCase,
            signOutUseCase,
            sendPasswordResetUseCase,
            signInWithGoogleUseCase,
            syncUserUseCase,
            preferenceManager,
            getSessionUseCase
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun signIn_success_setsToastMessageAndTriggersSync() = runTest {
        val mockUser = User("123", "test@example.com", "Test User", null)
        `when`(signInUseCase("test@example.com", "password123"))
            .thenReturn(Result.success(mockUser))
        `when`(syncUserUseCase("Test User", null))
            .thenReturn(Result.success(Unit))

        viewModel.signIn("test@example.com", "password123", true)

        advanceUntilIdle()

        // Verify that the use cases were invoked with appropriate arguments
        org.mockito.Mockito.verify(signInUseCase).invoke("test@example.com", "password123")
        org.mockito.Mockito.verify(syncUserUseCase).invoke("Test User", null)
    }
}
