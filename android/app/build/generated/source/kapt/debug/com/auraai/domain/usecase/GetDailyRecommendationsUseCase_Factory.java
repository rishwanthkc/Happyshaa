package com.auraai.domain.usecase;

import com.auraai.domain.repository.RecommendationRepository;
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
public final class GetDailyRecommendationsUseCase_Factory implements Factory<GetDailyRecommendationsUseCase> {
  private final Provider<RecommendationRepository> repoProvider;

  public GetDailyRecommendationsUseCase_Factory(Provider<RecommendationRepository> repoProvider) {
    this.repoProvider = repoProvider;
  }

  @Override
  public GetDailyRecommendationsUseCase get() {
    return newInstance(repoProvider.get());
  }

  public static GetDailyRecommendationsUseCase_Factory create(
      Provider<RecommendationRepository> repoProvider) {
    return new GetDailyRecommendationsUseCase_Factory(repoProvider);
  }

  public static GetDailyRecommendationsUseCase newInstance(RecommendationRepository repo) {
    return new GetDailyRecommendationsUseCase(repo);
  }
}
