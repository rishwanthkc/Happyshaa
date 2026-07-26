package com.auraai.di;

import com.auraai.data.local.db.AuraDatabase;
import com.auraai.data.local.db.QuestDao;
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
public final class DatabaseModule_ProvideQuestDaoFactory implements Factory<QuestDao> {
  private final Provider<AuraDatabase> databaseProvider;

  public DatabaseModule_ProvideQuestDaoFactory(Provider<AuraDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public QuestDao get() {
    return provideQuestDao(databaseProvider.get());
  }

  public static DatabaseModule_ProvideQuestDaoFactory create(
      Provider<AuraDatabase> databaseProvider) {
    return new DatabaseModule_ProvideQuestDaoFactory(databaseProvider);
  }

  public static QuestDao provideQuestDao(AuraDatabase database) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideQuestDao(database));
  }
}
