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
public final class GetAchievementsUseCase_Factory implements Factory<GetAchievementsUseCase> {
  private final Provider<GamesRepository> repoProvider;

  public GetAchievementsUseCase_Factory(Provider<GamesRepository> repoProvider) {
    this.repoProvider = repoProvider;
  }

  @Override
  public GetAchievementsUseCase get() {
    return newInstance(repoProvider.get());
  }

  public static GetAchievementsUseCase_Factory create(Provider<GamesRepository> repoProvider) {
    return new GetAchievementsUseCase_Factory(repoProvider);
  }

  public static GetAchievementsUseCase newInstance(GamesRepository repo) {
    return new GetAchievementsUseCase(repo);
  }
}
