package com.auraai.data.repository;

/**
 * Implementation of [AuthRepository] using Firebase Authentication.
 */
@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0014\b\u0007\u0018\u00002\u00020\u0001B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u001c\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011H\u0096@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u0013\u0010\u0014J$\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00160\u00112\u0006\u0010\u0017\u001a\u00020\u0012H\u0096@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u0018\u0010\u0019J,\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\r0\u00112\u0006\u0010\u0017\u001a\u00020\u00122\u0006\u0010\u001b\u001a\u00020\u0012H\u0096@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u001c\u0010\u001dJ$\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\r0\u00112\u0006\u0010\u001f\u001a\u00020\u0012H\u0096@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b \u0010\u0019J\u001c\u0010!\u001a\b\u0012\u0004\u0012\u00020\u00160\u0011H\u0096@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\"\u0010\u0014J4\u0010#\u001a\b\u0012\u0004\u0012\u00020\r0\u00112\u0006\u0010\u0017\u001a\u00020\u00122\u0006\u0010$\u001a\u00020\u00122\u0006\u0010\u001b\u001a\u00020\u0012H\u0096@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b%\u0010&J0\u0010\'\u001a\b\u0012\u0004\u0012\u00020\u00160\u00112\b\u0010$\u001a\u0004\u0018\u00010\u00122\b\u0010(\u001a\u0004\u0018\u00010\u0012H\u0096@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b)\u0010\u001dR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\f\u001a\u0004\u0018\u00010\rX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u000e\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\t0\u000fX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u000b\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006*"}, d2 = {"Lcom/auraai/data/repository/FirebaseAuthRepositoryImpl;", "Lcom/auraai/domain/repository/AuthRepository;", "firebaseAuth", "Lcom/google/firebase/auth/FirebaseAuth;", "apiService", "Lcom/auraai/data/remote/api/AuraApiService;", "(Lcom/google/firebase/auth/FirebaseAuth;Lcom/auraai/data/remote/api/AuraApiService;)V", "authState", "Lkotlinx/coroutines/flow/Flow;", "Lcom/auraai/domain/model/AuthState;", "getAuthState", "()Lkotlinx/coroutines/flow/Flow;", "mockUser", "Lcom/auraai/domain/model/User;", "mockUserFlow", "Lkotlinx/coroutines/flow/MutableStateFlow;", "getCurrentUserToken", "Lkotlin/Result;", "", "getCurrentUserToken-IoAF18A", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "sendPasswordResetEmail", "", "email", "sendPasswordResetEmail-gIAlu-s", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "signInWithEmailAndPassword", "password", "signInWithEmailAndPassword-0E7RQCE", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "signInWithGoogle", "idToken", "signInWithGoogle-gIAlu-s", "signOut", "signOut-IoAF18A", "signUpWithEmailAndPassword", "displayName", "signUpWithEmailAndPassword-BWLJW6A", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "syncUserWithBackend", "photoUrl", "syncUserWithBackend-0E7RQCE", "app_debug"})
public final class FirebaseAuthRepositoryImpl implements com.auraai.domain.repository.AuthRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.google.firebase.auth.FirebaseAuth firebaseAuth = null;
    @org.jetbrains.annotations.NotNull()
    private final com.auraai.data.remote.api.AuraApiService apiService = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.auraai.domain.model.AuthState> mockUserFlow = null;
    @org.jetbrains.annotations.Nullable()
    private com.auraai.domain.model.User mockUser;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.Flow<com.auraai.domain.model.AuthState> authState = null;
    
    @javax.inject.Inject()
    public FirebaseAuthRepositoryImpl(@org.jetbrains.annotations.NotNull()
    com.google.firebase.auth.FirebaseAuth firebaseAuth, @org.jetbrains.annotations.NotNull()
    com.auraai.data.remote.api.AuraApiService apiService) {
        super();
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<com.auraai.domain.model.AuthState> getAuthState() {
        return null;
    }
}