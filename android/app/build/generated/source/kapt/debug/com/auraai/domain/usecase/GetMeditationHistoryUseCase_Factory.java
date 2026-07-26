package com.auraai.domain.usecase;

import com.auraai.domain.repository.MeditationRepository;
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
public final class GetMeditationHistoryUseCase_Factory implements Factory<GetMeditationHistoryUseCase> {
  private final Provider<MeditationRepository> repositoryProvider;

  public GetMeditationHistoryUseCase_Factory(Provider<MeditationRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public GetMeditationHistoryUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static GetMeditationHistoryUseCase_Factory create(
      Provider<MeditationRepository> repositoryProvider) {
    return new GetMeditationHistoryUseCase_Factory(repositoryProvider);
  }

  public static GetMeditationHistoryUseCase newInstance(MeditationRepository repository) {
    return new GetMeditationHistoryUseCase(repository);
  }
}
