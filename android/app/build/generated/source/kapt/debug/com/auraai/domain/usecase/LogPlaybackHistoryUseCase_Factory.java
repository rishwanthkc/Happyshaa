package com.auraai.domain.usecase;

import com.auraai.domain.repository.MusicRepository;
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
public final class LogPlaybackHistoryUseCase_Factory implements Factory<LogPlaybackHistoryUseCase> {
  private final Provider<MusicRepository> repoProvider;

  public LogPlaybackHistoryUseCase_Factory(Provider<MusicRepository> repoProvider) {
    this.repoProvider = repoProvider;
  }

  @Override
  public LogPlaybackHistoryUseCase get() {
    return newInstance(repoProvider.get());
  }

  public static LogPlaybackHistoryUseCase_Factory create(Provider<MusicRepository> repoProvider) {
    return new LogPlaybackHistoryUseCase_Factory(repoProvider);
  }

  public static LogPlaybackHistoryUseCase newInstance(MusicRepository repo) {
    return new LogPlaybackHistoryUseCase(repo);
  }
}
