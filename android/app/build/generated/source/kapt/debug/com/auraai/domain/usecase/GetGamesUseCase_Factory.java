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
public final class GetGamesUseCase_Factory implements Factory<GetGamesUseCase> {
  private final Provider<GamesRepository> repoProvider;

  public GetGamesUseCase_Factory(Provider<GamesRepository> repoProvider) {
    this.repoProvider = repoProvider;
  }

  @Override
  public GetGamesUseCase get() {
    return newInstance(repoProvider.get());
  }

  public static GetGamesUseCase_Factory create(Provider<GamesRepository> repoProvider) {
    return new GetGamesUseCase_Factory(repoProvider);
  }

  public static GetGamesUseCase newInstance(GamesRepository repo) {
    return new GetGamesUseCase(repo);
  }
}
