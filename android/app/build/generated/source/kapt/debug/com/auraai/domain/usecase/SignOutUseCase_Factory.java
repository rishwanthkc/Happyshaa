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
public final class SignOutUseCase_Factory implements Factory<SignOutUseCase> {
  private final Provider<AuthRepository> repositoryProvider;

  public SignOutUseCase_Factory(Provider<AuthRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public SignOutUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static SignOutUseCase_Factory create(Provider<AuthRepository> repositoryProvider) {
    return new SignOutUseCase_Factory(repositoryProvider);
  }

  public static SignOutUseCase newInstance(AuthRepository repository) {
    return new SignOutUseCase(repository);
  }
}
