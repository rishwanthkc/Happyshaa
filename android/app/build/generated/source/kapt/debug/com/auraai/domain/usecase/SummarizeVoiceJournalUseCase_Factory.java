package com.auraai.domain.usecase;

import com.auraai.domain.repository.JournalRepository;
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
public final class SummarizeVoiceJournalUseCase_Factory implements Factory<SummarizeVoiceJournalUseCase> {
  private final Provider<JournalRepository> repoProvider;

  public SummarizeVoiceJournalUseCase_Factory(Provider<JournalRepository> repoProvider) {
    this.repoProvider = repoProvider;
  }

  @Override
  public SummarizeVoiceJournalUseCase get() {
    return newInstance(repoProvider.get());
  }

  public static SummarizeVoiceJournalUseCase_Factory create(
      Provider<JournalRepository> repoProvider) {
    return new SummarizeVoiceJournalUseCase_Factory(repoProvider);
  }

  public static SummarizeVoiceJournalUseCase newInstance(JournalRepository repo) {
    return new SummarizeVoiceJournalUseCase(repo);
  }
}
