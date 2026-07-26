package com.auraai.ui.mood;

import com.auraai.domain.usecase.AnalyzeMoodUseCase;
import com.auraai.domain.usecase.CacheMoodLocallyUseCase;
import com.auraai.domain.usecase.GetCurrentUserTokenUseCase;
import com.auraai.domain.usecase.GetMoodHistoryUseCase;
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
public final class MoodViewModel_Factory implements Factory<MoodViewModel> {
  private final Provider<AnalyzeMoodUseCase> analyzeMoodUseCaseProvider;

  private final Provider<GetMoodHistoryUseCase> getMoodHistoryUseCaseProvider;

  private final Provider<CacheMoodLocallyUseCase> cacheMoodLocallyUseCaseProvider;

  private final Provider<GetCurrentUserTokenUseCase> getTokenUseCaseProvider;

  public MoodViewModel_Factory(Provider<AnalyzeMoodUseCase> analyzeMoodUseCaseProvider,
      Provider<GetMoodHistoryUseCase> getMoodHistoryUseCaseProvider,
      Provider<CacheMoodLocallyUseCase> cacheMoodLocallyUseCaseProvider,
      Provider<GetCurrentUserTokenUseCase> getTokenUseCaseProvider) {
    this.analyzeMoodUseCaseProvider = analyzeMoodUseCaseProvider;
    this.getMoodHistoryUseCaseProvider = getMoodHistoryUseCaseProvider;
    this.cacheMoodLocallyUseCaseProvider = cacheMoodLocallyUseCaseProvider;
    this.getTokenUseCaseProvider = getTokenUseCaseProvider;
  }

  @Override
  public MoodViewModel get() {
    return newInstance(analyzeMoodUseCaseProvider.get(), getMoodHistoryUseCaseProvider.get(), cacheMoodLocallyUseCaseProvider.get(), getTokenUseCaseProvider.get());
  }

  public static MoodViewModel_Factory create(
      Provider<AnalyzeMoodUseCase> analyzeMoodUseCaseProvider,
      Provider<GetMoodHistoryUseCase> getMoodHistoryUseCaseProvider,
      Provider<CacheMoodLocallyUseCase> cacheMoodLocallyUseCaseProvider,
      Provider<GetCurrentUserTokenUseCase> getTokenUseCaseProvider) {
    return new MoodViewModel_Factory(analyzeMoodUseCaseProvider, getMoodHistoryUseCaseProvider, cacheMoodLocallyUseCaseProvider, getTokenUseCaseProvider);
  }

  public static MoodViewModel newInstance(AnalyzeMoodUseCase analyzeMoodUseCase,
      GetMoodHistoryUseCase getMoodHistoryUseCase, CacheMoodLocallyUseCase cacheMoodLocallyUseCase,
      GetCurrentUserTokenUseCase getTokenUseCase) {
    return new MoodViewModel(analyzeMoodUseCase, getMoodHistoryUseCase, cacheMoodLocallyUseCase, getTokenUseCase);
  }
}
