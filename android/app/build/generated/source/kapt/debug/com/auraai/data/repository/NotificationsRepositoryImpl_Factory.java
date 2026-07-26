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
public final class NotificationsRepositoryImpl_Factory implements Factory<NotificationsRepositoryImpl> {
  private final Provider<AuraApiService> apiServiceProvider;

  public NotificationsRepositoryImpl_Factory(Provider<AuraApiService> apiServiceProvider) {
    this.apiServiceProvider = apiServiceProvider;
  }

  @Override
  public NotificationsRepositoryImpl get() {
    return newInstance(apiServiceProvider.get());
  }

  public static NotificationsRepositoryImpl_Factory create(
      Provider<AuraApiService> apiServiceProvider) {
    return new NotificationsRepositoryImpl_Factory(apiServiceProvider);
  }

  public static NotificationsRepositoryImpl newInstance(AuraApiService apiService) {
    return new NotificationsRepositoryImpl(apiService);
  }
}
