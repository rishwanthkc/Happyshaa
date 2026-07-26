package com.auraai.domain.usecase;

/**
 * JVM unit tests for Authentication use cases using Mockito.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\r\u001a\u00020\u000eH\u0007J\f\u0010\u000f\u001a\u00060\u000ej\u0002`\u0010H\u0007J\f\u0010\u0011\u001a\u00060\u000ej\u0002`\u0010H\u0007J\f\u0010\u0012\u001a\u00060\u000ej\u0002`\u0010H\u0007J\f\u0010\u0013\u001a\u00060\u000ej\u0002`\u0010H\u0007J\f\u0010\u0014\u001a\u00060\u000ej\u0002`\u0010H\u0007R\u0012\u0010\u0003\u001a\u00020\u00048\u0002@\u0002X\u0083.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0015"}, d2 = {"Lcom/auraai/domain/usecase/AuthUseCasesTest;", "", "()V", "repository", "Lcom/auraai/domain/repository/AuthRepository;", "signInUseCase", "Lcom/auraai/domain/usecase/SignInUseCase;", "signInWithGoogleUseCase", "Lcom/auraai/domain/usecase/SignInWithGoogleUseCase;", "signUpUseCase", "Lcom/auraai/domain/usecase/SignUpUseCase;", "syncUserUseCase", "Lcom/auraai/domain/usecase/SyncUserUseCase;", "setUp", "", "signInWithGoogle_withValidToken_returnsSuccess", "Lkotlinx/coroutines/test/TestResult;", "signIn_withEmptyInputs_returnsFailure", "signIn_withValidInputs_returnsSuccess", "signUp_withValidInputs_returnsSuccess", "syncUser_callsRepository_returnsSuccess", "app_debugUnitTest"})
public final class AuthUseCasesTest {
    @org.mockito.Mock()
    private com.auraai.domain.repository.AuthRepository repository;
    private com.auraai.domain.usecase.SignInUseCase signInUseCase;
    private com.auraai.domain.usecase.SignUpUseCase signUpUseCase;
    private com.auraai.domain.usecase.SignInWithGoogleUseCase signInWithGoogleUseCase;
    private com.auraai.domain.usecase.SyncUserUseCase syncUserUseCase;
    
    public AuthUseCasesTest() {
        super();
    }
    
    @org.junit.Before()
    public final void setUp() {
    }
    
    @org.junit.Test()
    public final void signIn_withValidInputs_returnsSuccess() {
    }
    
    @org.junit.Test()
    public final void signIn_withEmptyInputs_returnsFailure() {
    }
    
    @org.junit.Test()
    public final void signUp_withValidInputs_returnsSuccess() {
    }
    
    @org.junit.Test()
    public final void signInWithGoogle_withValidToken_returnsSuccess() {
    }
    
    @org.junit.Test()
    public final void syncUser_callsRepository_returnsSuccess() {
    }
}