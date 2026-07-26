package com.auraai.ui.mood;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\f\u0010\u000f\u001a\u00060\u0010j\u0002`\u0011H\u0007J\b\u0010\u0012\u001a\u00020\u0010H\u0007J\b\u0010\u0013\u001a\u00020\u0010H\u0007R\u0012\u0010\u0003\u001a\u00020\u00048\u0002@\u0002X\u0083.\u00a2\u0006\u0002\n\u0000R\u0012\u0010\u0005\u001a\u00020\u00068\u0002@\u0002X\u0083.\u00a2\u0006\u0002\n\u0000R\u0012\u0010\u0007\u001a\u00020\b8\u0002@\u0002X\u0083.\u00a2\u0006\u0002\n\u0000R\u0012\u0010\t\u001a\u00020\n8\u0002@\u0002X\u0083.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0014"}, d2 = {"Lcom/auraai/ui/mood/MoodViewModelTest;", "", "()V", "analyzeMoodUseCase", "Lcom/auraai/domain/usecase/AnalyzeMoodUseCase;", "cacheMoodLocallyUseCase", "Lcom/auraai/domain/usecase/CacheMoodLocallyUseCase;", "getMoodHistoryUseCase", "Lcom/auraai/domain/usecase/GetMoodHistoryUseCase;", "getTokenUseCase", "Lcom/auraai/domain/usecase/GetCurrentUserTokenUseCase;", "testDispatcher", "Lkotlinx/coroutines/test/TestDispatcher;", "viewModel", "Lcom/auraai/ui/mood/MoodViewModel;", "analyzeAndLogMood_success_updatesLastAnalysisResult", "", "Lkotlinx/coroutines/test/TestResult;", "setUp", "tearDown", "app_debugUnitTest"})
@kotlin.OptIn(markerClass = {kotlinx.coroutines.ExperimentalCoroutinesApi.class})
public final class MoodViewModelTest {
    @org.mockito.Mock()
    private com.auraai.domain.usecase.AnalyzeMoodUseCase analyzeMoodUseCase;
    @org.mockito.Mock()
    private com.auraai.domain.usecase.GetMoodHistoryUseCase getMoodHistoryUseCase;
    @org.mockito.Mock()
    private com.auraai.domain.usecase.CacheMoodLocallyUseCase cacheMoodLocallyUseCase;
    @org.mockito.Mock()
    private com.auraai.domain.usecase.GetCurrentUserTokenUseCase getTokenUseCase;
    private com.auraai.ui.mood.MoodViewModel viewModel;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.test.TestDispatcher testDispatcher = null;
    
    public MoodViewModelTest() {
        super();
    }
    
    @org.junit.Before()
    public final void setUp() {
    }
    
    @org.junit.After()
    public final void tearDown() {
    }
    
    @org.junit.Test()
    public final void analyzeAndLogMood_success_updatesLastAnalysisResult() {
    }
}