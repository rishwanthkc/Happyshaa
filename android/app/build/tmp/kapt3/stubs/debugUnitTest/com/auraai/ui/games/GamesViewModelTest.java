package com.auraai.ui.games;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\f\u0010\u0011\u001a\u00060\u0012j\u0002`\u0013H\u0007J\b\u0010\u0014\u001a\u00020\u0012H\u0007J\f\u0010\u0015\u001a\u00060\u0012j\u0002`\u0013H\u0007J\b\u0010\u0016\u001a\u00020\u0012H\u0007R\u0012\u0010\u0003\u001a\u00020\u00048\u0002@\u0002X\u0083.\u00a2\u0006\u0002\n\u0000R\u0012\u0010\u0005\u001a\u00020\u00068\u0002@\u0002X\u0083.\u00a2\u0006\u0002\n\u0000R\u0012\u0010\u0007\u001a\u00020\b8\u0002@\u0002X\u0083.\u00a2\u0006\u0002\n\u0000R\u0012\u0010\t\u001a\u00020\n8\u0002@\u0002X\u0083.\u00a2\u0006\u0002\n\u0000R\u0012\u0010\u000b\u001a\u00020\f8\u0002@\u0002X\u0083.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0017"}, d2 = {"Lcom/auraai/ui/games/GamesViewModelTest;", "", "()V", "getAchievementsUseCase", "Lcom/auraai/domain/usecase/GetAchievementsUseCase;", "getGamesUseCase", "Lcom/auraai/domain/usecase/GetGamesUseCase;", "getTokenUseCase", "Lcom/auraai/domain/usecase/GetCurrentUserTokenUseCase;", "questDao", "Lcom/auraai/data/local/db/QuestDao;", "submitGameScoreUseCase", "Lcom/auraai/domain/usecase/SubmitGameScoreUseCase;", "testDispatcher", "Lkotlinx/coroutines/test/TestDispatcher;", "viewModel", "Lcom/auraai/ui/games/GamesViewModel;", "loadGameCenter_success_updatesGamesState", "", "Lkotlinx/coroutines/test/TestResult;", "setUp", "submitScore_success_emitsProgressEvent", "tearDown", "app_debugUnitTest"})
@kotlin.OptIn(markerClass = {kotlinx.coroutines.ExperimentalCoroutinesApi.class})
public final class GamesViewModelTest {
    @org.mockito.Mock()
    private com.auraai.domain.usecase.GetGamesUseCase getGamesUseCase;
    @org.mockito.Mock()
    private com.auraai.domain.usecase.SubmitGameScoreUseCase submitGameScoreUseCase;
    @org.mockito.Mock()
    private com.auraai.domain.usecase.GetAchievementsUseCase getAchievementsUseCase;
    @org.mockito.Mock()
    private com.auraai.domain.usecase.GetCurrentUserTokenUseCase getTokenUseCase;
    @org.mockito.Mock()
    private com.auraai.data.local.db.QuestDao questDao;
    private com.auraai.ui.games.GamesViewModel viewModel;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.test.TestDispatcher testDispatcher = null;
    
    public GamesViewModelTest() {
        super();
    }
    
    @org.junit.Before()
    public final void setUp() {
    }
    
    @org.junit.After()
    public final void tearDown() {
    }
    
    @org.junit.Test()
    public final void loadGameCenter_success_updatesGamesState() {
    }
    
    @org.junit.Test()
    public final void submitScore_success_emitsProgressEvent() {
    }
}