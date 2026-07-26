package com.auraai.ui.auth;

/**
 * ViewModel managing the UI states and operations for authentication.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u000b\b\u0007\u0018\u00002\u00020\u0001BG\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u0012\u0006\u0010\u000e\u001a\u00020\u000f\u0012\u0006\u0010\u0010\u001a\u00020\u0011\u00a2\u0006\u0002\u0010\u0012J\u0006\u0010&\u001a\u00020\'J\u000e\u0010(\u001a\u00020\'2\u0006\u0010)\u001a\u00020\u0015J\u001e\u0010*\u001a\u00020\'2\u0006\u0010)\u001a\u00020\u00152\u0006\u0010+\u001a\u00020\u00152\u0006\u0010,\u001a\u00020\u0017J\u000e\u0010-\u001a\u00020\'2\u0006\u0010.\u001a\u00020\u0015J\u0006\u0010/\u001a\u00020\'J\u001e\u00100\u001a\u00020\'2\u0006\u0010)\u001a\u00020\u00152\u0006\u00101\u001a\u00020\u00152\u0006\u0010+\u001a\u00020\u0015R\u0016\u0010\u0013\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00150\u0014X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00170\u0014X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00150\u0019X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u001c0\u001b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR\u0019\u0010\u001f\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00150\u001b\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010\u001eR\u0017\u0010!\u001a\b\u0012\u0004\u0012\u00020\u00170\u001b\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\u001eR\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u00150#\u00a2\u0006\b\n\u0000\u001a\u0004\b$\u0010%\u00a8\u00062"}, d2 = {"Lcom/auraai/ui/auth/AuthViewModel;", "Landroidx/lifecycle/ViewModel;", "signInUseCase", "Lcom/auraai/domain/usecase/SignInUseCase;", "signUpUseCase", "Lcom/auraai/domain/usecase/SignUpUseCase;", "signOutUseCase", "Lcom/auraai/domain/usecase/SignOutUseCase;", "sendPasswordResetUseCase", "Lcom/auraai/domain/usecase/SendPasswordResetUseCase;", "signInWithGoogleUseCase", "Lcom/auraai/domain/usecase/SignInWithGoogleUseCase;", "syncUserUseCase", "Lcom/auraai/domain/usecase/SyncUserUseCase;", "preferenceManager", "Lcom/auraai/data/local/preferences/PreferenceManager;", "getSessionUseCase", "Lcom/auraai/domain/usecase/GetSessionUseCase;", "(Lcom/auraai/domain/usecase/SignInUseCase;Lcom/auraai/domain/usecase/SignUpUseCase;Lcom/auraai/domain/usecase/SignOutUseCase;Lcom/auraai/domain/usecase/SendPasswordResetUseCase;Lcom/auraai/domain/usecase/SignInWithGoogleUseCase;Lcom/auraai/domain/usecase/SyncUserUseCase;Lcom/auraai/data/local/preferences/PreferenceManager;Lcom/auraai/domain/usecase/GetSessionUseCase;)V", "_errorMessage", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "_isLoading", "", "_toastMessage", "Lkotlinx/coroutines/flow/MutableSharedFlow;", "authState", "Lkotlinx/coroutines/flow/StateFlow;", "Lcom/auraai/domain/model/AuthState;", "getAuthState", "()Lkotlinx/coroutines/flow/StateFlow;", "errorMessage", "getErrorMessage", "isLoading", "toastMessage", "Lkotlinx/coroutines/flow/SharedFlow;", "getToastMessage", "()Lkotlinx/coroutines/flow/SharedFlow;", "clearError", "", "resetPassword", "email", "signIn", "password", "rememberMe", "signInWithGoogle", "idToken", "signOut", "signUp", "displayName", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class AuthViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.auraai.domain.usecase.SignInUseCase signInUseCase = null;
    @org.jetbrains.annotations.NotNull()
    private final com.auraai.domain.usecase.SignUpUseCase signUpUseCase = null;
    @org.jetbrains.annotations.NotNull()
    private final com.auraai.domain.usecase.SignOutUseCase signOutUseCase = null;
    @org.jetbrains.annotations.NotNull()
    private final com.auraai.domain.usecase.SendPasswordResetUseCase sendPasswordResetUseCase = null;
    @org.jetbrains.annotations.NotNull()
    private final com.auraai.domain.usecase.SignInWithGoogleUseCase signInWithGoogleUseCase = null;
    @org.jetbrains.annotations.NotNull()
    private final com.auraai.domain.usecase.SyncUserUseCase syncUserUseCase = null;
    @org.jetbrains.annotations.NotNull()
    private final com.auraai.data.local.preferences.PreferenceManager preferenceManager = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.auraai.domain.model.AuthState> authState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Boolean> _isLoading = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isLoading = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> _errorMessage = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.String> errorMessage = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableSharedFlow<java.lang.String> _toastMessage = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.SharedFlow<java.lang.String> toastMessage = null;
    
    @javax.inject.Inject()
    public AuthViewModel(@org.jetbrains.annotations.NotNull()
    com.auraai.domain.usecase.SignInUseCase signInUseCase, @org.jetbrains.annotations.NotNull()
    com.auraai.domain.usecase.SignUpUseCase signUpUseCase, @org.jetbrains.annotations.NotNull()
    com.auraai.domain.usecase.SignOutUseCase signOutUseCase, @org.jetbrains.annotations.NotNull()
    com.auraai.domain.usecase.SendPasswordResetUseCase sendPasswordResetUseCase, @org.jetbrains.annotations.NotNull()
    com.auraai.domain.usecase.SignInWithGoogleUseCase signInWithGoogleUseCase, @org.jetbrains.annotations.NotNull()
    com.auraai.domain.usecase.SyncUserUseCase syncUserUseCase, @org.jetbrains.annotations.NotNull()
    com.auraai.data.local.preferences.PreferenceManager preferenceManager, @org.jetbrains.annotations.NotNull()
    com.auraai.domain.usecase.GetSessionUseCase getSessionUseCase) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.auraai.domain.model.AuthState> getAuthState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isLoading() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.String> getErrorMessage() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.SharedFlow<java.lang.String> getToastMessage() {
        return null;
    }
    
    public final void signIn(@org.jetbrains.annotations.NotNull()
    java.lang.String email, @org.jetbrains.annotations.NotNull()
    java.lang.String password, boolean rememberMe) {
    }
    
    public final void signUp(@org.jetbrains.annotations.NotNull()
    java.lang.String email, @org.jetbrains.annotations.NotNull()
    java.lang.String displayName, @org.jetbrains.annotations.NotNull()
    java.lang.String password) {
    }
    
    public final void signInWithGoogle(@org.jetbrains.annotations.NotNull()
    java.lang.String idToken) {
    }
    
    public final void resetPassword(@org.jetbrains.annotations.NotNull()
    java.lang.String email) {
    }
    
    public final void signOut() {
    }
    
    public final void clearError() {
    }
}