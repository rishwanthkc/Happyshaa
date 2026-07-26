package com.auraai.di;

import com.auraai.data.local.dao.StoriesDao;
import com.auraai.data.local.db.AuraDatabase;
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
public final class DatabaseModule_ProvideStoriesDaoFactory implements Factory<StoriesDao> {
  private final Provider<AuraDatabase> databaseProvider;

  public DatabaseModule_ProvideStoriesDaoFactory(Provider<AuraDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public StoriesDao get() {
    return provideStoriesDao(databaseProvider.get());
  }

  public static DatabaseModule_ProvideStoriesDaoFactory create(
      Provider<AuraDatabase> databaseProvider) {
    return new DatabaseModule_ProvideStoriesDaoFactory(databaseProvider);
  }

  public static StoriesDao provideStoriesDao(AuraDatabase database) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideStoriesDao(database));
  }
}
