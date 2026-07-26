package com.auraai.data.repository;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J,\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000bH\u0096@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\r\u0010\u000eJ&\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u000b2\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u000bH\u0096@\u00a2\u0006\u0002\u0010\u0015J\u001c\u0010\u0016\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\u00180\u00172\u0006\u0010\u0011\u001a\u00020\u000bH\u0016J\f\u0010\u0019\u001a\u00020\t*\u00020\u001aH\u0002J\f\u0010\u0019\u001a\u00020\t*\u00020\u001bH\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u000b\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006\u001c"}, d2 = {"Lcom/auraai/data/repository/MoodRepositoryImpl;", "Lcom/auraai/domain/repository/MoodRepository;", "apiService", "Lcom/auraai/data/remote/api/AuraApiService;", "moodDao", "Lcom/auraai/data/local/db/MoodDao;", "(Lcom/auraai/data/remote/api/AuraApiService;Lcom/auraai/data/local/db/MoodDao;)V", "analyzeMood", "Lkotlin/Result;", "Lcom/auraai/domain/model/MoodLog;", "token", "", "text", "analyzeMood-0E7RQCE", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "cacheMoodLocally", "", "uid", "score", "", "note", "(Ljava/lang/String;ILjava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getMoodHistory", "Lkotlinx/coroutines/flow/Flow;", "", "toDomain", "Lcom/auraai/data/local/db/CachedMoodEntity;", "Lcom/auraai/data/remote/api/NetworkMoodLog;", "app_debug"})
public final class MoodRepositoryImpl implements com.auraai.domain.repository.MoodRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.auraai.data.remote.api.AuraApiService apiService = null;
    @org.jetbrains.annotations.NotNull()
    private final com.auraai.data.local.db.MoodDao moodDao = null;
    
    @javax.inject.Inject()
    public MoodRepositoryImpl(@org.jetbrains.annotations.NotNull()
    com.auraai.data.remote.api.AuraApiService apiService, @org.jetbrains.annotations.NotNull()
    com.auraai.data.local.db.MoodDao moodDao) {
        super();
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<java.util.List<com.auraai.domain.model.MoodLog>> getMoodHistory(@org.jetbrains.annotations.NotNull()
    java.lang.String uid) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object cacheMoodLocally(@org.jetbrains.annotations.NotNull()
    java.lang.String uid, int score, @org.jetbrains.annotations.NotNull()
    java.lang.String note, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final com.auraai.domain.model.MoodLog toDomain(com.auraai.data.remote.api.NetworkMoodLog $this$toDomain) {
        return null;
    }
    
    private final com.auraai.domain.model.MoodLog toDomain(com.auraai.data.local.db.CachedMoodEntity $this$toDomain) {
        return null;
    }
}