package com.auraai.domain.repository

import com.auraai.domain.model.AuthState
import com.auraai.domain.model.User
import kotlinx.coroutines.flow.Flow

/**
 * Interface defining authentication operations.
 * Implemented in the Data layer using Firebase Authentication.
 */
interface AuthRepository {
    /**
     * Flow of the current authentication state.
     */
    val authState: Flow<AuthState>
    
    /**
     * Authenticates a user with email and password.
     */
    suspend fun signInWithEmailAndPassword(email: String, password: String): Result<User>
    
    /**
     * Registers a new user with email and password, and optional display name.
     */
    suspend fun signUpWithEmailAndPassword(email: String, displayName: String, password: String): Result<User>
    
    /**
     * Authenticates a user using Google Sign-In credential token.
     */
    suspend fun signInWithGoogle(idToken: String): Result<User>
    
    /**
     * Triggers a password reset email for the given email address.
     */
    suspend fun sendPasswordResetEmail(email: String): Result<Unit>
    
    /**
     * Signs out the currently authenticated user.
     */
    suspend fun signOut(): Result<Unit>
    
    /**
     * Retrieves the Firebase ID token for the currently authenticated user.
     * Used for secure backend requests.
     */
    suspend fun getCurrentUserToken(): Result<String>

    /**
     * Sends profile metadata to backend /sync endpoint to update Firestore user record.
     */
    suspend fun syncUserWithBackend(displayName: String?, photoUrl: String?): Result<Unit>
}
