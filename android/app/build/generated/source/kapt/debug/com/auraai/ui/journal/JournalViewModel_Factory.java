package com.auraai.ui.journal;

import com.auraai.domain.usecase.CreateJournalEntryUseCase;
import com.auraai.domain.usecase.GenerateWeeklyReportUseCase;
import com.auraai.domain.usecase.GetCurrentUserTokenUseCase;
import com.auraai.domain.usecase.GetJournalsUseCase;
import com.auraai.domain.usecase.GetWeeklyReportsUseCase;
import com.auraai.domain.usecase.SummarizeVoiceJournalUseCase;
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
public final class JournalViewModel_Factory implements Factory<JournalViewModel> {
  private final Provider<CreateJournalEntryUseCase> createJournalEntryUseCaseProvider;

  private final Provider<GetJournalsUseCase> getJournalsUseCaseProvider;

  private final Provider<GenerateWeeklyReportUseCase> generateWeeklyReportUseCaseProvider;

  private final Provider<GetWeeklyReportsUseCase> getWeeklyReportsUseCaseProvider;

  private final Provider<SummarizeVoiceJournalUseCase> summarizeVoiceJournalUseCaseProvider;

  private final Provider<GetCurrentUserTokenUseCase> getTokenUseCaseProvider;

  public JournalViewModel_Factory(
      Provider<CreateJournalEntryUseCase> createJournalEntryUseCaseProvider,
      Provider<GetJournalsUseCase> getJournalsUseCaseProvider,
      Provider<GenerateWeeklyReportUseCase> generateWeeklyReportUseCaseProvider,
      Provider<GetWeeklyReportsUseCase> getWeeklyReportsUseCaseProvider,
      Provider<SummarizeVoiceJournalUseCase> summarizeVoiceJournalUseCaseProvider,
      Provider<GetCurrentUserTokenUseCase> getTokenUseCaseProvider) {
    this.createJournalEntryUseCaseProvider = createJournalEntryUseCaseProvider;
    this.getJournalsUseCaseProvider = getJournalsUseCaseProvider;
    this.generateWeeklyReportUseCaseProvider = generateWeeklyReportUseCaseProvider;
    this.getWeeklyReportsUseCaseProvider = getWeeklyReportsUseCaseProvider;
    this.summarizeVoiceJournalUseCaseProvider = summarizeVoiceJournalUseCaseProvider;
    this.getTokenUseCaseProvider = getTokenUseCaseProvider;
  }

  @Override
  public JournalViewModel get() {
    return newInstance(createJournalEntryUseCaseProvider.get(), getJournalsUseCaseProvider.get(), generateWeeklyReportUseCaseProvider.get(), getWeeklyReportsUseCaseProvider.get(), summarizeVoiceJournalUseCaseProvider.get(), getTokenUseCaseProvider.get());
  }

  public static JournalViewModel_Factory create(
      Provider<CreateJournalEntryUseCase> createJournalEntryUseCaseProvider,
      Provider<GetJournalsUseCase> getJournalsUseCaseProvider,
      Provider<GenerateWeeklyReportUseCase> generateWeeklyReportUseCaseProvider,
      Provider<GetWeeklyReportsUseCase> getWeeklyReportsUseCaseProvider,
      Provider<SummarizeVoiceJournalUseCase> summarizeVoiceJournalUseCaseProvider,
      Provider<GetCurrentUserTokenUseCase> getTokenUseCaseProvider) {
    return new JournalViewModel_Factory(createJournalEntryUseCaseProvider, getJournalsUseCaseProvider, generateWeeklyReportUseCaseProvider, getWeeklyReportsUseCaseProvider, summarizeVoiceJournalUseCaseProvider, getTokenUseCaseProvider);
  }

  public static JournalViewModel newInstance(CreateJournalEntryUseCase createJournalEntryUseCase,
      GetJournalsUseCase getJournalsUseCase,
      GenerateWeeklyReportUseCase generateWeeklyReportUseCase,
      GetWeeklyReportsUseCase getWeeklyReportsUseCase,
      SummarizeVoiceJournalUseCase summarizeVoiceJournalUseCase,
      GetCurrentUserTokenUseCase getTokenUseCase) {
    return new JournalViewModel(createJournalEntryUseCase, getJournalsUseCase, generateWeeklyReportUseCase, getWeeklyReportsUseCase, summarizeVoiceJournalUseCase, getTokenUseCase);
  }
}
