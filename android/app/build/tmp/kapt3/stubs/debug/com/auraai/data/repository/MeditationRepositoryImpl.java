package com.auraai.data.repository;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0005\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J$\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t2\u0006\u0010\u000b\u001a\u00020\fH\u0096@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\r\u0010\u000eJD\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00070\t2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0010\u001a\u00020\f2\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00122\u0006\u0010\u0014\u001a\u00020\u0012H\u0096@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u0015\u0010\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u000b\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006\u0017"}, d2 = {"Lcom/auraai/data/repository/MeditationRepositoryImpl;", "Lcom/auraai/domain/repository/MeditationRepository;", "apiService", "Lcom/auraai/data/remote/api/AuraApiService;", "(Lcom/auraai/data/remote/api/AuraApiService;)V", "offlineSessions", "", "Lcom/auraai/domain/model/MeditationSession;", "getHistory", "Lkotlin/Result;", "Lcom/auraai/domain/model/MeditationHistory;", "token", "", "getHistory-gIAlu-s", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "submitSession", "breathingType", "durationSeconds", "", "coinsReward", "xpReward", "submitSession-hUnOzRk", "(Ljava/lang/String;Ljava/lang/String;IIILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class MeditationRepositoryImpl implements com.auraai.domain.repository.MeditationRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.auraai.data.remote.api.AuraApiService apiService = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.auraai.domain.model.MeditationSession> offlineSessions = null;
    
    @javax.inject.Inject()
    public MeditationRepositoryImpl(@org.jetbrains.annotations.NotNull()
    com.auraai.data.remote.api.AuraApiService apiService) {
        super();
    }
}