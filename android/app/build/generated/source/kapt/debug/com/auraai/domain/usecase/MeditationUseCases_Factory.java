package com.auraai.domain.usecase;

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
public final class MeditationUseCases_Factory implements Factory<MeditationUseCases> {
  private final Provider<SubmitMeditationSessionUseCase> submitSessionProvider;

  private final Provider<GetMeditationHistoryUseCase> getHistoryProvider;

  public MeditationUseCases_Factory(Provider<SubmitMeditationSessionUseCase> submitSessionProvider,
      Provider<GetMeditationHistoryUseCase> getHistoryProvider) {
    this.submitSessionProvider = submitSessionProvider;
    this.getHistoryProvider = getHistoryProvider;
  }

  @Override
  public MeditationUseCases get() {
    return newInstance(submitSessionProvider.get(), getHistoryProvider.get());
  }

  public static MeditationUseCases_Factory create(
      Provider<SubmitMeditationSessionUseCase> submitSessionProvider,
      Provider<GetMeditationHistoryUseCase> getHistoryProvider) {
    return new MeditationUseCases_Factory(submitSessionProvider, getHistoryProvider);
  }

  public static MeditationUseCases newInstance(SubmitMeditationSessionUseCase submitSession,
      GetMeditationHistoryUseCase getHistory) {
    return new MeditationUseCases(submitSession, getHistory);
  }
}
