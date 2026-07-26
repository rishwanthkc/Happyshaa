package com.auraai.data.repository;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J*\u0010\r\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u000b0\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0096@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u0011\u0010\u0012J*\u0010\u0013\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0\u000b0\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0096@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u0014\u0010\u0012J4\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00160\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0017\u001a\u00020\u00102\u0006\u0010\u0018\u001a\u00020\tH\u0096@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u0019\u0010\u001aR\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u000b\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006\u001b"}, d2 = {"Lcom/auraai/data/repository/GamesRepositoryImpl;", "Lcom/auraai/domain/repository/GamesRepository;", "apiService", "Lcom/auraai/data/remote/api/AuraApiService;", "(Lcom/auraai/data/remote/api/AuraApiService;)V", "achievementsLog", "", "Lcom/auraai/domain/model/Achievement;", "localBalance", "", "staticFallbackGames", "", "Lcom/auraai/domain/model/Game;", "getAchievements", "Lkotlin/Result;", "token", "", "getAchievements-gIAlu-s", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getGames", "getGames-gIAlu-s", "submitGameScore", "Lcom/auraai/domain/model/GameProgress;", "gameId", "score", "submitGameScore-BWLJW6A", "(Ljava/lang/String;Ljava/lang/String;ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class GamesRepositoryImpl implements com.auraai.domain.repository.GamesRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.auraai.data.remote.api.AuraApiService apiService = null;
    private int localBalance = 0;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.auraai.domain.model.Achievement> achievementsLog = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.auraai.domain.model.Game> staticFallbackGames = null;
    
    @javax.inject.Inject()
    public GamesRepositoryImpl(@org.jetbrains.annotations.NotNull()
    com.auraai.data.remote.api.AuraApiService apiService) {
        super();
    }
}