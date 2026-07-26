package com.auraai.ui.games;

import com.auraai.data.local.db.QuestDao;
import com.auraai.domain.usecase.GetAchievementsUseCase;
import com.auraai.domain.usecase.GetCurrentUserTokenUseCase;
import com.auraai.domain.usecase.GetGamesUseCase;
import com.auraai.domain.usecase.SubmitGameScoreUseCase;
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
public final class GamesViewModel_Factory implements Factory<GamesViewModel> {
  private final Provider<GetGamesUseCase> getGamesUseCaseProvider;

  private final Provider<SubmitGameScoreUseCase> submitGameScoreUseCaseProvider;

  private final Provider<GetAchievementsUseCase> getAchievementsUseCaseProvider;

  private final Provider<GetCurrentUserTokenUseCase> getTokenUseCaseProvider;

  private final Provider<QuestDao> questDaoProvider;

  public GamesViewModel_Factory(Provider<GetGamesUseCase> getGamesUseCaseProvider,
      Provider<SubmitGameScoreUseCase> submitGameScoreUseCaseProvider,
      Provider<GetAchievementsUseCase> getAchievementsUseCaseProvider,
      Provider<GetCurrentUserTokenUseCase> getTokenUseCaseProvider,
      Provider<QuestDao> questDaoProvider) {
    this.getGamesUseCaseProvider = getGamesUseCaseProvider;
    this.submitGameScoreUseCaseProvider = submitGameScoreUseCaseProvider;
    this.getAchievementsUseCaseProvider = getAchievementsUseCaseProvider;
    this.getTokenUseCaseProvider = getTokenUseCaseProvider;
    this.questDaoProvider = questDaoProvider;
  }

  @Override
  public GamesViewModel get() {
    return newInstance(getGamesUseCaseProvider.get(), submitGameScoreUseCaseProvider.get(), getAchievementsUseCaseProvider.get(), getTokenUseCaseProvider.get(), questDaoProvider.get());
  }

  public static GamesViewModel_Factory create(Provider<GetGamesUseCase> getGamesUseCaseProvider,
      Provider<SubmitGameScoreUseCase> submitGameScoreUseCaseProvider,
      Provider<GetAchievementsUseCase> getAchievementsUseCaseProvider,
      Provider<GetCurrentUserTokenUseCase> getTokenUseCaseProvider,
      Provider<QuestDao> questDaoProvider) {
    return new GamesViewModel_Factory(getGamesUseCaseProvider, submitGameScoreUseCaseProvider, getAchievementsUseCaseProvider, getTokenUseCaseProvider, questDaoProvider);
  }

  public static GamesViewModel newInstance(GetGamesUseCase getGamesUseCase,
      SubmitGameScoreUseCase submitGameScoreUseCase, GetAchievementsUseCase getAchievementsUseCase,
      GetCurrentUserTokenUseCase getTokenUseCase, QuestDao questDao) {
    return new GamesViewModel(getGamesUseCase, submitGameScoreUseCase, getAchievementsUseCase, getTokenUseCase, questDao);
  }
}
