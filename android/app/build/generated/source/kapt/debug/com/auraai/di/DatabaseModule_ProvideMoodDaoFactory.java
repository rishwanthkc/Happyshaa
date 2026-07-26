package com.auraai.di;

import com.auraai.data.local.db.AuraDatabase;
import com.auraai.data.local.db.MoodDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava"
})
public final class DatabaseModule_ProvideMoodDaoFactory implements Factory<MoodDao> {
  private final Provider<AuraDatabase> databaseProvider;

  public DatabaseModule_ProvideMoodDaoFactory(Provider<AuraDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public MoodDao get() {
    return provideMoodDao(databaseProvider.get());
  }

  public static DatabaseModule_ProvideMoodDaoFactory create(
      Provider<AuraDatabase> databaseProvider) {
    return new DatabaseModule_ProvideMoodDaoFactory(databaseProvider);
  }

  public static MoodDao provideMoodDao(AuraDatabase database) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideMoodDao(database));
  }
}
