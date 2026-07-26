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
public final class MusicRepositoryImpl_Factory implements Factory<MusicRepositoryImpl> {
  private final Provider<AuraApiService> apiServiceProvider;

  public MusicRepositoryImpl_Factory(Provider<AuraApiService> apiServiceProvider) {
    this.apiServiceProvider = apiServiceProvider;
  }

  @Override
  public MusicRepositoryImpl get() {
    return newInstance(apiServiceProvider.get());
  }

  public static MusicRepositoryImpl_Factory create(Provider<AuraApiService> apiServiceProvider) {
    return new MusicRepositoryImpl_Factory(apiServiceProvider);
  }

  public static MusicRepositoryImpl newInstance(AuraApiService apiService) {
    return new MusicRepositoryImpl(apiService);
  }
}
