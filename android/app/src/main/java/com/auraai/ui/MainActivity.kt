package com.auraai.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.auraai.domain.model.AuthState
import com.auraai.domain.model.User
import com.auraai.ui.auth.AuthScreen
import com.auraai.ui.auth.AuthViewModel
import com.auraai.ui.chat.ChatScreen
import com.auraai.ui.chat.ChatViewModel
import com.auraai.ui.mood.MoodLogScreen
import com.auraai.ui.mood.MoodViewModel
import com.auraai.ui.music.MusicPlayerScreen
import com.auraai.ui.music.MusicViewModel
import com.auraai.ui.contacts.SupportContactsScreen
import com.auraai.ui.contacts.ContactsViewModel
import com.auraai.ui.games.GameCenterScreen
import com.auraai.ui.games.GamesViewModel
import com.auraai.ui.games.MemoryMatchScreen
import com.auraai.ui.games.ColoringScreen
import com.auraai.ui.journal.JournalScreen
import com.auraai.ui.journal.JournalViewModel
import com.auraai.ui.recommendation.RecommendationsScreen
import com.auraai.ui.recommendation.RecommendationViewModel
import com.auraai.ui.notifications.NotificationsScreen
import com.auraai.ui.notifications.NotificationsViewModel
import com.auraai.ui.navigation.NavRoute
import com.auraai.ui.story.StoryGeneratorScreen
import com.auraai.ui.story.StoryGeneratorViewModel
import com.auraai.ui.meditation.MeditationTimerScreen
import com.auraai.ui.meditation.MeditationViewModel
import com.auraai.ui.profile.ProfileScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var preferenceManager: com.auraai.data.local.preferences.PreferenceManager

    private val authViewModel: AuthViewModel by viewModels()
    private val chatViewModel: ChatViewModel by viewModels()
    private val moodViewModel: MoodViewModel by viewModels()
    private val musicViewModel: MusicViewModel by viewModels()
    private val contactsViewModel: ContactsViewModel by viewModels()
    private val gamesViewModel: GamesViewModel by viewModels()
    private val journalViewModel: JournalViewModel by viewModels()
    private val recommendationViewModel: RecommendationViewModel by viewModels()
    private val notificationsViewModel: NotificationsViewModel by viewModels()
    private val storyViewModel: StoryGeneratorViewModel by viewModels()
    private val meditationViewModel: MeditationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Retrieve and register FCM token
        try {
            com.google.firebase.messaging.FirebaseMessaging.getInstance().token.addOnCompleteListener(
                com.google.android.gms.tasks.OnCompleteListener { task ->
                    if (task.isSuccessful) {
                        task.result?.let { token ->
                            notificationsViewModel.registerToken(token)
                        }
                    }
                }
            )
        } catch (e: Exception) {
            // Safe fallback if Firebase is not fully configured
        }

        setContent {
            val themeMode by preferenceManager.themeMode.collectAsState(initial = "SYSTEM")
            val isDark = when (themeMode) {
                "DARK" -> true
                "LIGHT" -> false
                else -> androidx.compose.foundation.isSystemInDarkTheme()
            }
            com.auraai.ui.theme.AuraTheme(darkTheme = isDark) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val authState by authViewModel.authState.collectAsState()
                    var currentRoute by remember { mutableStateOf<NavRoute>(NavRoute.Dashboard) }

                    Crossfade(targetState = authState, label = "MainScreenStateTransition") { state ->
                        when (state) {
                            is AuthState.Loading, AuthState.Idle -> {
                                LoadingStateScreen()
                            }
                            is AuthState.Authenticated -> {
                                val userCoins by gamesViewModel.userCoins.collectAsState()
                                when (currentRoute) {
                                    NavRoute.Dashboard -> {
                                        DashboardScreen(
                                            user = state.user,
                                            gamesViewModel = gamesViewModel,
                                            onNavigate = { currentRoute = it },
                                            onSignOutClick = { authViewModel.signOut() }
                                        )
                                    }
                                    NavRoute.Chat -> ChatScreen(
                                        viewModel = chatViewModel,
                                        uid = state.user.uid,
                                        currentMood = "Calm",
                                        onBackClick = { currentRoute = NavRoute.Dashboard }
                                    )
                                    NavRoute.MoodLog -> MoodLogScreen(
                                        viewModel = moodViewModel,
                                        uid = state.user.uid,
                                        onBackClick = { currentRoute = NavRoute.Dashboard }
                                    )
                                    NavRoute.MusicPlayer -> MusicPlayerScreen(musicViewModel, onBackClick = { currentRoute = NavRoute.Dashboard })
                                    NavRoute.SupportContacts -> SupportContactsScreen(contactsViewModel, onBackClick = { currentRoute = NavRoute.Dashboard })
                                    NavRoute.GameCenter -> GameCenterScreen(gamesViewModel, onBackClick = { currentRoute = NavRoute.Dashboard }, onNavigateToGame = { currentRoute = it })
                                    NavRoute.Journal -> JournalScreen(journalViewModel, onBackClick = { currentRoute = NavRoute.Dashboard })
                                    NavRoute.Recommendations -> RecommendationsScreen(recommendationViewModel, onBackClick = { currentRoute = NavRoute.Dashboard }, onNavigateToRoute = { routeStr ->
                                        currentRoute = when (routeStr) {
                                            "music_player/nature_rain" -> NavRoute.MusicPlayer
                                            "music_player/lofi_focus" -> NavRoute.MusicPlayer
                                            "game_center/bubblepop" -> NavRoute.GameCenter
                                            "game_center/wordpuzzle" -> NavRoute.GameCenter
                                            "journal/new" -> NavRoute.Journal
                                            else -> NavRoute.Dashboard
                                        }
                                    })
                                    NavRoute.Notifications -> NotificationsScreen(notificationsViewModel, onBackClick = { currentRoute = NavRoute.Dashboard })
                                    NavRoute.StoryGenerator -> StoryGeneratorScreen(
                                        viewModel = storyViewModel,
                                        uid = state.user.uid,
                                        onBackClick = { currentRoute = NavRoute.Dashboard }
                                    )
                                    NavRoute.Meditation -> MeditationTimerScreen(
                                        viewModel = meditationViewModel,
                                        onBackClick = { currentRoute = NavRoute.Dashboard }
                                    )
                                    NavRoute.MemoryMatch -> MemoryMatchScreen(
                                        viewModel = gamesViewModel,
                                        onBackClick = { currentRoute = NavRoute.GameCenter }
                                    )
                                    NavRoute.Coloring -> ColoringScreen(
                                        viewModel = gamesViewModel,
                                        onBackClick = { currentRoute = NavRoute.GameCenter }
                                    )
                                    NavRoute.Profile -> {
                                        val coroutineScope = rememberCoroutineScope()
                                        ProfileScreen(
                                            user = state.user,
                                            coins = userCoins,
                                            themeMode = themeMode,
                                            onThemeChange = { mode ->
                                                coroutineScope.launch {
                                                    preferenceManager.setThemeMode(mode)
                                                }
                                            },
                                            onBackClick = { currentRoute = NavRoute.Dashboard }
                                        )
                                    }
                                    else -> {
                                        currentRoute = NavRoute.Dashboard
                                    }
                                }
                            }
                            is AuthState.Unauthenticated, is AuthState.Error -> {
                                AuthScreen(
                                    viewModel = authViewModel,
                                    onAuthSuccess = { currentRoute = NavRoute.Dashboard }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun LoadingStateScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0F0C20)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator(
                color = Color(0xFF00B4D8),
                modifier = Modifier.size(50.dp),
                strokeWidth = 3.dp
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Aligning Aura...",
                color = Color.White.copy(alpha = 0.6f),
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

data class DashboardItem(
    val title: String,
    val description: String,
    val targetRoute: NavRoute,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val tint: Color
)

@Composable
fun DashboardScreen(
    user: User,
    gamesViewModel: GamesViewModel,
    onNavigate: (NavRoute) -> Unit,
    onSignOutClick: () -> Unit
) {
    val isLight = MaterialTheme.colorScheme.background == com.auraai.ui.theme.LightBg
    val onBgColor = MaterialTheme.colorScheme.onBackground
    val cardContainerBg = if (isLight) Color.Black.copy(alpha = 0.04f) else Color.White.copy(alpha = 0.04f)

    val bgGradient = if (isLight) {
        Brush.verticalGradient(
            colors = listOf(
                Color(0xFFFAFAFF),
                Color(0xFFECECFA),
                Color(0xFFF2F2FC)
            )
        )
    } else {
        Brush.verticalGradient(
            colors = listOf(
                Color(0xFF0F0C20),
                Color(0xFF1A153A),
                Color(0xFF0B0914)
            )
        )
    }
    val coins by gamesViewModel.userCoins.collectAsState()

    val dashboardItems = listOf(
        DashboardItem("AI Chat Companion", "Empathetic active listening", NavRoute.Chat, Icons.Filled.Send, Color(0xFF00F2FE)),
        DashboardItem("Mood Check-in", "Log and track emotions", NavRoute.MoodLog, Icons.Filled.Face, Color(0xFFFFD700)),
        DashboardItem("Calm Soundscapes", "Calming background player", NavRoute.MusicPlayer, Icons.Filled.PlayArrow, Color(0xFF4FACFE)),
        DashboardItem("Circle of Support", "Crisis contacts and shortcuts", NavRoute.SupportContacts, Icons.Filled.AccountCircle, Color(0xFFFF5252)),
        DashboardItem("Mindful Games", "Fun stress-relief puzzles", NavRoute.GameCenter, Icons.Filled.Info, Color(0xFFFF9F43)),
        DashboardItem("Wellness Journal", "Write daily CBT summaries", NavRoute.Journal, Icons.Filled.List, Color(0xFF10AC84)),
        DashboardItem("Self-Care Strategy", "Recommendations checklist", NavRoute.Recommendations, Icons.Filled.Star, Color(0xFF7209B7)),
        DashboardItem("Alerts & Reminders", "Schedule check-in settings", NavRoute.Notifications, Icons.Filled.Notifications, Color(0xFF48DBFB)),
        DashboardItem("Zen Stories", "Gemini calm audio stories", NavRoute.StoryGenerator, Icons.Filled.List, Color(0xFFE040FB)),
        DashboardItem("Zen Breathing", "Animated meditation timer", NavRoute.Meditation, Icons.Filled.Favorite, Color(0xFF00FF87))
    )

    val affirmations = remember {
        listOf(
            "I am worthy of peace, happiness, and emotional balance.",
            "I release worries about things I cannot control.",
            "I choose to focus on my strength and wellness today.",
            "Every breath I take fills me with peace and confidence.",
            "I am doing the best I can, and that is more than enough."
        )
    }
    val currentAffirmation = remember { affirmations.random() }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(bgGradient)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {
            // Header Section
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Hello, ${user.displayName ?: "Friend"}!",
                        color = onBgColor,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Happyshaa • Empathy & Healing",
                        color = onBgColor.copy(alpha = 0.6f),
                        fontSize = 13.sp
                    )
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = { onNavigate(NavRoute.Profile) }) {
                        Icon(Icons.Filled.AccountCircle, contentDescription = "Profile", tint = Color(0xFF00F2FE))
                    }
                    IconButton(onClick = onSignOutClick) {
                        Icon(Icons.Filled.ExitToApp, contentDescription = "Sign Out", tint = onBgColor)
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Daily Affirmation above coins stats
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF00F2FE).copy(alpha = 0.08f)),
                shape = RoundedCornerShape(12.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF00F2FE).copy(alpha = 0.3f))
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "✨", fontSize = 18.sp)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = currentAffirmation,
                        color = if (isLight) Color(0xFF7209B7) else Color(0xFF00F2FE),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Economy / Stats Banner
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = cardContainerBg),
                shape = RoundedCornerShape(16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Filled.Star, contentDescription = null, tint = Color(0xFFFFD700), modifier = Modifier.size(24.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text("Happyshaa Coins", color = onBgColor.copy(alpha = 0.6f), fontSize = 11.sp)
                            Text("$coins 🪙", color = onBgColor, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        }
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Filled.Star, contentDescription = null, tint = Color(0xFF00F2FE), modifier = Modifier.size(24.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text("Level progress", color = onBgColor.copy(alpha = 0.6f), fontSize = 12.sp)
                            Text("Level 1 (Beginner)", color = onBgColor, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Daily Wellness Quests / Tasks Section
            val questsList by gamesViewModel.quests.collectAsState()
            var showAddDialog by remember { mutableStateOf(false) }
            var taskToEdit by remember { mutableStateOf<com.auraai.data.local.db.QuestEntity?>(null) }
            
            val (activeQuests, completedQuests) = questsList.partition { !it.isCompleted }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Daily Wellness Quests", color = onBgColor, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                IconButton(onClick = { showAddDialog = true }, modifier = Modifier.size(36.dp)) {
                    Icon(Icons.Filled.Add, contentDescription = "Add Quest", tint = Color(0xFF00F2FE))
                }
            }
            Spacer(modifier = Modifier.height(4.dp))

            // Active Quests Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = cardContainerBg),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    if (activeQuests.isEmpty()) {
                        Text(
                            text = "No active tasks! Add a task to start earning coins.",
                            color = onBgColor.copy(alpha = 0.5f),
                            fontSize = 13.sp,
                            modifier = Modifier.padding(8.dp)
                        )
                    } else {
                        activeQuests.forEach { quest ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Checkbox(
                                    checked = quest.isCompleted,
                                    onCheckedChange = { 
                                        gamesViewModel.toggleQuestCompleted(quest)
                                    },
                                    colors = CheckboxDefaults.colors(checkedColor = Color(0xFF00F2FE))
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = quest.text,
                                    color = onBgColor,
                                    fontSize = 13.sp,
                                    modifier = Modifier
                                        .weight(1f)
                                        .clickable { taskToEdit = quest }
                                )
                                IconButton(onClick = { gamesViewModel.deleteQuest(quest.id) }, modifier = Modifier.size(32.dp)) {
                                    Icon(Icons.Filled.Delete, contentDescription = "Delete", tint = Color.Red.copy(alpha = 0.6f), modifier = Modifier.size(16.dp))
                                }
                            }
                        }
                    }
                }
            }

            // Completed Quests Section (like Google Tasks collapsed view)
            if (completedQuests.isNotEmpty()) {
                var isCompletedExpanded by remember { mutableStateOf(false) }
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { isCompletedExpanded = !isCompletedExpanded }
                        .padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        if (isCompletedExpanded) Icons.Filled.KeyboardArrowDown else Icons.Filled.KeyboardArrowUp,
                        contentDescription = null,
                        tint = onBgColor.copy(alpha = 0.6f),
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Completed (${completedQuests.size})",
                        color = onBgColor.copy(alpha = 0.6f),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                if (isCompletedExpanded) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = cardContainerBg),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            completedQuests.forEach { quest ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Checkbox(
                                        checked = quest.isCompleted,
                                        onCheckedChange = { 
                                            gamesViewModel.toggleQuestCompleted(quest)
                                        },
                                        colors = CheckboxDefaults.colors(checkedColor = Color(0xFF00F2FE))
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = quest.text,
                                        color = onBgColor.copy(alpha = 0.4f),
                                        fontSize = 13.sp,
                                        style = androidx.compose.ui.text.TextStyle(
                                            textDecoration = androidx.compose.ui.text.style.TextDecoration.LineThrough
                                        ),
                                        modifier = Modifier.weight(1f)
                                    )
                                    IconButton(onClick = { gamesViewModel.deleteQuest(quest.id) }, modifier = Modifier.size(32.dp)) {
                                        Icon(Icons.Filled.Delete, contentDescription = "Delete", tint = Color.Red.copy(alpha = 0.6f), modifier = Modifier.size(16.dp))
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Dialog for adding task
            if (showAddDialog) {
                val dialogBg = if (isLight) MaterialTheme.colorScheme.surface else Color(0xFF15102A)
                val dialogText = if (isLight) MaterialTheme.colorScheme.onSurface else Color.White
                val dialogSubText = if (isLight) MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f) else Color.White.copy(alpha = 0.6f)
                var newQuestText by remember { mutableStateOf("") }
                AlertDialog(
                    onDismissRequest = { showAddDialog = false },
                    title = { Text("New Wellness Quest", color = dialogText, fontWeight = FontWeight.Bold) },
                    text = {
                        OutlinedTextField(
                            value = newQuestText,
                            onValueChange = { newQuestText = it },
                            label = { Text("What quest do you want to accomplish?", color = dialogSubText) },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth(),
                            textStyle = androidx.compose.ui.text.TextStyle(color = dialogText),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color(0xFF00F2FE),
                                unfocusedBorderColor = dialogSubText,
                                cursorColor = Color(0xFF00F2FE)
                            )
                        )
                    },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                if (newQuestText.isNotBlank()) {
                                    gamesViewModel.addQuest(newQuestText)
                                }
                                showAddDialog = false
                            }
                        ) {
                            Text("Add", color = Color(0xFF00F2FE), fontWeight = FontWeight.Bold)
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showAddDialog = false }) {
                            Text("Cancel", color = dialogSubText)
                        }
                    },
                    containerColor = dialogBg
                )
            }

            // Dialog for editing task
            if (taskToEdit != null) {
                val dialogBg = if (isLight) MaterialTheme.colorScheme.surface else Color(0xFF15102A)
                val dialogText = if (isLight) MaterialTheme.colorScheme.onSurface else Color.White
                val dialogSubText = if (isLight) MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f) else Color.White.copy(alpha = 0.6f)
                var editedQuestText by remember { mutableStateOf(taskToEdit!!.text) }
                AlertDialog(
                    onDismissRequest = { taskToEdit = null },
                    title = { Text("Edit Quest", color = dialogText, fontWeight = FontWeight.Bold) },
                    text = {
                        OutlinedTextField(
                            value = editedQuestText,
                            onValueChange = { editedQuestText = it },
                            label = { Text("Quest title", color = dialogSubText) },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth(),
                            textStyle = androidx.compose.ui.text.TextStyle(color = dialogText),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color(0xFF00F2FE),
                                unfocusedBorderColor = dialogSubText,
                                cursorColor = Color(0xFF00F2FE)
                            )
                        )
                    },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                if (editedQuestText.isNotBlank()) {
                                    gamesViewModel.updateQuestText(taskToEdit!!, editedQuestText)
                                }
                                taskToEdit = null
                            }
                        ) {
                            Text("Save", color = Color(0xFF00F2FE), fontWeight = FontWeight.Bold)
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { taskToEdit = null }) {
                            Text("Cancel", color = dialogSubText)
                        }
                    },
                    containerColor = dialogBg
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Menu Options Grid
            Text("Wellness Hub", color = onBgColor, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(6.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(dashboardItems) { item ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp)
                            .clickable { onNavigate(item.targetRoute) },
                        colors = CardDefaults.cardColors(containerColor = cardContainerBg),
                        shape = RoundedCornerShape(20.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(12.dp),
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(item.tint.copy(alpha = 0.1f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(item.icon, contentDescription = null, tint = item.tint, modifier = Modifier.size(18.dp))
                            }
                            Column {
                                Text(item.title, color = onBgColor, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                                Spacer(modifier = Modifier.height(1.dp))
                                Text(item.description, color = onBgColor.copy(alpha = 0.4f), fontSize = 9.sp, maxLines = 2)
                            }
                        }
                    }
                }
            }
        }
    }
}
