package com.auraai.di;

@dagger.Module()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0084\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\'\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\'J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\'J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\'J\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\'J\u0010\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0016H\'J\u0010\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001aH\'J\u0010\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001eH\'J\u0010\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\"H\'J\u0010\u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00020&H\'J\u0010\u0010\'\u001a\u00020(2\u0006\u0010)\u001a\u00020*H\'\u00a8\u0006+"}, d2 = {"Lcom/auraai/di/RepositoryModule;", "", "()V", "bindChatRepository", "Lcom/auraai/domain/repository/ChatRepository;", "chatRepositoryImpl", "Lcom/auraai/data/repository/ChatRepositoryImpl;", "bindContactsRepository", "Lcom/auraai/domain/repository/ContactsRepository;", "contactsRepositoryImpl", "Lcom/auraai/data/repository/ContactsRepositoryImpl;", "bindGamesRepository", "Lcom/auraai/domain/repository/GamesRepository;", "gamesRepositoryImpl", "Lcom/auraai/data/repository/GamesRepositoryImpl;", "bindJournalRepository", "Lcom/auraai/domain/repository/JournalRepository;", "journalRepositoryImpl", "Lcom/auraai/data/repository/JournalRepositoryImpl;", "bindMeditationRepository", "Lcom/auraai/domain/repository/MeditationRepository;", "meditationRepositoryImpl", "Lcom/auraai/data/repository/MeditationRepositoryImpl;", "bindMoodRepository", "Lcom/auraai/domain/repository/MoodRepository;", "moodRepositoryImpl", "Lcom/auraai/data/repository/MoodRepositoryImpl;", "bindMusicRepository", "Lcom/auraai/domain/repository/MusicRepository;", "musicRepositoryImpl", "Lcom/auraai/data/repository/MusicRepositoryImpl;", "bindNotificationsRepository", "Lcom/auraai/domain/repository/NotificationsRepository;", "notificationsRepositoryImpl", "Lcom/auraai/data/repository/NotificationsRepositoryImpl;", "bindRecommendationRepository", "Lcom/auraai/domain/repository/RecommendationRepository;", "recommendationRepositoryImpl", "Lcom/auraai/data/repository/RecommendationRepositoryImpl;", "bindStoriesRepository", "Lcom/auraai/domain/repository/StoriesRepository;", "storiesRepositoryImpl", "Lcom/auraai/data/repository/StoriesRepositoryImpl;", "app_debug"})
@dagger.hilt.InstallIn(value = {dagger.hilt.components.SingletonComponent.class})
public abstract class RepositoryModule {
    
    public RepositoryModule() {
        super();
    }
    
    @dagger.Binds()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public abstract com.auraai.domain.repository.MoodRepository bindMoodRepository(@org.jetbrains.annotations.NotNull()
    com.auraai.data.repository.MoodRepositoryImpl moodRepositoryImpl);
    
    @dagger.Binds()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public abstract com.auraai.domain.repository.ChatRepository bindChatRepository(@org.jetbrains.annotations.NotNull()
    com.auraai.data.repository.ChatRepositoryImpl chatRepositoryImpl);
    
    @dagger.Binds()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public abstract com.auraai.domain.repository.MusicRepository bindMusicRepository(@org.jetbrains.annotations.NotNull()
    com.auraai.data.repository.MusicRepositoryImpl musicRepositoryImpl);
    
    @dagger.Binds()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public abstract com.auraai.domain.repository.ContactsRepository bindContactsRepository(@org.jetbrains.annotations.NotNull()
    com.auraai.data.repository.ContactsRepositoryImpl contactsRepositoryImpl);
    
    @dagger.Binds()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public abstract com.auraai.domain.repository.GamesRepository bindGamesRepository(@org.jetbrains.annotations.NotNull()
    com.auraai.data.repository.GamesRepositoryImpl gamesRepositoryImpl);
    
    @dagger.Binds()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public abstract com.auraai.domain.repository.JournalRepository bindJournalRepository(@org.jetbrains.annotations.NotNull()
    com.auraai.data.repository.JournalRepositoryImpl journalRepositoryImpl);
    
    @dagger.Binds()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public abstract com.auraai.domain.repository.RecommendationRepository bindRecommendationRepository(@org.jetbrains.annotations.NotNull()
    com.auraai.data.repository.RecommendationRepositoryImpl recommendationRepositoryImpl);
    
    @dagger.Binds()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public abstract com.auraai.domain.repository.NotificationsRepository bindNotificationsRepository(@org.jetbrains.annotations.NotNull()
    com.auraai.data.repository.NotificationsRepositoryImpl notificationsRepositoryImpl);
    
    @dagger.Binds()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public abstract com.auraai.domain.repository.StoriesRepository bindStoriesRepository(@org.jetbrains.annotations.NotNull()
    com.auraai.data.repository.StoriesRepositoryImpl storiesRepositoryImpl);
    
    @dagger.Binds()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public abstract com.auraai.domain.repository.MeditationRepository bindMeditationRepository(@org.jetbrains.annotations.NotNull()
    com.auraai.data.repository.MeditationRepositoryImpl meditationRepositoryImpl);
}