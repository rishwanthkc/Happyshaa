package com.auraai.data.repository;

import com.auraai.data.local.dao.StoriesDao;
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
public final class StoriesRepositoryImpl_Factory implements Factory<StoriesRepositoryImpl> {
  private final Provider<AuraApiService> apiServiceProvider;

  private final Provider<StoriesDao> storiesDaoProvider;

  public StoriesRepositoryImpl_Factory(Provider<AuraApiService> apiServiceProvider,
      Provider<StoriesDao> storiesDaoProvider) {
    this.apiServiceProvider = apiServiceProvider;
    this.storiesDaoProvider = storiesDaoProvider;
  }

  @Override
  public StoriesRepositoryImpl get() {
    return newInstance(apiServiceProvider.get(), storiesDaoProvider.get());
  }

  public static StoriesRepositoryImpl_Factory create(Provider<AuraApiService> apiServiceProvider,
      Provider<StoriesDao> storiesDaoProvider) {
    return new StoriesRepositoryImpl_Factory(apiServiceProvider, storiesDaoProvider);
  }

  public static StoriesRepositoryImpl newInstance(AuraApiService apiService,
      StoriesDao storiesDao) {
    return new StoriesRepositoryImpl(apiService, storiesDao);
  }
}
