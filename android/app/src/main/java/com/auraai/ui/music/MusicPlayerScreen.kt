package com.auraai.ui.music

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.auraai.domain.model.MusicTrack
import kotlinx.coroutines.delay

@Composable
fun MusicPlayerScreen(
    viewModel: MusicViewModel,
    onBackClick: () -> Unit
) {
    val songs by viewModel.songs.collectAsState()
    val favorites by viewModel.favorites.collectAsState()
    val recommendations by viewModel.recommendations.collectAsState()
    val currentTrack by viewModel.currentTrack.collectAsState()
    val isPlaying by viewModel.isPlaying.collectAsState()
    val playbackPosition by viewModel.playbackPosition.collectAsState()
    val duration by viewModel.trackDuration.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    var showSleepTimerMenu by remember { mutableStateFlowOf(false) }
    var activeTimerMinutes by remember { mutableStateFlowOf<Int?>(null) }
    var timerRemainingSeconds by remember { mutableStateFlowOf(0) }

    var isShuffleEnabled by remember { mutableStateFlowOf(false) }
    var isRepeatEnabled by remember { mutableStateFlowOf(false) }
    var selectedCategoryFilter by remember { mutableStateFlowOf("All") }

    LaunchedEffect(Unit) {
        viewModel.loadAllMusic()
    }

    // Dynamic timer ticker
    LaunchedEffect(activeTimerMinutes, isPlaying) {
        if (activeTimerMinutes != null && isPlaying) {
            timerRemainingSeconds = activeTimerMinutes!! * 60
            while (timerRemainingSeconds > 0) {
                delay(1000)
                timerRemainingSeconds--
                if (timerRemainingSeconds == 0) {
                    viewModel.pauseTrack()
                    activeTimerMinutes = null
                }
            }
        } else {
            timerRemainingSeconds = 0
        }
    }

    // Vinyl rotation angle
    val infiniteTransition = rememberInfiniteTransition(label = "vinyl")
    val rotationAngle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(8000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "angle"
    )
    val finalAngle = if (isPlaying) rotationAngle else 0f

    // Simulated progress ticks
    LaunchedEffect(isPlaying) {
        while (isPlaying) {
            delay(1000)
            if (playbackPosition < duration) {
                viewModel.seekTo(playbackPosition + 1)
            } else {
                if (isRepeatEnabled && currentTrack != null) {
                    viewModel.playTrack(currentTrack!!)
                } else {
                    viewModel.pauseTrack()
                }
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
                    text = "Aura Calm Player",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                IconButton(onClick = { showSleepTimerMenu = true }) {
                    Icon(
                        imageVector = Icons.Filled.Info,
                        contentDescription = "Sleep Timer",
                        tint = if (activeTimerMinutes != null) Color(0xFF00F2FE) else Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Upper Vinyl Section or loading
            if (currentTrack != null) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Rotating Vinyl
                    Box(
                        modifier = Modifier
                            .size(180.dp)
                            .rotate(finalAngle)
                            .clip(CircleShape)
                            .background(Color.Black)
                            .padding(4.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        // Outer Vinyl Grooves
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(CircleShape)
                                .background(
                                    Brush.radialGradient(
                                        colors = listOf(Color(0xFF2C2C2C), Color.Black)
                                    )
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            // Center Disk label
                            Box(
                                modifier = Modifier
                                    .size(70.dp)
                                    .clip(CircleShape)
                                    .background(
                                        Brush.linearGradient(
                                            colors = listOf(Color(0xFF00F2FE), Color(0xFF4FACFE))
                                        )
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.PlayArrow,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(32.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = currentTrack!!.title,
                        color = Color.White,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = currentTrack!!.artist,
                        color = Color.White.copy(alpha = 0.7f),
                        fontSize = 15.sp
                    )

                    // Timer Remaining Alert
                    if (activeTimerMinutes != null) {
                        val mins = timerRemainingSeconds / 60
                        val secs = timerRemainingSeconds % 60
                        Text(
                            text = String.format("Sleep timer active: %02d:%02d", mins, secs),
                            color = Color(0xFF00F2FE),
                            fontSize = 13.sp,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Seek Slider
                    Slider(
                        value = playbackPosition.toFloat(),
                        onValueChange = { viewModel.seekTo(it.toLong()) },
                        valueRange = 0f..duration.toFloat(),
                        colors = SliderDefaults.colors(
                            activeTrackColor = Color(0xFF00F2FE),
                            thumbColor = Color(0xFF00F2FE),
                            inactiveTrackColor = Color.White.copy(alpha = 0.2f)
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )

                    // Seek labels
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = String.format("%d:%02d", playbackPosition / 60, playbackPosition % 60),
                            color = Color.White.copy(alpha = 0.5f),
                            fontSize = 12.sp
                        )
                        Text(
                            text = String.format("%d:%02d", duration / 60, duration % 60),
                            color = Color.White.copy(alpha = 0.5f),
                            fontSize = 12.sp
                        )
                    }

                    // Media controls
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = { isShuffleEnabled = !isShuffleEnabled }) {
                            Icon(
                                imageVector = Icons.Filled.Refresh,
                                contentDescription = "Shuffle",
                                tint = if (isShuffleEnabled) Color(0xFF00F2FE) else Color.White
                            )
                        }
                        IconButton(onClick = { /* Previous track */ }) {
                            Icon(Icons.Filled.ArrowBack, contentDescription = "Previous", tint = Color.White, modifier = Modifier.size(36.dp))
                        }
                        FloatingActionButton(
                            onClick = { if (isPlaying) viewModel.pauseTrack() else viewModel.playTrack(currentTrack!!) },
                            containerColor = Color(0xFF00F2FE),
                            contentColor = Color.Black,
                            shape = CircleShape,
                            modifier = Modifier.size(60.dp)
                        ) {
                            Icon(
                                imageVector = if (isPlaying) Icons.Filled.Close else Icons.Filled.PlayArrow,
                                contentDescription = "Play/Pause",
                                modifier = Modifier.size(32.dp)
                            )
                        }
                        IconButton(onClick = { /* Next track */ }) {
                            Icon(Icons.Filled.ArrowForward, contentDescription = "Next", tint = Color.White, modifier = Modifier.size(36.dp))
                        }
                        IconButton(onClick = { isRepeatEnabled = !isRepeatEnabled }) {
                            Icon(
                                imageVector = Icons.Filled.Refresh,
                                contentDescription = "Repeat",
                                tint = if (isRepeatEnabled) Color(0xFF00F2FE) else Color.White
                            )
                        }
                    }
                }
            } else {
                // Intro Screen Empty state
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(24.dp))
                        .background(Color.White.copy(alpha = 0.05f)),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Filled.PlayArrow, contentDescription = null, tint = Color.White.copy(alpha = 0.3f), modifier = Modifier.size(64.dp))
                        Spacer(modifier = Modifier.height(12.dp))
                        Text("Select a Calm Track below to begin", color = Color.White.copy(alpha = 0.5f), fontSize = 14.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Catalog Header
            Text(
                text = "Calming Soundscapes",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            // Category Chips
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf("All", "Nature", "Lofi", "Binaural Beats").forEach { category ->
                    val isSelected = selectedCategoryFilter == category
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .background(if (isSelected) Color(0xFF00F2FE) else Color.White.copy(alpha = 0.05f))
                            .clickable { selectedCategoryFilter = category }
                            .padding(horizontal = 14.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = category,
                            color = if (isSelected) Color.Black else Color.White,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            // Track list
            if (isLoading) {
                Box(modifier = Modifier.fillMaxWidth().weight(1f), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Color(0xFF00F2FE))
                }
            } else {
                val filteredSongs = songs.filter {
                    selectedCategoryFilter == "All" || it.category == selectedCategoryFilter
                }

                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(filteredSongs) { track ->
                        val isCurrent = currentTrack?.songId == track.songId
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(16.dp))
                                .background(if (isCurrent) Color.White.copy(alpha = 0.1f) else Color.White.copy(alpha = 0.03f))
                                .clickable { viewModel.playTrack(track) }
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = if (isCurrent && isPlaying) Icons.Filled.PlayArrow else Icons.Filled.PlayArrow,
                                contentDescription = null,
                                tint = if (isCurrent) Color(0xFF00F2FE) else Color.White.copy(alpha = 0.6f),
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(track.title, color = Color.White, fontWeight = FontWeight.SemiBold, fontSize = 15.sp)
                                Text(track.artist, color = Color.White.copy(alpha = 0.5f), fontSize = 13.sp)
                            }
                            IconButton(onClick = { viewModel.toggleFavorite(track) }) {
                                Icon(
                                    imageVector = if (track.isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                                    contentDescription = "Favorite",
                                    tint = if (track.isFavorite) Color.Red else Color.White.copy(alpha = 0.5f)
                                )
                            }
                        }
                    }
                }
            }
        }

        // Sleep Timer Bottom Sheet dialog mock
        if (showSleepTimerMenu) {
            AlertDialog(
                onDismissRequest = { showSleepTimerMenu = false },
                title = { Text("Set Sleep Timer") },
                text = {
                    Column {
                        listOf(15, 30, 45, 60).forEach { mins ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        activeTimerMinutes = mins
                                        showSleepTimerMenu = false
                                    }
                                    .padding(vertical = 12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(text = "$mins Minutes")
                                if (activeTimerMinutes == mins) {
                                    Icon(Icons.Filled.Check, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                                }
                            }
                        }
                        if (activeTimerMinutes != null) {
                            Button(
                                onClick = {
                                    activeTimerMinutes = null
                                    showSleepTimerMenu = false
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 16.dp)
                            ) {
                                Text("Cancel Timer")
                            }
                        }
                    }
                },
                confirmButton = {
                    TextButton(onClick = { showSleepTimerMenu = false }) {
                        Text("Close")
                    }
                }
            )
        }
    }
}

// Simple state flow helper mapping to Compose State
private fun <T> mutableStateFlowOf(value: T): MutableState<T> = mutableStateOf(value)
