package com.auraai.domain.usecase

import com.auraai.domain.model.AuthState
import com.auraai.domain.model.User
import com.auraai.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Use case to sign in a user with email and password.
 */
class SignInUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): Result<User> {
        if (email.isBlank() || password.isBlank()) {
            return Result.failure(IllegalArgumentException("Email and password cannot be empty."))
        }
        return repository.signInWithEmailAndPassword(email, password)
    }
}

/**
 * Use case to sign up a new user with email, display name, and password.
 */
class SignUpUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(email: String, displayName: String, password: String): Result<User> {
        if (email.isBlank() || displayName.isBlank() || password.isBlank()) {
            return Result.failure(IllegalArgumentException("All fields are required."))
        }
        return repository.signUpWithEmailAndPassword(email, displayName, password)
    }
}

/**
 * Use case to sign out the current user session.
 */
class SignOutUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(): Result<Unit> = repository.signOut()
}

/**
 * Use case to retrieve the current active user authentication session flow.
 */
class GetSessionUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    operator fun invoke(): Flow<AuthState> = repository.authState
}

/**
 * Use case to trigger a password reset email.
 */
class SendPasswordResetUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(email: String): Result<Unit> {
        if (email.isBlank()) {
            return Result.failure(IllegalArgumentException("Email cannot be empty."))
        }
        return repository.sendPasswordResetEmail(email)
    }
}

/**
 * Use case to sign in a user using Google Sign-In credentials.
 */
class SignInWithGoogleUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(idToken: String): Result<User> {
        if (idToken.isBlank()) {
            return Result.failure(IllegalArgumentException("ID Token cannot be empty."))
        }
        return repository.signInWithGoogle(idToken)
    }
}

/**
 * Use case to sync the authenticated user profile with the backend Firestore.
 */
class SyncUserUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(displayName: String?, photoUrl: String?): Result<Unit> {
        return repository.syncUserWithBackend(displayName, photoUrl)
    }
}

/**
 * Use case to retrieve the active Firebase ID token for secure backend API calls.
 */
class GetCurrentUserTokenUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(): Result<String> = repository.getCurrentUserToken()
}
