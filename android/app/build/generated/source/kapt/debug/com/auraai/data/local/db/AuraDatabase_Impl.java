package com.auraai.data.local.db;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import com.auraai.data.local.dao.StoriesDao;
import com.auraai.data.local.dao.StoriesDao_Impl;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class AuraDatabase_Impl extends AuraDatabase {
  private volatile MoodDao _moodDao;

  private volatile StoriesDao _storiesDao;

  private volatile QuestDao _questDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(2) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `cached_moods` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `uid` TEXT NOT NULL, `timestamp` INTEGER NOT NULL, `score` INTEGER NOT NULL, `note` TEXT NOT NULL, `isSynced` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `cached_stories` (`storyId` TEXT NOT NULL, `uid` TEXT NOT NULL, `title` TEXT NOT NULL, `content` TEXT NOT NULL, `category` TEXT NOT NULL, `length` TEXT NOT NULL, `timestamp` INTEGER NOT NULL, `isFavorite` INTEGER NOT NULL, PRIMARY KEY(`storyId`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `quests` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `text` TEXT NOT NULL, `isCompleted` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'cb4f5d69c27ccc197d5c66c00c9402a4')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `cached_moods`");
        db.execSQL("DROP TABLE IF EXISTS `cached_stories`");
        db.execSQL("DROP TABLE IF EXISTS `quests`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsCachedMoods = new HashMap<String, TableInfo.Column>(6);
        _columnsCachedMoods.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCachedMoods.put("uid", new TableInfo.Column("uid", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCachedMoods.put("timestamp", new TableInfo.Column("timestamp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCachedMoods.put("score", new TableInfo.Column("score", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCachedMoods.put("note", new TableInfo.Column("note", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCachedMoods.put("isSynced", new TableInfo.Column("isSynced", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysCachedMoods = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesCachedMoods = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoCachedMoods = new TableInfo("cached_moods", _columnsCachedMoods, _foreignKeysCachedMoods, _indicesCachedMoods);
        final TableInfo _existingCachedMoods = TableInfo.read(db, "cached_moods");
        if (!_infoCachedMoods.equals(_existingCachedMoods)) {
          return new RoomOpenHelper.ValidationResult(false, "cached_moods(com.auraai.data.local.db.CachedMoodEntity).\n"
                  + " Expected:\n" + _infoCachedMoods + "\n"
                  + " Found:\n" + _existingCachedMoods);
        }
        final HashMap<String, TableInfo.Column> _columnsCachedStories = new HashMap<String, TableInfo.Column>(8);
        _columnsCachedStories.put("storyId", new TableInfo.Column("storyId", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCachedStories.put("uid", new TableInfo.Column("uid", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCachedStories.put("title", new TableInfo.Column("title", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCachedStories.put("content", new TableInfo.Column("content", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCachedStories.put("category", new TableInfo.Column("category", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCachedStories.put("length", new TableInfo.Column("length", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCachedStories.put("timestamp", new TableInfo.Column("timestamp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCachedStories.put("isFavorite", new TableInfo.Column("isFavorite", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysCachedStories = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesCachedStories = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoCachedStories = new TableInfo("cached_stories", _columnsCachedStories, _foreignKeysCachedStories, _indicesCachedStories);
        final TableInfo _existingCachedStories = TableInfo.read(db, "cached_stories");
        if (!_infoCachedStories.equals(_existingCachedStories)) {
          return new RoomOpenHelper.ValidationResult(false, "cached_stories(com.auraai.data.local.entity.CachedStoryEntity).\n"
                  + " Expected:\n" + _infoCachedStories + "\n"
                  + " Found:\n" + _existingCachedStories);
        }
        final HashMap<String, TableInfo.Column> _columnsQuests = new HashMap<String, TableInfo.Column>(3);
        _columnsQuests.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuests.put("text", new TableInfo.Column("text", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuests.put("isCompleted", new TableInfo.Column("isCompleted", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysQuests = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesQuests = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoQuests = new TableInfo("quests", _columnsQuests, _foreignKeysQuests, _indicesQuests);
        final TableInfo _existingQuests = TableInfo.read(db, "quests");
        if (!_infoQuests.equals(_existingQuests)) {
          return new RoomOpenHelper.ValidationResult(false, "quests(com.auraai.data.local.db.QuestEntity).\n"
                  + " Expected:\n" + _infoQuests + "\n"
                  + " Found:\n" + _existingQuests);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "cb4f5d69c27ccc197d5c66c00c9402a4", "2908b2d01b95a2634cf871a3892a8b13");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "cached_moods","cached_stories","quests");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `cached_moods`");
      _db.execSQL("DELETE FROM `cached_stories`");
      _db.execSQL("DELETE FROM `quests`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(MoodDao.class, MoodDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(StoriesDao.class, StoriesDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(QuestDao.class, QuestDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public MoodDao moodDao() {
    if (_moodDao != null) {
      return _moodDao;
    } else {
      synchronized(this) {
        if(_moodDao == null) {
          _moodDao = new MoodDao_Impl(this);
        }
        return _moodDao;
      }
    }
  }

  @Override
  public StoriesDao storiesDao() {
    if (_storiesDao != null) {
      return _storiesDao;
    } else {
      synchronized(this) {
        if(_storiesDao == null) {
          _storiesDao = new StoriesDao_Impl(this);
        }
        return _storiesDao;
      }
    }
  }

  @Override
  public QuestDao questDao() {
    if (_questDao != null) {
      return _questDao;
    } else {
      synchronized(this) {
        if(_questDao == null) {
          _questDao = new QuestDao_Impl(this);
        }
        return _questDao;
      }
    }
  }
}
