package com.auraai.data.local.db

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase
import kotlinx.coroutines.flow.Flow

import com.auraai.data.local.entity.CachedStoryEntity
import com.auraai.data.local.dao.StoriesDao

/**
 * Local Room Entity for caching mood entries before syncing with Firestore.
 */
@Entity(tableName = "cached_moods")
data class CachedMoodEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val uid: String,
    val timestamp: Long,
    val score: Int,
    val note: String,
    val isSynced: Boolean
)

/**
 * Data Access Object for local mood cache operations.
 */
@Dao
interface MoodDao {
    
    @Query("SELECT * FROM cached_moods WHERE uid = :uid ORDER BY timestamp DESC")
    fun getCachedMoods(uid: String): Flow<List<CachedMoodEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMood(mood: CachedMoodEntity)

    @Query("DELETE FROM cached_moods WHERE id = :id")
    suspend fun deleteMood(id: Int)
}

/**
 * Local Room Entity representing a Daily Quest / Task.
 */
@Entity(tableName = "quests")
data class QuestEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val text: String,
    val isCompleted: Boolean
)

/**
 * Data Access Object for local Quest/Task list management.
 */
@Dao
interface QuestDao {
    @Query("SELECT * FROM quests ORDER BY id ASC")
    fun getAllQuests(): Flow<List<QuestEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuest(quest: QuestEntity)

    @Query("DELETE FROM quests WHERE id = :id")
    suspend fun deleteQuest(id: Int)
    
    @Query("UPDATE quests SET isCompleted = :completed WHERE id = :id")
    suspend fun updateQuestCompletion(id: Int, completed: Boolean)
}

/**
 * Local database definition using Room.
 */
@Database(entities = [CachedMoodEntity::class, CachedStoryEntity::class, QuestEntity::class], version = 2, exportSchema = false)
abstract class AuraDatabase : RoomDatabase() {
    abstract fun moodDao(): MoodDao
    abstract fun storiesDao(): StoriesDao
    abstract fun questDao(): QuestDao
}
