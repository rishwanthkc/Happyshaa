package com.auraai.ui.music;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\f\u0010\u0015\u001a\u00060\u0016j\u0002`\u0017H\u0007J\b\u0010\u0018\u001a\u00020\u0016H\u0007J\b\u0010\u0019\u001a\u00020\u0016H\u0007J\f\u0010\u001a\u001a\u00060\u0016j\u0002`\u0017H\u0007R\u0012\u0010\u0003\u001a\u00020\u00048\u0002@\u0002X\u0083.\u00a2\u0006\u0002\n\u0000R\u0012\u0010\u0005\u001a\u00020\u00068\u0002@\u0002X\u0083.\u00a2\u0006\u0002\n\u0000R\u0012\u0010\u0007\u001a\u00020\b8\u0002@\u0002X\u0083.\u00a2\u0006\u0002\n\u0000R\u0012\u0010\t\u001a\u00020\n8\u0002@\u0002X\u0083.\u00a2\u0006\u0002\n\u0000R\u0012\u0010\u000b\u001a\u00020\f8\u0002@\u0002X\u0083.\u00a2\u0006\u0002\n\u0000R\u0012\u0010\r\u001a\u00020\u000e8\u0002@\u0002X\u0083.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0012\u0010\u0011\u001a\u00020\u00128\u0002@\u0002X\u0083.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001b"}, d2 = {"Lcom/auraai/ui/music/MusicViewModelTest;", "", "()V", "context", "Landroid/content/Context;", "getFavoriteSongsUseCase", "Lcom/auraai/domain/usecase/GetFavoriteSongsUseCase;", "getMusicRecommendationsUseCase", "Lcom/auraai/domain/usecase/GetMusicRecommendationsUseCase;", "getSongsUseCase", "Lcom/auraai/domain/usecase/GetSongsUseCase;", "getTokenUseCase", "Lcom/auraai/domain/usecase/GetCurrentUserTokenUseCase;", "logPlaybackHistoryUseCase", "Lcom/auraai/domain/usecase/LogPlaybackHistoryUseCase;", "testDispatcher", "Lkotlinx/coroutines/test/TestDispatcher;", "toggleFavoriteSongUseCase", "Lcom/auraai/domain/usecase/ToggleFavoriteSongUseCase;", "viewModel", "Lcom/auraai/ui/music/MusicViewModel;", "loadAllMusic_success_populatesSongsAndFavorites", "", "Lkotlinx/coroutines/test/TestResult;", "setUp", "tearDown", "toggleFavorite_success_callsUseCase", "app_debugUnitTest"})
@kotlin.OptIn(markerClass = {kotlinx.coroutines.ExperimentalCoroutinesApi.class})
public final class MusicViewModelTest {
    @org.mockito.Mock()
    private com.auraai.domain.usecase.GetSongsUseCase getSongsUseCase;
    @org.mockito.Mock()
    private com.auraai.domain.usecase.ToggleFavoriteSongUseCase toggleFavoriteSongUseCase;
    @org.mockito.Mock()
    private com.auraai.domain.usecase.GetFavoriteSongsUseCase getFavoriteSongsUseCase;
    @org.mockito.Mock()
    private com.auraai.domain.usecase.LogPlaybackHistoryUseCase logPlaybackHistoryUseCase;
    @org.mockito.Mock()
    private com.auraai.domain.usecase.GetMusicRecommendationsUseCase getMusicRecommendationsUseCase;
    @org.mockito.Mock()
    private com.auraai.domain.usecase.GetCurrentUserTokenUseCase getTokenUseCase;
    @org.mockito.Mock()
    private android.content.Context context;
    private com.auraai.ui.music.MusicViewModel viewModel;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.test.TestDispatcher testDispatcher = null;
    
    public MusicViewModelTest() {
        super();
    }
    
    @org.junit.Before()
    public final void setUp() {
    }
    
    @org.junit.After()
    public final void tearDown() {
    }
    
    @org.junit.Test()
    public final void loadAllMusic_success_populatesSongsAndFavorites() {
    }
    
    @org.junit.Test()
    public final void toggleFavorite_success_callsUseCase() {
    }
}