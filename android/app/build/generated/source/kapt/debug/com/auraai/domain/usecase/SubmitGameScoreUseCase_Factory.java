package com.auraai.domain.usecase;

import com.auraai.domain.repository.GamesRepository;
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
public final class SubmitGameScoreUseCase_Factory implements Factory<SubmitGameScoreUseCase> {
  private final Provider<GamesRepository> repoProvider;

  public SubmitGameScoreUseCase_Factory(Provider<GamesRepository> repoProvider) {
    this.repoProvider = repoProvider;
  }

  @Override
  public SubmitGameScoreUseCase get() {
    return newInstance(repoProvider.get());
  }

  public static SubmitGameScoreUseCase_Factory create(Provider<GamesRepository> repoProvider) {
    return new SubmitGameScoreUseCase_Factory(repoProvider);
  }

  public static SubmitGameScoreUseCase newInstance(GamesRepository repo) {
    return new SubmitGameScoreUseCase(repo);
  }
}
