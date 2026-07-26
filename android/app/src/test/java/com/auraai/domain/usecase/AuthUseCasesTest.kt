package com.auraai.domain.usecase

import com.auraai.domain.model.User
import com.auraai.domain.repository.AuthRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

/**
 * JVM unit tests for Authentication use cases using Mockito.
 */
class AuthUseCasesTest {

    @Mock
    private lateinit var repository: AuthRepository

    private lateinit var signInUseCase: SignInUseCase
    private lateinit var signUpUseCase: SignUpUseCase
    private lateinit var signInWithGoogleUseCase: SignInWithGoogleUseCase
    private lateinit var syncUserUseCase: SyncUserUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        signInUseCase = SignInUseCase(repository)
        signUpUseCase = SignUpUseCase(repository)
        signInWithGoogleUseCase = SignInWithGoogleUseCase(repository)
        syncUserUseCase = SyncUserUseCase(repository)
    }

    @Test
    fun signIn_withValidInputs_returnsSuccess() = runTest {
        val mockUser = User("123", "test@example.com", "Test User", null)
        `when`(repository.signInWithEmailAndPassword("test@example.com", "password123"))
            .thenReturn(Result.success(mockUser))

        val result = signInUseCase("test@example.com", "password123")
        assertTrue(result.isSuccess)
        assertEquals(mockUser, result.getOrNull())
    }

    @Test
    fun signIn_withEmptyInputs_returnsFailure() = runTest {
        val result = signInUseCase("", "password123")
        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is IllegalArgumentException)
    }

    @Test
    fun signUp_withValidInputs_returnsSuccess() = runTest {
        val mockUser = User("123", "test@example.com", "Test User", null)
        `when`(repository.signUpWithEmailAndPassword("test@example.com", "Test User", "password123"))
            .thenReturn(Result.success(mockUser))

        val result = signUpUseCase("test@example.com", "Test User", "password123")
        assertTrue(result.isSuccess)
        assertEquals(mockUser, result.getOrNull())
    }

    @Test
    fun signInWithGoogle_withValidToken_returnsSuccess() = runTest {
        val mockUser = User("123", "test@example.com", "Test User", null)
        `when`(repository.signInWithGoogle("valid_token"))
            .thenReturn(Result.success(mockUser))

        val result = signInWithGoogleUseCase("valid_token")
        assertTrue(result.isSuccess)
        assertEquals(mockUser, result.getOrNull())
    }

    @Test
    fun syncUser_callsRepository_returnsSuccess() = runTest {
        `when`(repository.syncUserWithBackend("Test User", "photo"))
            .thenReturn(Result.success(Unit))

        val result = syncUserUseCase("Test User", "photo")
        assertTrue(result.isSuccess)
    }
}
