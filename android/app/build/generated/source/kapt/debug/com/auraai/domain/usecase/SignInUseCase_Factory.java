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
public final class SignInUseCase_Factory implements Factory<SignInUseCase> {
  private final Provider<AuthRepository> repositoryProvider;

  public SignInUseCase_Factory(Provider<AuthRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public SignInUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static SignInUseCase_Factory create(Provider<AuthRepository> repositoryProvider) {
    return new SignInUseCase_Factory(repositoryProvider);
  }

  public static SignInUseCase newInstance(AuthRepository repository) {
    return new SignInUseCase(repository);
  }
}
