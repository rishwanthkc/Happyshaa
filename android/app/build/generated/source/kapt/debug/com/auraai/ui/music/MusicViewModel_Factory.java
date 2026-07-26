package com.auraai.ui.music;

import android.content.Context;
import com.auraai.domain.usecase.GetCurrentUserTokenUseCase;
import com.auraai.domain.usecase.GetFavoriteSongsUseCase;
import com.auraai.domain.usecase.GetMusicRecommendationsUseCase;
import com.auraai.domain.usecase.GetSongsUseCase;
import com.auraai.domain.usecase.LogPlaybackHistoryUseCase;
import com.auraai.domain.usecase.ToggleFavoriteSongUseCase;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
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
public final class MusicViewModel_Factory implements Factory<MusicViewModel> {
  private final Provider<GetSongsUseCase> getSongsUseCaseProvider;

  private final Provider<ToggleFavoriteSongUseCase> toggleFavoriteSongUseCaseProvider;

  private final Provider<GetFavoriteSongsUseCase> getFavoriteSongsUseCaseProvider;

  private final Provider<LogPlaybackHistoryUseCase> logPlaybackHistoryUseCaseProvider;

  private final Provider<GetMusicRecommendationsUseCase> getMusicRecommendationsUseCaseProvider;

  private final Provider<GetCurrentUserTokenUseCase> getTokenUseCaseProvider;

  private final Provider<Context> contextProvider;

  public MusicViewModel_Factory(Provider<GetSongsUseCase> getSongsUseCaseProvider,
      Provider<ToggleFavoriteSongUseCase> toggleFavoriteSongUseCaseProvider,
      Provider<GetFavoriteSongsUseCase> getFavoriteSongsUseCaseProvider,
      Provider<LogPlaybackHistoryUseCase> logPlaybackHistoryUseCaseProvider,
      Provider<GetMusicRecommendationsUseCase> getMusicRecommendationsUseCaseProvider,
      Provider<GetCurrentUserTokenUseCase> getTokenUseCaseProvider,
      Provider<Context> contextProvider) {
    this.getSongsUseCaseProvider = getSongsUseCaseProvider;
    this.toggleFavoriteSongUseCaseProvider = toggleFavoriteSongUseCaseProvider;
    this.getFavoriteSongsUseCaseProvider = getFavoriteSongsUseCaseProvider;
    this.logPlaybackHistoryUseCaseProvider = logPlaybackHistoryUseCaseProvider;
    this.getMusicRecommendationsUseCaseProvider = getMusicRecommendationsUseCaseProvider;
    this.getTokenUseCaseProvider = getTokenUseCaseProvider;
    this.contextProvider = contextProvider;
  }

  @Override
  public MusicViewModel get() {
    return newInstance(getSongsUseCaseProvider.get(), toggleFavoriteSongUseCaseProvider.get(), getFavoriteSongsUseCaseProvider.get(), logPlaybackHistoryUseCaseProvider.get(), getMusicRecommendationsUseCaseProvider.get(), getTokenUseCaseProvider.get(), contextProvider.get());
  }

  public static MusicViewModel_Factory create(Provider<GetSongsUseCase> getSongsUseCaseProvider,
      Provider<ToggleFavoriteSongUseCase> toggleFavoriteSongUseCaseProvider,
      Provider<GetFavoriteSongsUseCase> getFavoriteSongsUseCaseProvider,
      Provider<LogPlaybackHistoryUseCase> logPlaybackHistoryUseCaseProvider,
      Provider<GetMusicRecommendationsUseCase> getMusicRecommendationsUseCaseProvider,
      Provider<GetCurrentUserTokenUseCase> getTokenUseCaseProvider,
      Provider<Context> contextProvider) {
    return new MusicViewModel_Factory(getSongsUseCaseProvider, toggleFavoriteSongUseCaseProvider, getFavoriteSongsUseCaseProvider, logPlaybackHistoryUseCaseProvider, getMusicRecommendationsUseCaseProvider, getTokenUseCaseProvider, contextProvider);
  }

  public static MusicViewModel newInstance(GetSongsUseCase getSongsUseCase,
      ToggleFavoriteSongUseCase toggleFavoriteSongUseCase,
      GetFavoriteSongsUseCase getFavoriteSongsUseCase,
      LogPlaybackHistoryUseCase logPlaybackHistoryUseCase,
      GetMusicRecommendationsUseCase getMusicRecommendationsUseCase,
      GetCurrentUserTokenUseCase getTokenUseCase, Context context) {
    return new MusicViewModel(getSongsUseCase, toggleFavoriteSongUseCase, getFavoriteSongsUseCase, logPlaybackHistoryUseCase, getMusicRecommendationsUseCase, getTokenUseCase, context);
  }
}
