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
public final class CreateJournalEntryUseCase_Factory implements Factory<CreateJournalEntryUseCase> {
  private final Provider<JournalRepository> repoProvider;

  public CreateJournalEntryUseCase_Factory(Provider<JournalRepository> repoProvider) {
    this.repoProvider = repoProvider;
  }

  @Override
  public CreateJournalEntryUseCase get() {
    return newInstance(repoProvider.get());
  }

  public static CreateJournalEntryUseCase_Factory create(Provider<JournalRepository> repoProvider) {
    return new CreateJournalEntryUseCase_Factory(repoProvider);
  }

  public static CreateJournalEntryUseCase newInstance(JournalRepository repo) {
    return new CreateJournalEntryUseCase(repo);
  }
}
