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
public final class SubmitMeditationSessionUseCase_Factory implements Factory<SubmitMeditationSessionUseCase> {
  private final Provider<MeditationRepository> repositoryProvider;

  public SubmitMeditationSessionUseCase_Factory(Provider<MeditationRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public SubmitMeditationSessionUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static SubmitMeditationSessionUseCase_Factory create(
      Provider<MeditationRepository> repositoryProvider) {
    return new SubmitMeditationSessionUseCase_Factory(repositoryProvider);
  }

  public static SubmitMeditationSessionUseCase newInstance(MeditationRepository repository) {
    return new SubmitMeditationSessionUseCase(repository);
  }
}
