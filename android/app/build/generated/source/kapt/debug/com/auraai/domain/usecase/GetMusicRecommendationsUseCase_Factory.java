package com.auraai.domain.usecase;

import com.auraai.domain.repository.MusicRepository;
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
public final class GetMusicRecommendationsUseCase_Factory implements Factory<GetMusicRecommendationsUseCase> {
  private final Provider<MusicRepository> repoProvider;

  public GetMusicRecommendationsUseCase_Factory(Provider<MusicRepository> repoProvider) {
    this.repoProvider = repoProvider;
  }

  @Override
  public GetMusicRecommendationsUseCase get() {
    return newInstance(repoProvider.get());
  }

  public static GetMusicRecommendationsUseCase_Factory create(
      Provider<MusicRepository> repoProvider) {
    return new GetMusicRecommendationsUseCase_Factory(repoProvider);
  }

  public static GetMusicRecommendationsUseCase newInstance(MusicRepository repo) {
    return new GetMusicRecommendationsUseCase(repo);
  }
}
