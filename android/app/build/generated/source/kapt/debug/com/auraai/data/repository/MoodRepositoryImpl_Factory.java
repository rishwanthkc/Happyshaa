package com.auraai.data.repository;

import com.auraai.data.local.db.MoodDao;
import com.auraai.data.remote.api.AuraApiService;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class MoodRepositoryImpl_Factory implements Factory<MoodRepositoryImpl> {
  private final Provider<AuraApiService> apiServiceProvider;

  private final Provider<MoodDao> moodDaoProvider;

  public MoodRepositoryImpl_Factory(Provider<AuraApiService> apiServiceProvider,
      Provider<MoodDao> moodDaoProvider) {
    this.apiServiceProvider = apiServiceProvider;
    this.moodDaoProvider = moodDaoProvider;
  }

  @Override
  public MoodRepositoryImpl get() {
    return newInstance(apiServiceProvider.get(), moodDaoProvider.get());
  }

  public static MoodRepositoryImpl_Factory create(Provider<AuraApiService> apiServiceProvider,
      Provider<MoodDao> moodDaoProvider) {
    return new MoodRepositoryImpl_Factory(apiServiceProvider, moodDaoProvider);
  }

  public static MoodRepositoryImpl newInstance(AuraApiService apiService, MoodDao moodDao) {
    return new MoodRepositoryImpl(apiService, moodDao);
  }
}
