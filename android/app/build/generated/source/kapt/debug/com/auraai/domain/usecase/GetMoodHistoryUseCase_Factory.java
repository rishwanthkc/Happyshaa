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
public final class GetMoodHistoryUseCase_Factory implements Factory<GetMoodHistoryUseCase> {
  private final Provider<MoodRepository> repositoryProvider;

  public GetMoodHistoryUseCase_Factory(Provider<MoodRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public GetMoodHistoryUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static GetMoodHistoryUseCase_Factory create(Provider<MoodRepository> repositoryProvider) {
    return new GetMoodHistoryUseCase_Factory(repositoryProvider);
  }

  public static GetMoodHistoryUseCase newInstance(MoodRepository repository) {
    return new GetMoodHistoryUseCase(repository);
  }
}
