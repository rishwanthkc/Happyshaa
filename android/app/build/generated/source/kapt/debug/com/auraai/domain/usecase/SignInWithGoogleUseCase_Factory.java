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
public final class SignInWithGoogleUseCase_Factory implements Factory<SignInWithGoogleUseCase> {
  private final Provider<AuthRepository> repositoryProvider;

  public SignInWithGoogleUseCase_Factory(Provider<AuthRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public SignInWithGoogleUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static SignInWithGoogleUseCase_Factory create(
      Provider<AuthRepository> repositoryProvider) {
    return new SignInWithGoogleUseCase_Factory(repositoryProvider);
  }

  public static SignInWithGoogleUseCase newInstance(AuthRepository repository) {
    return new SignInWithGoogleUseCase(repository);
  }
}
