package com.auraai.domain.usecase;

import com.auraai.domain.repository.AuthRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
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
public final class SyncUserUseCase_Factory implements Factory<SyncUserUseCase> {
  private final Provider<AuthRepository> repositoryProvider;

  public SyncUserUseCase_Factory(Provider<AuthRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public SyncUserUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static SyncUserUseCase_Factory create(Provider<AuthRepository> repositoryProvider) {
    return new SyncUserUseCase_Factory(repositoryProvider);
  }

  public static SyncUserUseCase newInstance(AuthRepository repository) {
    return new SyncUserUseCase(repository);
  }
}
