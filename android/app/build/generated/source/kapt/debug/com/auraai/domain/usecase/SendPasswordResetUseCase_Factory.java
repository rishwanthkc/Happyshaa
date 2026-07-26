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
public final class SendPasswordResetUseCase_Factory implements Factory<SendPasswordResetUseCase> {
  private final Provider<AuthRepository> repositoryProvider;

  public SendPasswordResetUseCase_Factory(Provider<AuthRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public SendPasswordResetUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static SendPasswordResetUseCase_Factory create(
      Provider<AuthRepository> repositoryProvider) {
    return new SendPasswordResetUseCase_Factory(repositoryProvider);
  }

  public static SendPasswordResetUseCase newInstance(AuthRepository repository) {
    return new SendPasswordResetUseCase(repository);
  }
}
