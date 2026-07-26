package com.auraai.ui.journal;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\f\u0010\u0013\u001a\u00060\u0014j\u0002`\u0015H\u0007J\b\u0010\u0016\u001a\u00020\u0014H\u0007J\f\u0010\u0017\u001a\u00060\u0014j\u0002`\u0015H\u0007J\b\u0010\u0018\u001a\u00020\u0014H\u0007R\u0012\u0010\u0003\u001a\u00020\u00048\u0002@\u0002X\u0083.\u00a2\u0006\u0002\n\u0000R\u0012\u0010\u0005\u001a\u00020\u00068\u0002@\u0002X\u0083.\u00a2\u0006\u0002\n\u0000R\u0012\u0010\u0007\u001a\u00020\b8\u0002@\u0002X\u0083.\u00a2\u0006\u0002\n\u0000R\u0012\u0010\t\u001a\u00020\n8\u0002@\u0002X\u0083.\u00a2\u0006\u0002\n\u0000R\u0012\u0010\u000b\u001a\u00020\f8\u0002@\u0002X\u0083.\u00a2\u0006\u0002\n\u0000R\u0012\u0010\r\u001a\u00020\u000e8\u0002@\u0002X\u0083.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0019"}, d2 = {"Lcom/auraai/ui/journal/JournalViewModelTest;", "", "()V", "createJournalEntryUseCase", "Lcom/auraai/domain/usecase/CreateJournalEntryUseCase;", "generateWeeklyReportUseCase", "Lcom/auraai/domain/usecase/GenerateWeeklyReportUseCase;", "getJournalsUseCase", "Lcom/auraai/domain/usecase/GetJournalsUseCase;", "getTokenUseCase", "Lcom/auraai/domain/usecase/GetCurrentUserTokenUseCase;", "getWeeklyReportsUseCase", "Lcom/auraai/domain/usecase/GetWeeklyReportsUseCase;", "summarizeVoiceJournalUseCase", "Lcom/auraai/domain/usecase/SummarizeVoiceJournalUseCase;", "testDispatcher", "Lkotlinx/coroutines/test/TestDispatcher;", "viewModel", "Lcom/auraai/ui/journal/JournalViewModel;", "loadJournalData_success_updatesJournalLists", "", "Lkotlinx/coroutines/test/TestResult;", "setUp", "submitJournal_success_emitsRecentAnalysis", "tearDown", "app_debugUnitTest"})
@kotlin.OptIn(markerClass = {kotlinx.coroutines.ExperimentalCoroutinesApi.class})
public final class JournalViewModelTest {
    @org.mockito.Mock()
    private com.auraai.domain.usecase.CreateJournalEntryUseCase createJournalEntryUseCase;
    @org.mockito.Mock()
    private com.auraai.domain.usecase.GetJournalsUseCase getJournalsUseCase;
    @org.mockito.Mock()
    private com.auraai.domain.usecase.GenerateWeeklyReportUseCase generateWeeklyReportUseCase;
    @org.mockito.Mock()
    private com.auraai.domain.usecase.GetWeeklyReportsUseCase getWeeklyReportsUseCase;
    @org.mockito.Mock()
    private com.auraai.domain.usecase.GetCurrentUserTokenUseCase getTokenUseCase;
    @org.mockito.Mock()
    private com.auraai.domain.usecase.SummarizeVoiceJournalUseCase summarizeVoiceJournalUseCase;
    private com.auraai.ui.journal.JournalViewModel viewModel;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.test.TestDispatcher testDispatcher = null;
    
    public JournalViewModelTest() {
        super();
    }
    
    @org.junit.Before()
    public final void setUp() {
    }
    
    @org.junit.After()
    public final void tearDown() {
    }
    
    @org.junit.Test()
    public final void loadJournalData_success_updatesJournalLists() {
    }
    
    @org.junit.Test()
    public final void submitJournal_success_emitsRecentAnalysis() {
    }
}