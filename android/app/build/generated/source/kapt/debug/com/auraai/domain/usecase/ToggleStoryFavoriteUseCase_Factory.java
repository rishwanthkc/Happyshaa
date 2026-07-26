package com.auraai.domain.usecase;

import com.auraai.domain.repository.StoriesRepository;
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
public final class ToggleStoryFavoriteUseCase_Factory implements Factory<ToggleStoryFavoriteUseCase> {
  private final Provider<StoriesRepository> repositoryProvider;

  public ToggleStoryFavoriteUseCase_Factory(Provider<StoriesRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public ToggleStoryFavoriteUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static ToggleStoryFavoriteUseCase_Factory create(
      Provider<StoriesRepository> repositoryProvider) {
    return new ToggleStoryFavoriteUseCase_Factory(repositoryProvider);
  }

  public static ToggleStoryFavoriteUseCase newInstance(StoriesRepository repository) {
    return new ToggleStoryFavoriteUseCase(repository);
  }
}
