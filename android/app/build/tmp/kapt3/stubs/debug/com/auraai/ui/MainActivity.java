package com.auraai.ui;

@dagger.hilt.android.AndroidEntryPoint()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000x\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0012\u0010A\u001a\u00020B2\b\u0010C\u001a\u0004\u0018\u00010DH\u0014R\u001b\u0010\u0003\u001a\u00020\u00048BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0007\u0010\b\u001a\u0004\b\u0005\u0010\u0006R\u001b\u0010\t\u001a\u00020\n8BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\r\u0010\b\u001a\u0004\b\u000b\u0010\fR\u001b\u0010\u000e\u001a\u00020\u000f8BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0012\u0010\b\u001a\u0004\b\u0010\u0010\u0011R\u001b\u0010\u0013\u001a\u00020\u00148BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0017\u0010\b\u001a\u0004\b\u0015\u0010\u0016R\u001b\u0010\u0018\u001a\u00020\u00198BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u001c\u0010\b\u001a\u0004\b\u001a\u0010\u001bR\u001b\u0010\u001d\u001a\u00020\u001e8BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b!\u0010\b\u001a\u0004\b\u001f\u0010 R\u001b\u0010\"\u001a\u00020#8BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b&\u0010\b\u001a\u0004\b$\u0010%R\u001b\u0010\'\u001a\u00020(8BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b+\u0010\b\u001a\u0004\b)\u0010*R\u001b\u0010,\u001a\u00020-8BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b0\u0010\b\u001a\u0004\b.\u0010/R\u001e\u00101\u001a\u0002028\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b3\u00104\"\u0004\b5\u00106R\u001b\u00107\u001a\u0002088BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b;\u0010\b\u001a\u0004\b9\u0010:R\u001b\u0010<\u001a\u00020=8BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b@\u0010\b\u001a\u0004\b>\u0010?\u00a8\u0006E"}, d2 = {"Lcom/auraai/ui/MainActivity;", "Landroidx/activity/ComponentActivity;", "()V", "authViewModel", "Lcom/auraai/ui/auth/AuthViewModel;", "getAuthViewModel", "()Lcom/auraai/ui/auth/AuthViewModel;", "authViewModel$delegate", "Lkotlin/Lazy;", "chatViewModel", "Lcom/auraai/ui/chat/ChatViewModel;", "getChatViewModel", "()Lcom/auraai/ui/chat/ChatViewModel;", "chatViewModel$delegate", "contactsViewModel", "Lcom/auraai/ui/contacts/ContactsViewModel;", "getContactsViewModel", "()Lcom/auraai/ui/contacts/ContactsViewModel;", "contactsViewModel$delegate", "gamesViewModel", "Lcom/auraai/ui/games/GamesViewModel;", "getGamesViewModel", "()Lcom/auraai/ui/games/GamesViewModel;", "gamesViewModel$delegate", "journalViewModel", "Lcom/auraai/ui/journal/JournalViewModel;", "getJournalViewModel", "()Lcom/auraai/ui/journal/JournalViewModel;", "journalViewModel$delegate", "meditationViewModel", "Lcom/auraai/ui/meditation/MeditationViewModel;", "getMeditationViewModel", "()Lcom/auraai/ui/meditation/MeditationViewModel;", "meditationViewModel$delegate", "moodViewModel", "Lcom/auraai/ui/mood/MoodViewModel;", "getMoodViewModel", "()Lcom/auraai/ui/mood/MoodViewModel;", "moodViewModel$delegate", "musicViewModel", "Lcom/auraai/ui/music/MusicViewModel;", "getMusicViewModel", "()Lcom/auraai/ui/music/MusicViewModel;", "musicViewModel$delegate", "notificationsViewModel", "Lcom/auraai/ui/notifications/NotificationsViewModel;", "getNotificationsViewModel", "()Lcom/auraai/ui/notifications/NotificationsViewModel;", "notificationsViewModel$delegate", "preferenceManager", "Lcom/auraai/data/local/preferences/PreferenceManager;", "getPreferenceManager", "()Lcom/auraai/data/local/preferences/PreferenceManager;", "setPreferenceManager", "(Lcom/auraai/data/local/preferences/PreferenceManager;)V", "recommendationViewModel", "Lcom/auraai/ui/recommendation/RecommendationViewModel;", "getRecommendationViewModel", "()Lcom/auraai/ui/recommendation/RecommendationViewModel;", "recommendationViewModel$delegate", "storyViewModel", "Lcom/auraai/ui/story/StoryGeneratorViewModel;", "getStoryViewModel", "()Lcom/auraai/ui/story/StoryGeneratorViewModel;", "storyViewModel$delegate", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "app_debug"})
public final class MainActivity extends androidx.activity.ComponentActivity {
    @javax.inject.Inject()
    public com.auraai.data.local.preferences.PreferenceManager preferenceManager;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy authViewModel$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy chatViewModel$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy moodViewModel$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy musicViewModel$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy contactsViewModel$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy gamesViewModel$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy journalViewModel$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy recommendationViewModel$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy notificationsViewModel$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy storyViewModel$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy meditationViewModel$delegate = null;
    
    public MainActivity() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.auraai.data.local.preferences.PreferenceManager getPreferenceManager() {
        return null;
    }
    
    public final void setPreferenceManager(@org.jetbrains.annotations.NotNull()
    com.auraai.data.local.preferences.PreferenceManager p0) {
    }
    
    private final com.auraai.ui.auth.AuthViewModel getAuthViewModel() {
        return null;
    }
    
    private final com.auraai.ui.chat.ChatViewModel getChatViewModel() {
        return null;
    }
    
    private final com.auraai.ui.mood.MoodViewModel getMoodViewModel() {
        return null;
    }
    
    private final com.auraai.ui.music.MusicViewModel getMusicViewModel() {
        return null;
    }
    
    private final com.auraai.ui.contacts.ContactsViewModel getContactsViewModel() {
        return null;
    }
    
    private final com.auraai.ui.games.GamesViewModel getGamesViewModel() {
        return null;
    }
    
    private final com.auraai.ui.journal.JournalViewModel getJournalViewModel() {
        return null;
    }
    
    private final com.auraai.ui.recommendation.RecommendationViewModel getRecommendationViewModel() {
        return null;
    }
    
    private final com.auraai.ui.notifications.NotificationsViewModel getNotificationsViewModel() {
        return null;
    }
    
    private final com.auraai.ui.story.StoryGeneratorViewModel getStoryViewModel() {
        return null;
    }
    
    private final com.auraai.ui.meditation.MeditationViewModel getMeditationViewModel() {
        return null;
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
}