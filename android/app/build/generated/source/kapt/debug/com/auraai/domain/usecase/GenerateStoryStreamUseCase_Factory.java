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
public final class GenerateStoryStreamUseCase_Factory implements Factory<GenerateStoryStreamUseCase> {
  private final Provider<StoriesRepository> repositoryProvider;

  public GenerateStoryStreamUseCase_Factory(Provider<StoriesRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public GenerateStoryStreamUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static GenerateStoryStreamUseCase_Factory create(
      Provider<StoriesRepository> repositoryProvider) {
    return new GenerateStoryStreamUseCase_Factory(repositoryProvider);
  }

  public static GenerateStoryStreamUseCase newInstance(StoriesRepository repository) {
    return new GenerateStoryStreamUseCase(repository);
  }
}
