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
public final class GetCurrentUserTokenUseCase_Factory implements Factory<GetCurrentUserTokenUseCase> {
  private final Provider<AuthRepository> repositoryProvider;

  public GetCurrentUserTokenUseCase_Factory(Provider<AuthRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public GetCurrentUserTokenUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static GetCurrentUserTokenUseCase_Factory create(
      Provider<AuthRepository> repositoryProvider) {
    return new GetCurrentUserTokenUseCase_Factory(repositoryProvider);
  }

  public static GetCurrentUserTokenUseCase newInstance(AuthRepository repository) {
    return new GetCurrentUserTokenUseCase(repository);
  }
}
