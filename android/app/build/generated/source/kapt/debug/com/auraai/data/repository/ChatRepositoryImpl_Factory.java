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
public final class ChatRepositoryImpl_Factory implements Factory<ChatRepositoryImpl> {
  private final Provider<AuraApiService> apiServiceProvider;

  public ChatRepositoryImpl_Factory(Provider<AuraApiService> apiServiceProvider) {
    this.apiServiceProvider = apiServiceProvider;
  }

  @Override
  public ChatRepositoryImpl get() {
    return newInstance(apiServiceProvider.get());
  }

  public static ChatRepositoryImpl_Factory create(Provider<AuraApiService> apiServiceProvider) {
    return new ChatRepositoryImpl_Factory(apiServiceProvider);
  }

  public static ChatRepositoryImpl newInstance(AuraApiService apiService) {
    return new ChatRepositoryImpl(apiService);
  }
}
