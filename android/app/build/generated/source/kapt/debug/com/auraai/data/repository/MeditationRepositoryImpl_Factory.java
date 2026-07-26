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
public final class MeditationRepositoryImpl_Factory implements Factory<MeditationRepositoryImpl> {
  private final Provider<AuraApiService> apiServiceProvider;

  public MeditationRepositoryImpl_Factory(Provider<AuraApiService> apiServiceProvider) {
    this.apiServiceProvider = apiServiceProvider;
  }

  @Override
  public MeditationRepositoryImpl get() {
    return newInstance(apiServiceProvider.get());
  }

  public static MeditationRepositoryImpl_Factory create(
      Provider<AuraApiService> apiServiceProvider) {
    return new MeditationRepositoryImpl_Factory(apiServiceProvider);
  }

  public static MeditationRepositoryImpl newInstance(AuraApiService apiService) {
    return new MeditationRepositoryImpl(apiService);
  }
}
