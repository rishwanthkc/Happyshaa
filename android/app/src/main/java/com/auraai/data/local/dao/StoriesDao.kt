package com.auraai.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.auraai.data.local.entity.CachedStoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface StoriesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStory(story: CachedStoryEntity)

    @Query("SELECT * FROM cached_stories WHERE uid = :uid ORDER BY timestamp DESC")
    fun getStories(uid: String): Flow<List<CachedStoryEntity>>

    @Query("UPDATE cached_stories SET isFavorite = :isFavorite WHERE storyId = :storyId")
    suspend fun updateFavoriteStatus(storyId: String, isFavorite: Boolean)

    @Query("DELETE FROM cached_stories WHERE storyId = :storyId")
    suspend fun deleteStory(storyId: String)
}
