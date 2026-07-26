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
public final class GetJournalsUseCase_Factory implements Factory<GetJournalsUseCase> {
  private final Provider<JournalRepository> repoProvider;

  public GetJournalsUseCase_Factory(Provider<JournalRepository> repoProvider) {
    this.repoProvider = repoProvider;
  }

  @Override
  public GetJournalsUseCase get() {
    return newInstance(repoProvider.get());
  }

  public static GetJournalsUseCase_Factory create(Provider<JournalRepository> repoProvider) {
    return new GetJournalsUseCase_Factory(repoProvider);
  }

  public static GetJournalsUseCase newInstance(JournalRepository repo) {
    return new GetJournalsUseCase(repo);
  }
}
