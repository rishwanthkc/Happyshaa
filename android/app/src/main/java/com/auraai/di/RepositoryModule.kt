package com.auraai.di

import com.auraai.data.repository.ChatRepositoryImpl
import com.auraai.data.repository.MoodRepositoryImpl
import com.auraai.domain.repository.ChatRepository
import com.auraai.domain.repository.MoodRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindMoodRepository(
        moodRepositoryImpl: MoodRepositoryImpl
    ): MoodRepository

    @Binds
    @Singleton
    abstract fun bindChatRepository(
        chatRepositoryImpl: ChatRepositoryImpl
    ): ChatRepository

    @Binds
    @Singleton
    abstract fun bindMusicRepository(
        musicRepositoryImpl: com.auraai.data.repository.MusicRepositoryImpl
    ): com.auraai.domain.repository.MusicRepository

    @Binds
    @Singleton
    abstract fun bindContactsRepository(
        contactsRepositoryImpl: com.auraai.data.repository.ContactsRepositoryImpl
    ): com.auraai.domain.repository.ContactsRepository

    @Binds
    @Singleton
    abstract fun bindGamesRepository(
        gamesRepositoryImpl: com.auraai.data.repository.GamesRepositoryImpl
    ): com.auraai.domain.repository.GamesRepository

    @Binds
    @Singleton
    abstract fun bindJournalRepository(
        journalRepositoryImpl: com.auraai.data.repository.JournalRepositoryImpl
    ): com.auraai.domain.repository.JournalRepository

    @Binds
    @Singleton
    abstract fun bindRecommendationRepository(
        recommendationRepositoryImpl: com.auraai.data.repository.RecommendationRepositoryImpl
    ): com.auraai.domain.repository.RecommendationRepository

    @Binds
    @Singleton
    abstract fun bindNotificationsRepository(
        notificationsRepositoryImpl: com.auraai.data.repository.NotificationsRepositoryImpl
    ): com.auraai.domain.repository.NotificationsRepository

    @Binds
    @Singleton
    abstract fun bindStoriesRepository(
        storiesRepositoryImpl: com.auraai.data.repository.StoriesRepositoryImpl
    ): com.auraai.domain.repository.StoriesRepository

    @Binds
    @Singleton
    abstract fun bindMeditationRepository(
        meditationRepositoryImpl: com.auraai.data.repository.MeditationRepositoryImpl
    ): com.auraai.domain.repository.MeditationRepository
}
