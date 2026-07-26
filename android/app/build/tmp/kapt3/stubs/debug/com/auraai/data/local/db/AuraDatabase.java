package com.auraai.data.local.db;

/**
 * Local database definition using Room.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\'\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H&J\b\u0010\u0005\u001a\u00020\u0006H&J\b\u0010\u0007\u001a\u00020\bH&\u00a8\u0006\t"}, d2 = {"Lcom/auraai/data/local/db/AuraDatabase;", "Landroidx/room/RoomDatabase;", "()V", "moodDao", "Lcom/auraai/data/local/db/MoodDao;", "questDao", "Lcom/auraai/data/local/db/QuestDao;", "storiesDao", "Lcom/auraai/data/local/dao/StoriesDao;", "app_debug"})
@androidx.room.Database(entities = {com.auraai.data.local.db.CachedMoodEntity.class, com.auraai.data.local.entity.CachedStoryEntity.class, com.auraai.data.local.db.QuestEntity.class}, version = 2, exportSchema = false)
public abstract class AuraDatabase extends androidx.room.RoomDatabase {
    
    public AuraDatabase() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.auraai.data.local.db.MoodDao moodDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.auraai.data.local.dao.StoriesDao storiesDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.auraai.data.local.db.QuestDao questDao();
}