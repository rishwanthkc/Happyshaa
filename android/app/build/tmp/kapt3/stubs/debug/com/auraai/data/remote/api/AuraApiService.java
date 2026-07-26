package com.auraai.data.remote.api;

/**
 * Retrofit interface defining Aura AI backend API services.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u00f2\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\"\u0010\u0002\u001a\u00020\u00032\b\b\u0001\u0010\u0004\u001a\u00020\u00052\b\b\u0001\u0010\u0006\u001a\u00020\u0007H\u00a7@\u00a2\u0006\u0002\u0010\bJ\"\u0010\t\u001a\u00020\n2\b\b\u0001\u0010\u0004\u001a\u00020\u00052\b\b\u0001\u0010\u0006\u001a\u00020\u000bH\u00a7@\u00a2\u0006\u0002\u0010\fJ\"\u0010\r\u001a\u00020\u000e2\b\b\u0001\u0010\u0004\u001a\u00020\u00052\b\b\u0001\u0010\u0006\u001a\u00020\u000fH\u00a7@\u00a2\u0006\u0002\u0010\u0010J\"\u0010\u0011\u001a\u00020\u00122\b\b\u0001\u0010\u0004\u001a\u00020\u00052\b\b\u0001\u0010\u0013\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0014J\"\u0010\u0015\u001a\u00020\u00122\b\b\u0001\u0010\u0004\u001a\u00020\u00052\b\b\u0001\u0010\u0006\u001a\u00020\u0016H\u00a7@\u00a2\u0006\u0002\u0010\u0017J\u0018\u0010\u0018\u001a\u00020\u00192\b\b\u0001\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u001aJ\u001e\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001d0\u001c2\b\b\u0001\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u001aJ\u0018\u0010\u001e\u001a\u00020\u001f2\b\b\u0001\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u001aJ\u001e\u0010 \u001a\b\u0012\u0004\u0012\u00020\n0\u001c2\b\b\u0001\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u001aJ\u001e\u0010!\u001a\b\u0012\u0004\u0012\u00020\"0\u001c2\b\b\u0001\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u001aJ\u001e\u0010#\u001a\b\u0012\u0004\u0012\u00020$0\u001c2\b\b\u0001\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u001aJ\u001e\u0010%\u001a\b\u0012\u0004\u0012\u00020&0\u001c2\b\b\u0001\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u001aJ\u001e\u0010\'\u001a\b\u0012\u0004\u0012\u00020\u000e0\u001c2\b\b\u0001\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u001aJ\u0018\u0010(\u001a\u00020)2\b\b\u0001\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u001aJ\u0018\u0010*\u001a\u00020+2\b\b\u0001\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u001aJ\u001e\u0010,\u001a\b\u0012\u0004\u0012\u00020$0\u001c2\b\b\u0001\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u001aJ\u001e\u0010-\u001a\b\u0012\u0004\u0012\u00020.0\u001c2\b\b\u0001\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u001aJ\u001e\u0010/\u001a\b\u0012\u0004\u0012\u00020$0\u001c2\b\b\u0001\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u001aJ\u001e\u00100\u001a\b\u0012\u0004\u0012\u0002010\u001c2\b\b\u0001\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u001aJ\u001e\u00102\u001a\b\u0012\u0004\u0012\u00020\u00190\u001c2\b\b\u0001\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u001aJ\"\u00103\u001a\u00020\u00122\b\b\u0001\u0010\u0004\u001a\u00020\u00052\b\b\u0001\u0010\u0006\u001a\u000204H\u00a7@\u00a2\u0006\u0002\u00105J\"\u00106\u001a\u00020\u00122\b\b\u0001\u0010\u0004\u001a\u00020\u00052\b\b\u0001\u0010\u0006\u001a\u000207H\u00a7@\u00a2\u0006\u0002\u00108J\u0018\u00109\u001a\u00020\u00122\b\b\u0001\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u001aJ\"\u0010:\u001a\u00020\u00122\b\b\u0001\u0010\u0004\u001a\u00020\u00052\b\b\u0001\u0010\u0006\u001a\u00020;H\u00a7@\u00a2\u0006\u0002\u0010<J\"\u0010=\u001a\u00020>2\b\b\u0001\u0010\u0004\u001a\u00020\u00052\b\b\u0001\u0010\u0006\u001a\u00020?H\u00a7@\u00a2\u0006\u0002\u0010@J\"\u0010A\u001a\u00020B2\b\b\u0001\u0010\u0004\u001a\u00020\u00052\b\b\u0001\u0010\u0006\u001a\u00020CH\u00a7@\u00a2\u0006\u0002\u0010DJ\"\u0010E\u001a\u00020F2\b\b\u0001\u0010\u0004\u001a\u00020\u00052\b\b\u0001\u0010\u0006\u001a\u00020GH\u00a7@\u00a2\u0006\u0002\u0010HJ\"\u0010I\u001a\u00020J2\b\b\u0001\u0010\u0004\u001a\u00020\u00052\b\b\u0001\u0010\u0006\u001a\u00020KH\u00a7@\u00a2\u0006\u0002\u0010LJ\"\u0010M\u001a\u00020\u00122\b\b\u0001\u0010\u0004\u001a\u00020\u00052\b\b\u0001\u0010\u0013\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0014J\"\u0010N\u001a\u00020\u00122\b\b\u0001\u0010\u0004\u001a\u00020\u00052\b\b\u0001\u0010\u0006\u001a\u00020OH\u00a7@\u00a2\u0006\u0002\u0010PJ\"\u0010Q\u001a\u0002012\b\b\u0001\u0010\u0004\u001a\u00020\u00052\b\b\u0001\u0010\u0006\u001a\u00020RH\u00a7@\u00a2\u0006\u0002\u0010SJ,\u0010T\u001a\u00020\u00122\b\b\u0001\u0010\u0004\u001a\u00020\u00052\b\b\u0001\u0010\u0013\u001a\u00020\u00052\b\b\u0001\u0010\u0006\u001a\u00020UH\u00a7@\u00a2\u0006\u0002\u0010V\u00a8\u0006W"}, d2 = {"Lcom/auraai/data/remote/api/AuraApiService;", "", "analyzeMood", "Lcom/auraai/data/remote/api/NetworkMoodLog;", "token", "", "request", "Lcom/auraai/data/remote/api/MoodAnalysisRequest;", "(Ljava/lang/String;Lcom/auraai/data/remote/api/MoodAnalysisRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "createContact", "Lcom/auraai/data/remote/api/NetworkContact;", "Lcom/auraai/data/remote/api/ContactCreateRequest;", "(Ljava/lang/String;Lcom/auraai/data/remote/api/ContactCreateRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "createJournalEntry", "Lcom/auraai/data/remote/api/NetworkJournal;", "Lcom/auraai/data/remote/api/JournalCreateRequest;", "(Ljava/lang/String;Lcom/auraai/data/remote/api/JournalCreateRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteContact", "Lokhttp3/ResponseBody;", "contactId", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "generateStoryStream", "Lcom/auraai/data/remote/api/StoryGenerateRequest;", "(Ljava/lang/String;Lcom/auraai/data/remote/api/StoryGenerateRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "generateWeeklyReport", "Lcom/auraai/data/remote/api/NetworkWeeklyReport;", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAchievements", "", "Lcom/auraai/data/remote/api/NetworkAchievement;", "getChatHistory", "Lcom/auraai/data/remote/api/NetworkChatHistoryResponse;", "getContacts", "getDailyRecommendations", "Lcom/auraai/data/remote/api/NetworkRecommendationCard;", "getFavoriteSongs", "Lcom/auraai/data/remote/api/NetworkMusicTrack;", "getGames", "Lcom/auraai/data/remote/api/NetworkGame;", "getJournals", "getMeditationHistory", "Lcom/auraai/data/remote/api/MeditationHistoryResponse;", "getMoodHistory", "Lcom/auraai/data/remote/api/NetworkMoodHistoryResponse;", "getMusicRecommendations", "getNotificationHistory", "Lcom/auraai/data/remote/api/NetworkNotificationItem;", "getSongs", "getStoryHistory", "Lcom/auraai/data/remote/api/StoryResponse;", "getWeeklyReports", "logPlaybackHistory", "Lcom/auraai/data/remote/api/HistoryLogRequest;", "(Ljava/lang/String;Lcom/auraai/data/remote/api/HistoryLogRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "saveFcmToken", "Lcom/auraai/data/remote/api/FCMTokenRequest;", "(Ljava/lang/String;Lcom/auraai/data/remote/api/FCMTokenRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "sendTestNotification", "streamChatResponse", "Lcom/auraai/data/remote/api/ChatMessageRequest;", "(Ljava/lang/String;Lcom/auraai/data/remote/api/ChatMessageRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "submitGameScore", "Lcom/auraai/data/remote/api/NetworkGameProgressResponse;", "Lcom/auraai/data/remote/api/GameScoreSubmitRequest;", "(Ljava/lang/String;Lcom/auraai/data/remote/api/GameScoreSubmitRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "submitMeditationSession", "Lcom/auraai/data/remote/api/MeditationSessionResponse;", "Lcom/auraai/data/remote/api/MeditationSessionRequest;", "(Ljava/lang/String;Lcom/auraai/data/remote/api/MeditationSessionRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "summarizeVoiceJournal", "Lcom/auraai/data/remote/api/VoiceJournalSummarizeResponse;", "Lcom/auraai/data/remote/api/VoiceJournalSummarizeRequest;", "(Ljava/lang/String;Lcom/auraai/data/remote/api/VoiceJournalSummarizeRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "syncUser", "Lcom/auraai/data/remote/api/UserSyncResponse;", "Lcom/auraai/data/remote/api/UserSyncRequest;", "(Ljava/lang/String;Lcom/auraai/data/remote/api/UserSyncRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "toggleFavoriteContact", "toggleFavoriteSong", "Lcom/auraai/data/remote/api/FavoriteToggleRequest;", "(Ljava/lang/String;Lcom/auraai/data/remote/api/FavoriteToggleRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "toggleStoryFavorite", "Lcom/auraai/data/remote/api/StoryFavoriteRequest;", "(Ljava/lang/String;Lcom/auraai/data/remote/api/StoryFavoriteRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateContact", "Lcom/auraai/data/remote/api/ContactUpdateRequest;", "(Ljava/lang/String;Ljava/lang/String;Lcom/auraai/data/remote/api/ContactUpdateRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public abstract interface AuraApiService {
    
    @retrofit2.http.POST(value = "api/v1/auth/sync")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object syncUser(@retrofit2.http.Header(value = "Authorization")
    @org.jetbrains.annotations.NotNull()
    java.lang.String token, @retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.auraai.data.remote.api.UserSyncRequest request, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.auraai.data.remote.api.UserSyncResponse> $completion);
    
    @retrofit2.http.POST(value = "api/v1/mood/analyze")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object analyzeMood(@retrofit2.http.Header(value = "Authorization")
    @org.jetbrains.annotations.NotNull()
    java.lang.String token, @retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.auraai.data.remote.api.MoodAnalysisRequest request, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.auraai.data.remote.api.NetworkMoodLog> $completion);
    
    @retrofit2.http.GET(value = "api/v1/mood/history")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getMoodHistory(@retrofit2.http.Header(value = "Authorization")
    @org.jetbrains.annotations.NotNull()
    java.lang.String token, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.auraai.data.remote.api.NetworkMoodHistoryResponse> $completion);
    
    @retrofit2.http.Streaming()
    @retrofit2.http.POST(value = "api/v1/chat/respond")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object streamChatResponse(@retrofit2.http.Header(value = "Authorization")
    @org.jetbrains.annotations.NotNull()
    java.lang.String token, @retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.auraai.data.remote.api.ChatMessageRequest request, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super okhttp3.ResponseBody> $completion);
    
    @retrofit2.http.GET(value = "api/v1/chat/history")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getChatHistory(@retrofit2.http.Header(value = "Authorization")
    @org.jetbrains.annotations.NotNull()
    java.lang.String token, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.auraai.data.remote.api.NetworkChatHistoryResponse> $completion);
    
    @retrofit2.http.GET(value = "api/v1/music/songs")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getSongs(@retrofit2.http.Header(value = "Authorization")
    @org.jetbrains.annotations.NotNull()
    java.lang.String token, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.auraai.data.remote.api.NetworkMusicTrack>> $completion);
    
    @retrofit2.http.POST(value = "api/v1/music/favorites/toggle")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object toggleFavoriteSong(@retrofit2.http.Header(value = "Authorization")
    @org.jetbrains.annotations.NotNull()
    java.lang.String token, @retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.auraai.data.remote.api.FavoriteToggleRequest request, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super okhttp3.ResponseBody> $completion);
    
    @retrofit2.http.GET(value = "api/v1/music/favorites")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getFavoriteSongs(@retrofit2.http.Header(value = "Authorization")
    @org.jetbrains.annotations.NotNull()
    java.lang.String token, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.auraai.data.remote.api.NetworkMusicTrack>> $completion);
    
    @retrofit2.http.POST(value = "api/v1/music/history")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object logPlaybackHistory(@retrofit2.http.Header(value = "Authorization")
    @org.jetbrains.annotations.NotNull()
    java.lang.String token, @retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.auraai.data.remote.api.HistoryLogRequest request, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super okhttp3.ResponseBody> $completion);
    
    @retrofit2.http.GET(value = "api/v1/music/recommend")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getMusicRecommendations(@retrofit2.http.Header(value = "Authorization")
    @org.jetbrains.annotations.NotNull()
    java.lang.String token, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.auraai.data.remote.api.NetworkMusicTrack>> $completion);
    
    @retrofit2.http.GET(value = "api/v1/contacts")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getContacts(@retrofit2.http.Header(value = "Authorization")
    @org.jetbrains.annotations.NotNull()
    java.lang.String token, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.auraai.data.remote.api.NetworkContact>> $completion);
    
    @retrofit2.http.POST(value = "api/v1/contacts")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object createContact(@retrofit2.http.Header(value = "Authorization")
    @org.jetbrains.annotations.NotNull()
    java.lang.String token, @retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.auraai.data.remote.api.ContactCreateRequest request, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.auraai.data.remote.api.NetworkContact> $completion);
    
    @retrofit2.http.PUT(value = "api/v1/contacts/{contact_id}")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateContact(@retrofit2.http.Header(value = "Authorization")
    @org.jetbrains.annotations.NotNull()
    java.lang.String token, @retrofit2.http.Path(value = "contact_id")
    @org.jetbrains.annotations.NotNull()
    java.lang.String contactId, @retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.auraai.data.remote.api.ContactUpdateRequest request, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super okhttp3.ResponseBody> $completion);
    
    @retrofit2.http.DELETE(value = "api/v1/contacts/{contact_id}")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteContact(@retrofit2.http.Header(value = "Authorization")
    @org.jetbrains.annotations.NotNull()
    java.lang.String token, @retrofit2.http.Path(value = "contact_id")
    @org.jetbrains.annotations.NotNull()
    java.lang.String contactId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super okhttp3.ResponseBody> $completion);
    
    @retrofit2.http.POST(value = "api/v1/contacts/{contact_id}/favorite")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object toggleFavoriteContact(@retrofit2.http.Header(value = "Authorization")
    @org.jetbrains.annotations.NotNull()
    java.lang.String token, @retrofit2.http.Path(value = "contact_id")
    @org.jetbrains.annotations.NotNull()
    java.lang.String contactId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super okhttp3.ResponseBody> $completion);
    
    @retrofit2.http.GET(value = "api/v1/games")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getGames(@retrofit2.http.Header(value = "Authorization")
    @org.jetbrains.annotations.NotNull()
    java.lang.String token, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.auraai.data.remote.api.NetworkGame>> $completion);
    
    @retrofit2.http.POST(value = "api/v1/games/scores")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object submitGameScore(@retrofit2.http.Header(value = "Authorization")
    @org.jetbrains.annotations.NotNull()
    java.lang.String token, @retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.auraai.data.remote.api.GameScoreSubmitRequest request, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.auraai.data.remote.api.NetworkGameProgressResponse> $completion);
    
    @retrofit2.http.GET(value = "api/v1/games/achievements")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getAchievements(@retrofit2.http.Header(value = "Authorization")
    @org.jetbrains.annotations.NotNull()
    java.lang.String token, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.auraai.data.remote.api.NetworkAchievement>> $completion);
    
    @retrofit2.http.POST(value = "api/v1/journal")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object createJournalEntry(@retrofit2.http.Header(value = "Authorization")
    @org.jetbrains.annotations.NotNull()
    java.lang.String token, @retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.auraai.data.remote.api.JournalCreateRequest request, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.auraai.data.remote.api.NetworkJournal> $completion);
    
    @retrofit2.http.GET(value = "api/v1/journal")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getJournals(@retrofit2.http.Header(value = "Authorization")
    @org.jetbrains.annotations.NotNull()
    java.lang.String token, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.auraai.data.remote.api.NetworkJournal>> $completion);
    
    @retrofit2.http.POST(value = "api/v1/journal/weekly/generate")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object generateWeeklyReport(@retrofit2.http.Header(value = "Authorization")
    @org.jetbrains.annotations.NotNull()
    java.lang.String token, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.auraai.data.remote.api.NetworkWeeklyReport> $completion);
    
    @retrofit2.http.GET(value = "api/v1/journal/weekly")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getWeeklyReports(@retrofit2.http.Header(value = "Authorization")
    @org.jetbrains.annotations.NotNull()
    java.lang.String token, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.auraai.data.remote.api.NetworkWeeklyReport>> $completion);
    
    @retrofit2.http.GET(value = "api/v1/recommend")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getDailyRecommendations(@retrofit2.http.Header(value = "Authorization")
    @org.jetbrains.annotations.NotNull()
    java.lang.String token, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.auraai.data.remote.api.NetworkRecommendationCard>> $completion);
    
    @retrofit2.http.POST(value = "api/v1/notifications/token")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object saveFcmToken(@retrofit2.http.Header(value = "Authorization")
    @org.jetbrains.annotations.NotNull()
    java.lang.String token, @retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.auraai.data.remote.api.FCMTokenRequest request, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super okhttp3.ResponseBody> $completion);
    
    @retrofit2.http.GET(value = "api/v1/notifications/history")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getNotificationHistory(@retrofit2.http.Header(value = "Authorization")
    @org.jetbrains.annotations.NotNull()
    java.lang.String token, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.auraai.data.remote.api.NetworkNotificationItem>> $completion);
    
    @retrofit2.http.POST(value = "api/v1/notifications/test")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object sendTestNotification(@retrofit2.http.Header(value = "Authorization")
    @org.jetbrains.annotations.NotNull()
    java.lang.String token, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super okhttp3.ResponseBody> $completion);
    
    @retrofit2.http.Streaming()
    @retrofit2.http.POST(value = "api/v1/stories/generate")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object generateStoryStream(@retrofit2.http.Header(value = "Authorization")
    @org.jetbrains.annotations.NotNull()
    java.lang.String token, @retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.auraai.data.remote.api.StoryGenerateRequest request, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super okhttp3.ResponseBody> $completion);
    
    @retrofit2.http.GET(value = "api/v1/stories/history")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getStoryHistory(@retrofit2.http.Header(value = "Authorization")
    @org.jetbrains.annotations.NotNull()
    java.lang.String token, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.auraai.data.remote.api.StoryResponse>> $completion);
    
    @retrofit2.http.POST(value = "api/v1/stories/favorite")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object toggleStoryFavorite(@retrofit2.http.Header(value = "Authorization")
    @org.jetbrains.annotations.NotNull()
    java.lang.String token, @retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.auraai.data.remote.api.StoryFavoriteRequest request, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.auraai.data.remote.api.StoryResponse> $completion);
    
    @retrofit2.http.POST(value = "api/v1/meditation/session")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object submitMeditationSession(@retrofit2.http.Header(value = "Authorization")
    @org.jetbrains.annotations.NotNull()
    java.lang.String token, @retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.auraai.data.remote.api.MeditationSessionRequest request, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.auraai.data.remote.api.MeditationSessionResponse> $completion);
    
    @retrofit2.http.GET(value = "api/v1/meditation/history")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getMeditationHistory(@retrofit2.http.Header(value = "Authorization")
    @org.jetbrains.annotations.NotNull()
    java.lang.String token, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.auraai.data.remote.api.MeditationHistoryResponse> $completion);
    
    @retrofit2.http.POST(value = "api/v1/journal/voice/summarize")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object summarizeVoiceJournal(@retrofit2.http.Header(value = "Authorization")
    @org.jetbrains.annotations.NotNull()
    java.lang.String token, @retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.auraai.data.remote.api.VoiceJournalSummarizeRequest request, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.auraai.data.remote.api.VoiceJournalSummarizeResponse> $completion);
}