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
public final class JournalRepositoryImpl_Factory implements Factory<JournalRepositoryImpl> {
  private final Provider<AuraApiService> apiServiceProvider;

  public JournalRepositoryImpl_Factory(Provider<AuraApiService> apiServiceProvider) {
    this.apiServiceProvider = apiServiceProvider;
  }

  @Override
  public JournalRepositoryImpl get() {
    return newInstance(apiServiceProvider.get());
  }

  public static JournalRepositoryImpl_Factory create(Provider<AuraApiService> apiServiceProvider) {
    return new JournalRepositoryImpl_Factory(apiServiceProvider);
  }

  public static JournalRepositoryImpl newInstance(AuraApiService apiService) {
    return new JournalRepositoryImpl(apiService);
  }
}
