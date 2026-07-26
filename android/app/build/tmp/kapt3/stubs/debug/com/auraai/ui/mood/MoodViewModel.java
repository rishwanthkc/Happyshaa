package com.auraai.ui.mood;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B\'\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u00a2\u0006\u0002\u0010\nJ\u0016\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\r2\u0006\u0010\u001c\u001a\u00020\rJ\u0006\u0010\u001d\u001a\u00020\u001aJ\u001a\u0010\u001e\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00110\u001f0\u00132\u0006\u0010 \u001a\u00020\rR\u0016\u0010\u000b\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\r0\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000f0\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u0010\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00110\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0019\u0010\u0012\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\r0\u0013\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0013\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0015R\u0019\u0010\u0017\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00110\u0013\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0015\u00a8\u0006!"}, d2 = {"Lcom/auraai/ui/mood/MoodViewModel;", "Landroidx/lifecycle/ViewModel;", "analyzeMoodUseCase", "Lcom/auraai/domain/usecase/AnalyzeMoodUseCase;", "getMoodHistoryUseCase", "Lcom/auraai/domain/usecase/GetMoodHistoryUseCase;", "cacheMoodLocallyUseCase", "Lcom/auraai/domain/usecase/CacheMoodLocallyUseCase;", "getTokenUseCase", "Lcom/auraai/domain/usecase/GetCurrentUserTokenUseCase;", "(Lcom/auraai/domain/usecase/AnalyzeMoodUseCase;Lcom/auraai/domain/usecase/GetMoodHistoryUseCase;Lcom/auraai/domain/usecase/CacheMoodLocallyUseCase;Lcom/auraai/domain/usecase/GetCurrentUserTokenUseCase;)V", "_errorMessage", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "_isLoading", "", "_lastAnalysisResult", "Lcom/auraai/domain/model/MoodLog;", "errorMessage", "Lkotlinx/coroutines/flow/StateFlow;", "getErrorMessage", "()Lkotlinx/coroutines/flow/StateFlow;", "isLoading", "lastAnalysisResult", "getLastAnalysisResult", "analyzeAndLogMood", "", "text", "fallbackUid", "clearState", "getMoodHistory", "", "uid", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class MoodViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.auraai.domain.usecase.AnalyzeMoodUseCase analyzeMoodUseCase = null;
    @org.jetbrains.annotations.NotNull()
    private final com.auraai.domain.usecase.GetMoodHistoryUseCase getMoodHistoryUseCase = null;
    @org.jetbrains.annotations.NotNull()
    private final com.auraai.domain.usecase.CacheMoodLocallyUseCase cacheMoodLocallyUseCase = null;
    @org.jetbrains.annotations.NotNull()
    private final com.auraai.domain.usecase.GetCurrentUserTokenUseCase getTokenUseCase = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Boolean> _isLoading = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isLoading = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> _errorMessage = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.String> errorMessage = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.auraai.domain.model.MoodLog> _lastAnalysisResult = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.auraai.domain.model.MoodLog> lastAnalysisResult = null;
    
    @javax.inject.Inject()
    public MoodViewModel(@org.jetbrains.annotations.NotNull()
    com.auraai.domain.usecase.AnalyzeMoodUseCase analyzeMoodUseCase, @org.jetbrains.annotations.NotNull()
    com.auraai.domain.usecase.GetMoodHistoryUseCase getMoodHistoryUseCase, @org.jetbrains.annotations.NotNull()
    com.auraai.domain.usecase.CacheMoodLocallyUseCase cacheMoodLocallyUseCase, @org.jetbrains.annotations.NotNull()
    com.auraai.domain.usecase.GetCurrentUserTokenUseCase getTokenUseCase) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isLoading() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.String> getErrorMessage() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.auraai.domain.model.MoodLog> getLastAnalysisResult() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.auraai.domain.model.MoodLog>> getMoodHistory(@org.jetbrains.annotations.NotNull()
    java.lang.String uid) {
        return null;
    }
    
    public final void analyzeAndLogMood(@org.jetbrains.annotations.NotNull()
    java.lang.String text, @org.jetbrains.annotations.NotNull()
    java.lang.String fallbackUid) {
    }
    
    public final void clearState() {
    }
}