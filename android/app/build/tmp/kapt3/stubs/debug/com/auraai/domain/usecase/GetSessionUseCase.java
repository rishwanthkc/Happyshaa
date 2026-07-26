package com.auraai.domain.usecase;

/**
 * Use case to retrieve the current active user authentication session flow.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0086\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\b"}, d2 = {"Lcom/auraai/domain/usecase/GetSessionUseCase;", "", "repository", "Lcom/auraai/domain/repository/AuthRepository;", "(Lcom/auraai/domain/repository/AuthRepository;)V", "invoke", "Lkotlinx/coroutines/flow/Flow;", "Lcom/auraai/domain/model/AuthState;", "app_debug"})
public final class GetSessionUseCase {
    @org.jetbrains.annotations.NotNull()
    private final com.auraai.domain.repository.AuthRepository repository = null;
    
    @javax.inject.Inject()
    public GetSessionUseCase(@org.jetbrains.annotations.NotNull()
    com.auraai.domain.repository.AuthRepository repository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<com.auraai.domain.model.AuthState> invoke() {
        return null;
    }
}