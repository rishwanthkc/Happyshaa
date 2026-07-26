package com.auraai.ui.recommendation;

import com.auraai.domain.usecase.GetCurrentUserTokenUseCase;
import com.auraai.domain.usecase.GetDailyRecommendationsUseCase;
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
public final class RecommendationViewModel_Factory implements Factory<RecommendationViewModel> {
  private final Provider<GetDailyRecommendationsUseCase> getRecommendationsUseCaseProvider;

  private final Provider<GetCurrentUserTokenUseCase> getTokenUseCaseProvider;

  public RecommendationViewModel_Factory(
      Provider<GetDailyRecommendationsUseCase> getRecommendationsUseCaseProvider,
      Provider<GetCurrentUserTokenUseCase> getTokenUseCaseProvider) {
    this.getRecommendationsUseCaseProvider = getRecommendationsUseCaseProvider;
    this.getTokenUseCaseProvider = getTokenUseCaseProvider;
  }

  @Override
  public RecommendationViewModel get() {
    return newInstance(getRecommendationsUseCaseProvider.get(), getTokenUseCaseProvider.get());
  }

  public static RecommendationViewModel_Factory create(
      Provider<GetDailyRecommendationsUseCase> getRecommendationsUseCaseProvider,
      Provider<GetCurrentUserTokenUseCase> getTokenUseCaseProvider) {
    return new RecommendationViewModel_Factory(getRecommendationsUseCaseProvider, getTokenUseCaseProvider);
  }

  public static RecommendationViewModel newInstance(
      GetDailyRecommendationsUseCase getRecommendationsUseCase,
      GetCurrentUserTokenUseCase getTokenUseCase) {
    return new RecommendationViewModel(getRecommendationsUseCase, getTokenUseCase);
  }
}
