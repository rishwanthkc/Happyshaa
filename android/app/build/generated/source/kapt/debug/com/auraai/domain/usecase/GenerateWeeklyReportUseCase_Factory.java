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
public final class GenerateWeeklyReportUseCase_Factory implements Factory<GenerateWeeklyReportUseCase> {
  private final Provider<JournalRepository> repoProvider;

  public GenerateWeeklyReportUseCase_Factory(Provider<JournalRepository> repoProvider) {
    this.repoProvider = repoProvider;
  }

  @Override
  public GenerateWeeklyReportUseCase get() {
    return newInstance(repoProvider.get());
  }

  public static GenerateWeeklyReportUseCase_Factory create(
      Provider<JournalRepository> repoProvider) {
    return new GenerateWeeklyReportUseCase_Factory(repoProvider);
  }

  public static GenerateWeeklyReportUseCase newInstance(JournalRepository repo) {
    return new GenerateWeeklyReportUseCase(repo);
  }
}
