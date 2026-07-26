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
public final class GetWeeklyReportsUseCase_Factory implements Factory<GetWeeklyReportsUseCase> {
  private final Provider<JournalRepository> repoProvider;

  public GetWeeklyReportsUseCase_Factory(Provider<JournalRepository> repoProvider) {
    this.repoProvider = repoProvider;
  }

  @Override
  public GetWeeklyReportsUseCase get() {
    return newInstance(repoProvider.get());
  }

  public static GetWeeklyReportsUseCase_Factory create(Provider<JournalRepository> repoProvider) {
    return new GetWeeklyReportsUseCase_Factory(repoProvider);
  }

  public static GetWeeklyReportsUseCase newInstance(JournalRepository repo) {
    return new GetWeeklyReportsUseCase(repo);
  }
}
