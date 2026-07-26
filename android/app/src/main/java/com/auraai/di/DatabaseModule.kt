package com.auraai.di

import android.content.Context
import androidx.room.Room
import com.auraai.data.local.db.AuraDatabase
import com.auraai.data.local.db.MoodDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Hilt DI module for database configurations.
 */
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AuraDatabase {
        return Room.databaseBuilder(
            context,
            AuraDatabase::class.java,
            "aura_database"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideMoodDao(database: AuraDatabase): MoodDao {
        return database.moodDao()
    }

    @Provides
    @Singleton
    fun provideStoriesDao(database: AuraDatabase): com.auraai.data.local.dao.StoriesDao {
        return database.storiesDao()
    }

    @Provides
    @Singleton
    fun provideQuestDao(database: AuraDatabase): com.auraai.data.local.db.QuestDao {
        return database.questDao()
    }
}
