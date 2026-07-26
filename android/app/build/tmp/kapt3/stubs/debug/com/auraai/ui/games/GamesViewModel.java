package com.auraai.ui.games;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000l\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0010\u0002\n\u0002\b\u000f\b\u0007\u0018\u00002\u00020\u0001B/\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u00a2\u0006\u0002\u0010\fJ\u000e\u0010,\u001a\u00020-2\u0006\u0010.\u001a\u00020\u001cJ\u000e\u0010/\u001a\u00020-2\u0006\u00100\u001a\u00020\u0012J\u0006\u00101\u001a\u00020-J\u000e\u00102\u001a\u00020-2\u0006\u00103\u001a\u00020\u001cJ\u0006\u00104\u001a\u00020-J\u0016\u00105\u001a\u00020-2\u0006\u00106\u001a\u00020\u00122\u0006\u00107\u001a\u00020\u001cJ\u000e\u00108\u001a\u00020-2\u0006\u00109\u001a\u00020\u001aJ\u0016\u0010:\u001a\u00020-2\u0006\u00109\u001a\u00020\u001a2\u0006\u0010;\u001a\u00020\u0012R\u001a\u0010\r\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00100\u000f0\u000eX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u0011\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00120\u000eX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0013\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00140\u000f0\u000eX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00160\u000eX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u0017\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00180\u000eX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0019\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001a0\u000f0\u000eX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001c0\u000eX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u001d\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00100\u000f0\u001e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010 R\u0019\u0010!\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00120\u001e\u00a2\u0006\b\n\u0000\u001a\u0004\b\"\u0010 R\u001d\u0010#\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00140\u000f0\u001e\u00a2\u0006\b\n\u0000\u001a\u0004\b$\u0010 R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010%\u001a\b\u0012\u0004\u0012\u00020\u00160\u001e\u00a2\u0006\b\n\u0000\u001a\u0004\b%\u0010 R\u0019\u0010&\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00180\u001e\u00a2\u0006\b\n\u0000\u001a\u0004\b\'\u0010 R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010(\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001a0\u000f0\u001e\u00a2\u0006\b\n\u0000\u001a\u0004\b)\u0010 R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010*\u001a\b\u0012\u0004\u0012\u00020\u001c0\u001e\u00a2\u0006\b\n\u0000\u001a\u0004\b+\u0010 \u00a8\u0006<"}, d2 = {"Lcom/auraai/ui/games/GamesViewModel;", "Landroidx/lifecycle/ViewModel;", "getGamesUseCase", "Lcom/auraai/domain/usecase/GetGamesUseCase;", "submitGameScoreUseCase", "Lcom/auraai/domain/usecase/SubmitGameScoreUseCase;", "getAchievementsUseCase", "Lcom/auraai/domain/usecase/GetAchievementsUseCase;", "getTokenUseCase", "Lcom/auraai/domain/usecase/GetCurrentUserTokenUseCase;", "questDao", "Lcom/auraai/data/local/db/QuestDao;", "(Lcom/auraai/domain/usecase/GetGamesUseCase;Lcom/auraai/domain/usecase/SubmitGameScoreUseCase;Lcom/auraai/domain/usecase/GetAchievementsUseCase;Lcom/auraai/domain/usecase/GetCurrentUserTokenUseCase;Lcom/auraai/data/local/db/QuestDao;)V", "_achievements", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "Lcom/auraai/domain/model/Achievement;", "_errorMessage", "", "_games", "Lcom/auraai/domain/model/Game;", "_isLoading", "", "_progressEvent", "Lcom/auraai/domain/model/GameProgress;", "_quests", "Lcom/auraai/data/local/db/QuestEntity;", "_userCoins", "", "achievements", "Lkotlinx/coroutines/flow/StateFlow;", "getAchievements", "()Lkotlinx/coroutines/flow/StateFlow;", "errorMessage", "getErrorMessage", "games", "getGames", "isLoading", "progressEvent", "getProgressEvent", "quests", "getQuests", "userCoins", "getUserCoins", "addCoins", "", "amount", "addQuest", "text", "clearProgressEvent", "deleteQuest", "id", "loadGameCenter", "submitScore", "gameId", "score", "toggleQuestCompleted", "quest", "updateQuestText", "newText", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class GamesViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.auraai.domain.usecase.GetGamesUseCase getGamesUseCase = null;
    @org.jetbrains.annotations.NotNull()
    private final com.auraai.domain.usecase.SubmitGameScoreUseCase submitGameScoreUseCase = null;
    @org.jetbrains.annotations.NotNull()
    private final com.auraai.domain.usecase.GetAchievementsUseCase getAchievementsUseCase = null;
    @org.jetbrains.annotations.NotNull()
    private final com.auraai.domain.usecase.GetCurrentUserTokenUseCase getTokenUseCase = null;
    @org.jetbrains.annotations.NotNull()
    private final com.auraai.data.local.db.QuestDao questDao = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.List<com.auraai.domain.model.Game>> _games = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.auraai.domain.model.Game>> games = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.List<com.auraai.domain.model.Achievement>> _achievements = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.auraai.domain.model.Achievement>> achievements = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.auraai.domain.model.GameProgress> _progressEvent = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.auraai.domain.model.GameProgress> progressEvent = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Boolean> _isLoading = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> isLoading = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> _errorMessage = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.String> errorMessage = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Integer> _userCoins = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Integer> userCoins = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.List<com.auraai.data.local.db.QuestEntity>> _quests = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.auraai.data.local.db.QuestEntity>> quests = null;
    
    @javax.inject.Inject()
    public GamesViewModel(@org.jetbrains.annotations.NotNull()
    com.auraai.domain.usecase.GetGamesUseCase getGamesUseCase, @org.jetbrains.annotations.NotNull()
    com.auraai.domain.usecase.SubmitGameScoreUseCase submitGameScoreUseCase, @org.jetbrains.annotations.NotNull()
    com.auraai.domain.usecase.GetAchievementsUseCase getAchievementsUseCase, @org.jetbrains.annotations.NotNull()
    com.auraai.domain.usecase.GetCurrentUserTokenUseCase getTokenUseCase, @org.jetbrains.annotations.NotNull()
    com.auraai.data.local.db.QuestDao questDao) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.auraai.domain.model.Game>> getGames() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.auraai.domain.model.Achievement>> getAchievements() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.auraai.domain.model.GameProgress> getProgressEvent() {
        return null;
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
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Integer> getUserCoins() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.auraai.data.local.db.QuestEntity>> getQuests() {
        return null;
    }
    
    public final void loadGameCenter() {
    }
    
    public final void submitScore(@org.jetbrains.annotations.NotNull()
    java.lang.String gameId, int score) {
    }
    
    public final void clearProgressEvent() {
    }
    
    public final void addCoins(int amount) {
    }
    
    public final void addQuest(@org.jetbrains.annotations.NotNull()
    java.lang.String text) {
    }
    
    public final void deleteQuest(int id) {
    }
    
    public final void updateQuestText(@org.jetbrains.annotations.NotNull()
    com.auraai.data.local.db.QuestEntity quest, @org.jetbrains.annotations.NotNull()
    java.lang.String newText) {
    }
    
    public final void toggleQuestCompleted(@org.jetbrains.annotations.NotNull()
    com.auraai.data.local.db.QuestEntity quest) {
    }
}