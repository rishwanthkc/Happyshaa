package com.auraai.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.auraai.domain.model.AuthState
import com.auraai.domain.usecase.GetSessionUseCase
import com.auraai.domain.usecase.SendPasswordResetUseCase
import com.auraai.domain.usecase.SignInUseCase
import com.auraai.domain.usecase.SignUpUseCase
import com.auraai.domain.usecase.SignOutUseCase
import com.auraai.domain.usecase.SignInWithGoogleUseCase
import com.auraai.domain.usecase.SyncUserUseCase
import com.auraai.data.local.preferences.PreferenceManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel managing the UI states and operations for authentication.
 */
@HiltViewModel
class AuthViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase,
    private val signUpUseCase: SignUpUseCase,
    private val signOutUseCase: SignOutUseCase,
    private val sendPasswordResetUseCase: SendPasswordResetUseCase,
    private val signInWithGoogleUseCase: SignInWithGoogleUseCase,
    private val syncUserUseCase: SyncUserUseCase,
    private val preferenceManager: PreferenceManager,
    getSessionUseCase: GetSessionUseCase
) : ViewModel() {

    // Expose the global auth state from Firebase
    val authState: StateFlow<AuthState> = getSessionUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = AuthState.Idle
        )

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    private val _toastMessage = MutableSharedFlow<String>()
    val toastMessage: SharedFlow<String> = _toastMessage.asSharedFlow()

    fun signIn(email: String, password: String, rememberMe: Boolean) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            
            preferenceManager.setRememberMe(rememberMe)

            signInUseCase(email, password)
                .onSuccess { user ->
                    _toastMessage.emit("Successfully signed in!")
                    syncUserUseCase(user.displayName, user.photoUrl)
                }
                .onFailure { exception ->
                    _errorMessage.value = exception.localizedMessage ?: "Sign-in failed. Please try again."
                }
            
            _isLoading.value = false
        }
    }

    fun signUp(email: String, displayName: String, password: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            
            signUpUseCase(email, displayName, password)
                .onSuccess { user ->
                    _toastMessage.emit("Successfully registered!")
                    syncUserUseCase(user.displayName, user.photoUrl)
                }
                .onFailure { exception ->
                    _errorMessage.value = exception.localizedMessage ?: "Registration failed. Please try again."
                }
            
            _isLoading.value = false
        }
    }

    fun signInWithGoogle(idToken: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            
            signInWithGoogleUseCase(idToken)
                .onSuccess { user ->
                    _toastMessage.emit("Successfully signed in with Google!")
                    syncUserUseCase(user.displayName, user.photoUrl)
                }
                .onFailure { exception ->
                    _errorMessage.value = exception.localizedMessage ?: "Google sign-in failed. Please try again."
                }
            
            _isLoading.value = false
        }
    }

    fun resetPassword(email: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            
            sendPasswordResetUseCase(email)
                .onSuccess {
                    _toastMessage.emit("Password reset email sent!")
                }
                .onFailure { exception ->
                    _errorMessage.value = exception.localizedMessage ?: "Failed to send reset email."
                }
            
            _isLoading.value = false
        }
    }

    fun signOut() {
        viewModelScope.launch {
            _isLoading.value = true
            signOutUseCase()
                .onSuccess {
                    _toastMessage.emit("Signed out successfully.")
                }
                .onFailure { exception ->
                    _errorMessage.value = exception.localizedMessage ?: "Failed to sign out."
                }
            _isLoading.value = false
        }
    }

    fun clearError() {
        _errorMessage.value = null
    }
}
