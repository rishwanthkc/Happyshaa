package com.auraai.ui.story;

import android.content.Context;
import com.auraai.domain.usecase.GetCurrentUserTokenUseCase;
import com.auraai.domain.usecase.StoryUseCases;
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
public final class StoryGeneratorViewModel_Factory implements Factory<StoryGeneratorViewModel> {
  private final Provider<StoryUseCases> storyUseCasesProvider;

  private final Provider<GetCurrentUserTokenUseCase> getTokenUseCaseProvider;

  private final Provider<Context> contextProvider;

  public StoryGeneratorViewModel_Factory(Provider<StoryUseCases> storyUseCasesProvider,
      Provider<GetCurrentUserTokenUseCase> getTokenUseCaseProvider,
      Provider<Context> contextProvider) {
    this.storyUseCasesProvider = storyUseCasesProvider;
    this.getTokenUseCaseProvider = getTokenUseCaseProvider;
    this.contextProvider = contextProvider;
  }

  @Override
  public StoryGeneratorViewModel get() {
    return newInstance(storyUseCasesProvider.get(), getTokenUseCaseProvider.get(), contextProvider.get());
  }

  public static StoryGeneratorViewModel_Factory create(
      Provider<StoryUseCases> storyUseCasesProvider,
      Provider<GetCurrentUserTokenUseCase> getTokenUseCaseProvider,
      Provider<Context> contextProvider) {
    return new StoryGeneratorViewModel_Factory(storyUseCasesProvider, getTokenUseCaseProvider, contextProvider);
  }

  public static StoryGeneratorViewModel newInstance(StoryUseCases storyUseCases,
      GetCurrentUserTokenUseCase getTokenUseCase, Context context) {
    return new StoryGeneratorViewModel(storyUseCases, getTokenUseCase, context);
  }
}
