package com.auraai.data.repository;

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
public final class GamesRepositoryImpl_Factory implements Factory<GamesRepositoryImpl> {
  private final Provider<AuraApiService> apiServiceProvider;

  public GamesRepositoryImpl_Factory(Provider<AuraApiService> apiServiceProvider) {
    this.apiServiceProvider = apiServiceProvider;
  }

  @Override
  public GamesRepositoryImpl get() {
    return newInstance(apiServiceProvider.get());
  }

  public static GamesRepositoryImpl_Factory create(Provider<AuraApiService> apiServiceProvider) {
    return new GamesRepositoryImpl_Factory(apiServiceProvider);
  }

  public static GamesRepositoryImpl newInstance(AuraApiService apiService) {
    return new GamesRepositoryImpl(apiService);
  }
}
