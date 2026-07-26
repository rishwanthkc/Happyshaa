package com.auraai.domain.usecase;

import com.auraai.domain.repository.MoodRepository;
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
public final class AnalyzeMoodUseCase_Factory implements Factory<AnalyzeMoodUseCase> {
  private final Provider<MoodRepository> repositoryProvider;

  public AnalyzeMoodUseCase_Factory(Provider<MoodRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public AnalyzeMoodUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static AnalyzeMoodUseCase_Factory create(Provider<MoodRepository> repositoryProvider) {
    return new AnalyzeMoodUseCase_Factory(repositoryProvider);
  }

  public static AnalyzeMoodUseCase newInstance(MoodRepository repository) {
    return new AnalyzeMoodUseCase(repository);
  }
}
