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
public final class StoryUseCases_Factory implements Factory<StoryUseCases> {
  private final Provider<GenerateStoryStreamUseCase> generateStoryProvider;

  private final Provider<GetStoryHistoryUseCase> getStoryHistoryProvider;

  private final Provider<ToggleStoryFavoriteUseCase> toggleStoryFavoriteProvider;

  private final Provider<SyncStoriesHistoryUseCase> syncStoriesHistoryProvider;

  public StoryUseCases_Factory(Provider<GenerateStoryStreamUseCase> generateStoryProvider,
      Provider<GetStoryHistoryUseCase> getStoryHistoryProvider,
      Provider<ToggleStoryFavoriteUseCase> toggleStoryFavoriteProvider,
      Provider<SyncStoriesHistoryUseCase> syncStoriesHistoryProvider) {
    this.generateStoryProvider = generateStoryProvider;
    this.getStoryHistoryProvider = getStoryHistoryProvider;
    this.toggleStoryFavoriteProvider = toggleStoryFavoriteProvider;
    this.syncStoriesHistoryProvider = syncStoriesHistoryProvider;
  }

  @Override
  public StoryUseCases get() {
    return newInstance(generateStoryProvider.get(), getStoryHistoryProvider.get(), toggleStoryFavoriteProvider.get(), syncStoriesHistoryProvider.get());
  }

  public static StoryUseCases_Factory create(
      Provider<GenerateStoryStreamUseCase> generateStoryProvider,
      Provider<GetStoryHistoryUseCase> getStoryHistoryProvider,
      Provider<ToggleStoryFavoriteUseCase> toggleStoryFavoriteProvider,
      Provider<SyncStoriesHistoryUseCase> syncStoriesHistoryProvider) {
    return new StoryUseCases_Factory(generateStoryProvider, getStoryHistoryProvider, toggleStoryFavoriteProvider, syncStoriesHistoryProvider);
  }

  public static StoryUseCases newInstance(GenerateStoryStreamUseCase generateStory,
      GetStoryHistoryUseCase getStoryHistory, ToggleStoryFavoriteUseCase toggleStoryFavorite,
      SyncStoriesHistoryUseCase syncStoriesHistory) {
    return new StoryUseCases(generateStory, getStoryHistory, toggleStoryFavorite, syncStoriesHistory);
  }
}
