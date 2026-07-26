package com.auraai.domain.model

/**
 * Domain model representing a user in the application.
 */
data class User(
    val uid: String,
    val email: String,
    val displayName: String?,
    val photoUrl: String?
)

/**
 * Sealed interface representing different states of the authentication flow.
 */
sealed interface AuthState {
    data object Idle : AuthState
    data object Loading : AuthState
    data class Authenticated(val user: User) : AuthState
    data object Unauthenticated : AuthState
    data class Error(val message: String) : AuthState
}
