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
public final class RecommendationRepositoryImpl_Factory implements Factory<RecommendationRepositoryImpl> {
  private final Provider<AuraApiService> apiServiceProvider;

  public RecommendationRepositoryImpl_Factory(Provider<AuraApiService> apiServiceProvider) {
    this.apiServiceProvider = apiServiceProvider;
  }

  @Override
  public RecommendationRepositoryImpl get() {
    return newInstance(apiServiceProvider.get());
  }

  public static RecommendationRepositoryImpl_Factory create(
      Provider<AuraApiService> apiServiceProvider) {
    return new RecommendationRepositoryImpl_Factory(apiServiceProvider);
  }

  public static RecommendationRepositoryImpl newInstance(AuraApiService apiService) {
    return new RecommendationRepositoryImpl(apiService);
  }
}
