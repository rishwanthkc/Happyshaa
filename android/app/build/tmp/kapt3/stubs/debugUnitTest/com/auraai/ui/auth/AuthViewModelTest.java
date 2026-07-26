package com.auraai.ui.auth;

/**
 * ViewModel UI state JVM unit tests using Coroutine main dispatcher overrides.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0017\u001a\u00020\u0018H\u0007J\f\u0010\u0019\u001a\u00060\u0018j\u0002`\u001aH\u0007J\b\u0010\u001b\u001a\u00020\u0018H\u0007R\u0012\u0010\u0003\u001a\u00020\u00048\u0002@\u0002X\u0083.\u00a2\u0006\u0002\n\u0000R\u0012\u0010\u0005\u001a\u00020\u00068\u0002@\u0002X\u0083.\u00a2\u0006\u0002\n\u0000R\u0012\u0010\u0007\u001a\u00020\b8\u0002@\u0002X\u0083.\u00a2\u0006\u0002\n\u0000R\u0012\u0010\t\u001a\u00020\n8\u0002@\u0002X\u0083.\u00a2\u0006\u0002\n\u0000R\u0012\u0010\u000b\u001a\u00020\f8\u0002@\u0002X\u0083.\u00a2\u0006\u0002\n\u0000R\u0012\u0010\r\u001a\u00020\u000e8\u0002@\u0002X\u0083.\u00a2\u0006\u0002\n\u0000R\u0012\u0010\u000f\u001a\u00020\u00108\u0002@\u0002X\u0083.\u00a2\u0006\u0002\n\u0000R\u0012\u0010\u0011\u001a\u00020\u00128\u0002@\u0002X\u0083.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001c"}, d2 = {"Lcom/auraai/ui/auth/AuthViewModelTest;", "", "()V", "getSessionUseCase", "Lcom/auraai/domain/usecase/GetSessionUseCase;", "preferenceManager", "Lcom/auraai/data/local/preferences/PreferenceManager;", "sendPasswordResetUseCase", "Lcom/auraai/domain/usecase/SendPasswordResetUseCase;", "signInUseCase", "Lcom/auraai/domain/usecase/SignInUseCase;", "signInWithGoogleUseCase", "Lcom/auraai/domain/usecase/SignInWithGoogleUseCase;", "signOutUseCase", "Lcom/auraai/domain/usecase/SignOutUseCase;", "signUpUseCase", "Lcom/auraai/domain/usecase/SignUpUseCase;", "syncUserUseCase", "Lcom/auraai/domain/usecase/SyncUserUseCase;", "testDispatcher", "Lkotlinx/coroutines/test/TestDispatcher;", "viewModel", "Lcom/auraai/ui/auth/AuthViewModel;", "setUp", "", "signIn_success_setsToastMessageAndTriggersSync", "Lkotlinx/coroutines/test/TestResult;", "tearDown", "app_debugUnitTest"})
@kotlin.OptIn(markerClass = {kotlinx.coroutines.ExperimentalCoroutinesApi.class})
public final class AuthViewModelTest {
    @org.mockito.Mock()
    private com.auraai.domain.usecase.SignInUseCase signInUseCase;
    @org.mockito.Mock()
    private com.auraai.domain.usecase.SignUpUseCase signUpUseCase;
    @org.mockito.Mock()
    private com.auraai.domain.usecase.SignOutUseCase signOutUseCase;
    @org.mockito.Mock()
    private com.auraai.domain.usecase.SendPasswordResetUseCase sendPasswordResetUseCase;
    @org.mockito.Mock()
    private com.auraai.domain.usecase.SignInWithGoogleUseCase signInWithGoogleUseCase;
    @org.mockito.Mock()
    private com.auraai.domain.usecase.SyncUserUseCase syncUserUseCase;
    @org.mockito.Mock()
    private com.auraai.data.local.preferences.PreferenceManager preferenceManager;
    @org.mockito.Mock()
    private com.auraai.domain.usecase.GetSessionUseCase getSessionUseCase;
    private com.auraai.ui.auth.AuthViewModel viewModel;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.test.TestDispatcher testDispatcher = null;
    
    public AuthViewModelTest() {
        super();
    }
    
    @org.junit.Before()
    public final void setUp() {
    }
    
    @org.junit.After()
    public final void tearDown() {
    }
    
    @org.junit.Test()
    public final void signIn_success_setsToastMessageAndTriggersSync() {
    }
}