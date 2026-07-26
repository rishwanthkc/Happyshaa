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
public final class SignUpUseCase_Factory implements Factory<SignUpUseCase> {
  private final Provider<AuthRepository> repositoryProvider;

  public SignUpUseCase_Factory(Provider<AuthRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public SignUpUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static SignUpUseCase_Factory create(Provider<AuthRepository> repositoryProvider) {
    return new SignUpUseCase_Factory(repositoryProvider);
  }

  public static SignUpUseCase newInstance(AuthRepository repository) {
    return new SignUpUseCase(repository);
  }
}
