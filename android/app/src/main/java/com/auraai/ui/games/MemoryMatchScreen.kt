package com.auraai.ui.games

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MemoryMatchScreen(
    viewModel: GamesViewModel,
    onBackClick: () -> Unit
) {
    var isSixBySix by remember { mutableStateOf(false) } // false = 4x4, true = 6x6
    val boardSize = if (isSixBySix) 36 else 16

    val emojis = listOf(
        "🌸", "🌟", "🍀", "💎", "🔮", "🍃", "🌙", "🌊",
        "🦊", "🐼", "🐨", "🐸", "🍎", "🥝", "🧩", "⚽",
        "🎨", "🎭"
    )

    var cards by remember { mutableStateOf(emptyList<MemoryCard>()) }
    var selectedIndices by remember { mutableStateOf(emptyList<Int>()) }

    var movesCount by remember { mutableStateOf(0) }
    var matchCount by remember { mutableStateOf(0) }
    var elapsedTime by remember { mutableStateOf(0) }
    var isGameFinished by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()
    val progressEvent by viewModel.progressEvent.collectAsState()

    // Initialize/Reset Board
    fun resetBoard() {
        val pairsCount = boardSize / 2
        val chosenEmojis = emojis.take(pairsCount)
        val deck = (chosenEmojis + chosenEmojis).shuffled().mapIndexed { idx, emoji ->
            MemoryCard(id = idx, content = emoji)
        }
        cards = deck
        selectedIndices = emptyList()
        movesCount = 0
        matchCount = 0
        elapsedTime = 0
        isGameFinished = false
        viewModel.clearProgressEvent()
    }

    // Timer loop
    LaunchedEffect(isGameFinished, cards) {
        if (cards.isNotEmpty() && !isGameFinished) {
            while (true) {
                delay(1000)
                elapsedTime++
            }
        }
    }

    // Auto load board
    LaunchedEffect(isSixBySix) {
        resetBoard()
    }

    // Check complete
    LaunchedEffect(matchCount, boardSize) {
        if (matchCount > 0 && matchCount == boardSize / 2) {
            isGameFinished = true
            val finalScore = (100 - movesCount - elapsedTime / 2).coerceAtLeast(10)
            viewModel.submitScore("memorymatch", finalScore)
        }
    }

    // Card click logic
    fun onCardClick(index: Int) {
        if (selectedIndices.size >= 2 || cards[index].isFlipped || cards[index].isMatched) return

        // Flip selected
        cards = cards.mapIndexed { idx, card ->
            if (idx == index) card.copy(isFlipped = true) else card
        }
        val newSelected = selectedIndices + index
        selectedIndices = newSelected

        if (newSelected.size == 2) {
            movesCount++
            scope.launch {
                delay(800)
                val firstIdx = newSelected[0]
                val secondIdx = newSelected[1]
                if (cards[firstIdx].content == cards[secondIdx].content) {
                    // Match found
                    cards = cards.mapIndexed { idx, card ->
                        if (idx == firstIdx || idx == secondIdx) card.copy(isMatched = true) else card
                    }
                    matchCount++
                } else {
                    // Flip back
                    cards = cards.mapIndexed { idx, card ->
                        if (idx == firstIdx || idx == secondIdx) card.copy(isFlipped = false) else card
                    }
                }
                selectedIndices = emptyList()
            }
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
                    text = "Aura Memory Match",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                IconButton(onClick = { resetBoard() }) {
                    Icon(Icons.Filled.Refresh, contentDescription = "Restart", tint = Color(0xFF00F2FE))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Difficulty Chips
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                FilterChip(
                    selected = !isSixBySix,
                    onClick = { isSixBySix = false },
                    label = { Text("Easy 4x4 Grid") },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = Color(0xFF00F2FE).copy(alpha = 0.2f),
                        selectedLabelColor = Color(0xFF00F2FE)
                    )
                )
                Spacer(modifier = Modifier.width(16.dp))
                FilterChip(
                    selected = isSixBySix,
                    onClick = { isSixBySix = true },
                    label = { Text("Hard 6x6 Grid") },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = Color(0xFF00F2FE).copy(alpha = 0.2f),
                        selectedLabelColor = Color(0xFF00F2FE)
                    )
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Info Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White.copy(alpha = 0.03f), RoundedCornerShape(12.dp))
                    .padding(12.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Moves", color = Color.White.copy(alpha = 0.5f), fontSize = 11.sp)
                    Text("$movesCount", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Time", color = Color.White.copy(alpha = 0.5f), fontSize = 11.sp)
                    Text(String.format("%02d:%02d", elapsedTime / 60, elapsedTime % 60), color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Progress", color = Color.White.copy(alpha = 0.5f), fontSize = 11.sp)
                    Text("$matchCount/${boardSize / 2}", color = Color(0xFF00F2FE), fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Board Grid
            val cols = if (isSixBySix) 6 else 4
            LazyVerticalGrid(
                columns = GridCells.Fixed(cols),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(boardSize) { index ->
                    if (cards.size == boardSize) {
                        val card = cards[index]
                        MemoryCardView(card = card, onClick = { onCardClick(index) })
                    }
                }
            }
        }

        // Completion Confetti Particles & Rewards overlay
        if (isGameFinished) {
            ConfettiOverlay()

            AlertDialog(
                onDismissRequest = { resetBoard() },
                title = { Text("Zen Mind Level Completed!") },
                text = {
                    Column {
                        Text("Congratulations! Your memory focus was extremely sharp.", color = Color.White.copy(alpha = 0.8f))
                        Spacer(modifier = Modifier.height(12.dp))
                        Text("Stats:", fontWeight = FontWeight.Bold, color = Color(0xFF00F2FE))
                        Text("• Time: ${elapsedTime / 60}m ${elapsedTime % 60}s")
                        Text("• Total Moves: $movesCount")
                        
                        progressEvent?.let { event ->
                            Spacer(modifier = Modifier.height(12.dp))
                            Text("Rewards Earned:", fontWeight = FontWeight.Bold, color = Color(0xFFFFD700))
                            Text("• XP Earned: +${event.xpEarned} XP")
                            Text("• Coins Earned: +${event.coinsEarned} Coins")
                            Text("• Current Wallet Balance: ${event.newBalance} Coins")
                            
                            event.unlockedAchievement?.let { ach ->
                                Spacer(modifier = Modifier.height(8.dp))
                                Text("🏆 Milestone Unlocked: $ach!", color = Color(0xFF00FF87), fontWeight = FontWeight.SemiBold)
                            }
                        }
                    }
                },
                confirmButton = {
                    Button(onClick = { resetBoard() }) {
                        Text("Play Again")
                    }
                }
            )
        }
    }
}

@Composable
fun MemoryCardView(card: MemoryCard, onClick: () -> Unit) {
    val rotation by animateFloatAsState(
        targetValue = if (card.isFlipped || card.isMatched) 180f else 0f,
        animationSpec = tween(durationMillis = 400, easing = FastOutSlowInEasing),
        label = "cardFlipAnimation"
    )

    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .clip(RoundedCornerShape(12.dp))
            .graphicsLayer {
                rotationY = rotation
                cameraDistance = 12f * density
            }
            .background(
                if (card.isMatched) Color(0xFF00FF87).copy(alpha = 0.1f)
                else if (card.isFlipped) Color.White.copy(alpha = 0.08f)
                else Color(0xFF00F2FE).copy(alpha = 0.15f)
            )
            .clickable(enabled = !card.isMatched) { onClick() },
        contentAlignment = Alignment.Center
    ) {
        if (rotation > 90f) {
            Box(
                modifier = Modifier.graphicsLayer { rotationY = 180f },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = card.content,
                    fontSize = 28.sp
                )
            }
        } else {
            Text(
                text = "✨",
                color = Color(0xFF00F2FE),
                fontSize = 18.sp
            )
        }
    }
}

data class MemoryCard(
    val id: Int,
    val content: String,
    val isFlipped: Boolean = false,
    val isMatched: Boolean = false
)

@Composable
fun ConfettiOverlay() {
    val infiniteTransition = rememberInfiniteTransition(label = "confettiTransition")
    val progress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "confettiFall"
    )

    androidx.compose.foundation.Canvas(modifier = Modifier.fillMaxSize()) {
        val count = 40
        val random = Random(42)
        for (i in 0 until count) {
            val startX = random.nextFloat() * size.width
            val speed = 200f + random.nextFloat() * 300f
            val y = (speed * progress * 4f) % size.height
            val radius = 8f + random.nextFloat() * 12f
            val color = when (random.nextInt(4)) {
                0 -> Color(0xFF00F2FE)
                1 -> Color(0xFFFFD700)
                2 -> Color(0xFFFF5252)
                else -> Color(0xFF00FF87)
            }
            drawCircle(color = color, radius = radius, center = androidx.compose.ui.geometry.Offset(startX, y))
        }
    }
}
