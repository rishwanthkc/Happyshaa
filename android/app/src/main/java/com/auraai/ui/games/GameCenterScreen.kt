package com.auraai.ui.games

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.auraai.domain.model.Game
import com.auraai.domain.model.Achievement
import com.auraai.domain.model.GameProgress
import kotlinx.coroutines.delay

import com.auraai.ui.navigation.NavRoute

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameCenterScreen(
    viewModel: GamesViewModel,
    onBackClick: () -> Unit,
    onNavigateToGame: (NavRoute) -> Unit
) {
    val games by viewModel.games.collectAsState()
    val achievements by viewModel.achievements.collectAsState()
    val progressEvent by viewModel.progressEvent.collectAsState()
    val userCoins by viewModel.userCoins.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    var activePlayingGameId by remember { mutableStateOf<String?>(null) }
    var scoreValue by remember { mutableStateOf(0) }

    // Bubble Pop Game state
    var bubblesList by remember { mutableStateOf(emptyList<Pair<Int, Boolean>>()) } // Pair of id to isPopped

    LaunchedEffect(Unit) {
        viewModel.loadGameCenter()
    }

    // Initialize Bubble Pop Board
    LaunchedEffect(activePlayingGameId) {
        if (activePlayingGameId == "bubblepop") {
            scoreValue = 0
            bubblesList = (0 until 12).map { it to false }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF0F0C1B), Color(0xFF1E2A38))
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBackClick) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                }
                Text(
                    text = "Aura Game Center",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.White.copy(alpha = 0.05f))
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Filled.Star, contentDescription = "Coins", tint = Color(0xFFFFD700), modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("$userCoins Coins", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Lower Section Tabs: 1. Games catalog, 2. Achievements
            var selectedTab by remember { mutableStateOf(0) }
            TabRow(
                selectedTabIndex = selectedTab,
                containerColor = Color.Transparent,
                contentColor = Color(0xFF00F2FE),
                modifier = Modifier.fillMaxWidth()
            ) {
                Tab(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    text = { Text("Mindful Games", color = Color.White) }
                )
                Tab(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    text = { Text("Achievements", color = Color.White) }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (isLoading) {
                Box(modifier = Modifier.fillMaxWidth().weight(1f), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Color(0xFF00F2FE))
                }
            } else {
                if (selectedTab == 0) {
                    // Games grid
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier.weight(1f),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(games) { game ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        when (game.gameId) {
                                            "memorymatch" -> onNavigateToGame(NavRoute.MemoryMatch)
                                            "coloring" -> onNavigateToGame(NavRoute.Coloring)
                                            else -> activePlayingGameId = game.gameId
                                        }
                                    },
                                colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.03f)),
                                shape = RoundedCornerShape(16.dp)
                            ) {
                                Column(
                                    modifier = Modifier.padding(16.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(50.dp)
                                            .clip(CircleShape)
                                            .background(Color(0xFF00F2FE).copy(alpha = 0.1f)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        val icon = when (game.gameId) {
                                            "bubblepop" -> Icons.Filled.Info
                                            "coloring" -> Icons.Filled.Edit
                                            "wordpuzzle" -> Icons.Filled.Edit
                                            "tictactoe" -> Icons.Filled.Close
                                            else -> Icons.Filled.Info
                                        }
                                        Icon(icon, contentDescription = null, tint = Color(0xFF00F2FE), modifier = Modifier.size(28.dp))
                                    }
                                    Spacer(modifier = Modifier.height(12.dp))
                                    Text(game.title, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(game.description, color = Color.White.copy(alpha = 0.5f), fontSize = 11.sp, maxLines = 2)
                                }
                            }
                        }
                    }
                } else {
                    // Achievements list
                    if (achievements.isEmpty()) {
                        Box(modifier = Modifier.fillMaxWidth().weight(1f), contentAlignment = Alignment.Center) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(Icons.Filled.Star, contentDescription = null, tint = Color.White.copy(alpha = 0.2f), modifier = Modifier.size(64.dp))
                                Spacer(modifier = Modifier.height(12.dp))
                                Text("Unlock milestones by playing wellness games", color = Color.White.copy(alpha = 0.5f), fontSize = 13.sp)
                            }
                        }
                    } else {
                        LazyColumn(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(achievements) { ach ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(16.dp))
                                        .background(Color.White.copy(alpha = 0.03f))
                                        .padding(16.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(Icons.Filled.Star, contentDescription = null, tint = Color(0xFFFFD700), modifier = Modifier.size(32.dp))
                                    Spacer(modifier = Modifier.width(16.dp))
                                    Column {
                                        val title = if (ach.achievementType == "COIN_COLLECTOR") "Coin Collector" else "Mindful Gamer"
                                        val desc = if (ach.achievementType == "COIN_COLLECTOR") "Reach a balance of 100 virtual coins" else "Submit a wellness score 5 times"
                                        Text(title, color = Color.White, fontWeight = FontWeight.SemiBold, fontSize = 15.sp)
                                        Text(desc, color = Color.White.copy(alpha = 0.5f), fontSize = 13.sp)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // Bubble Pop interactive sheet game
        if (activePlayingGameId == "bubblepop") {
            ModalBottomSheet(
                onDismissRequest = { activePlayingGameId = null },
                containerColor = Color(0xFF1E2A38)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Pop Bubbles to Destress", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    Text("Score: $scoreValue", color = Color(0xFF00F2FE), fontSize = 14.sp)
                    
                    Spacer(modifier = Modifier.height(16.dp))

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(4),
                        modifier = Modifier.height(200.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(bubblesList) { bubble ->
                            val isPopped = bubble.second
                            Box(
                                modifier = Modifier
                                    .size(50.dp)
                                    .clip(CircleShape)
                                    .then(
                                        if (isPopped) Modifier.background(Color.White.copy(alpha = 0.05f))
                                        else Modifier.background(Brush.radialGradient(listOf(Color(0xFF00F2FE), Color(0xFF4FACFE))))
                                    )
                                    .clickable(enabled = !isPopped) {
                                        scoreValue += 5
                                        bubblesList = bubblesList.map {
                                            if (it.first == bubble.first) it.first to true else it
                                        }
                                    }
                                    .border(
                                        width = 1.dp,
                                        color = if (isPopped) Color.White.copy(alpha = 0.2f) else Color.Transparent,
                                        shape = CircleShape
                                    )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            viewModel.submitScore("bubblepop", scoreValue)
                            activePlayingGameId = null
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00F2FE), contentColor = Color.Black),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Complete Session")
                    }
                }
            }
        }

        // Score Reward Success Banner
        if (progressEvent != null) {
            AlertDialog(
                onDismissRequest = { viewModel.clearProgressEvent() },
                title = { Text("Session Completed!") },
                text = {
                    Column {
                        Text("Awesome! You completed a calm game session.")
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Coins Earned: +${progressEvent!!.coinsEarned} 🪙", color = Color(0xFFFFD700), fontWeight = FontWeight.Bold)
                        Text("XP Earned: +${progressEvent!!.xpEarned} ✨", color = Color(0xFF00F2FE), fontWeight = FontWeight.Bold)
                        if (progressEvent!!.unlockedAchievement != null) {
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("🏆 Achievement Unlocked: ${progressEvent!!.unlockedAchievement}!", color = Color.Red, fontWeight = FontWeight.Bold)
                        }
                    }
                },
                confirmButton = {
                    TextButton(onClick = { viewModel.clearProgressEvent() }) {
                        Text("Great!")
                    }
                }
            )
        }
    }
}
