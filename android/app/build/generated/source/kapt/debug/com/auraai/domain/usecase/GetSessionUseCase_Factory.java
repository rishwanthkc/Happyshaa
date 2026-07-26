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
public final class GetSessionUseCase_Factory implements Factory<GetSessionUseCase> {
  private final Provider<AuthRepository> repositoryProvider;

  public GetSessionUseCase_Factory(Provider<AuthRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public GetSessionUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static GetSessionUseCase_Factory create(Provider<AuthRepository> repositoryProvider) {
    return new GetSessionUseCase_Factory(repositoryProvider);
  }

  public static GetSessionUseCase newInstance(AuthRepository repository) {
    return new GetSessionUseCase(repository);
  }
}
