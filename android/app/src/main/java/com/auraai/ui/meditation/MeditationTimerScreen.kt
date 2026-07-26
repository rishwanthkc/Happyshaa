package com.auraai.ui.meditation

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.auraai.domain.model.MeditationSession
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MeditationTimerScreen(
    viewModel: MeditationViewModel,
    onBackClick: () -> Unit
) {
    val sessions by viewModel.sessions.collectAsState()
    val streak by viewModel.streak.collectAsState()
    val totalXp by viewModel.totalXp.collectAsState()
    val totalCoins by viewModel.totalCoins.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    val timerRemaining by viewModel.timerSecondsRemaining.collectAsState()
    val totalDuration by viewModel.totalDurationSeconds.collectAsState()
    val isActive by viewModel.isActive.collectAsState()
    val isPaused by viewModel.isPaused.collectAsState()
    val breathingPhase by viewModel.breathingPhase.collectAsState()
    val selectedPattern by viewModel.selectedPattern.collectAsState()

    val dateFormat = remember { SimpleDateFormat("MMM dd, yyyy • HH:mm", Locale.getDefault()) }

    val patterns = listOf("Box Breathing", "4-7-8 Breathing", "Calm Breathing", "Deep Breathing", "Mindfulness Timer")
    val durations = listOf(
        "1 Min" to 60,
        "2 Min" to 120,
        "5 Min" to 300,
        "10 Min" to 600
    )

    LaunchedEffect(Unit) {
        viewModel.loadHistory()
    }

    val bgGradient = Brush.verticalGradient(
        colors = listOf(Color(0xFF0F0C20), Color(0xFF101C38), Color(0xFF0B0914))
    )

    // Breathing circle scale animation mapping
    val targetScale = when (breathingPhase) {
        "Inhale" -> 1.8f
        "Exhale" -> 1.0f
        "Hold" -> 1.8f
        else -> 1.2f
    }
    val animatedScale by animateFloatAsState(
        targetValue = targetScale,
        animationSpec = tween(durationMillis = 3500),
        label = "breathingCircleScale"
    )

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
                    text = "Aura Meditation Timer",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Box(modifier = Modifier.size(48.dp))
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Stats row cards
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf(
                    Triple("Streak", "$streak Days", Color(0xFFFF9F43)),
                    Triple("Coins", "$totalCoins 🪙", Color(0xFFFFD700)),
                    Triple("XP earned", "$totalXp XP", Color(0xFF00F2FE))
                ).forEach { (label, value, tint) ->
                    Card(
                        modifier = Modifier.weight(1f),
                        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.03f)),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(12.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(label, color = Color.White.copy(alpha = 0.5f), fontSize = 11.sp)
                            Text(value, color = tint, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Main Interactive Circle or Settings Panel
            if (isActive) {
                // Interactive Breathing Ring View
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1.5f),
                    contentAlignment = Alignment.Center
                ) {
                    // Outer progress circle
                    val progress = if (totalDuration > 0) timerRemaining.toFloat() / totalDuration else 1f
                    Canvas(modifier = Modifier.size(240.dp)) {
                        drawCircle(
                            color = Color.White.copy(alpha = 0.05f),
                            style = Stroke(width = 8.dp.toPx())
                        )
                        drawArc(
                            color = Color(0xFF00F2FE),
                            startAngle = -90f,
                            sweepAngle = 360f * progress,
                            useCenter = false,
                            style = Stroke(width = 8.dp.toPx(), cap = StrokeCap.Round)
                        )
                    }

                    // Inner Breathing Circle (animated scale)
                    Box(
                        modifier = Modifier
                            .size(100.dp * animatedScale)
                            .clip(RoundedCornerShape(100.dp))
                            .background(
                                Brush.radialGradient(
                                    colors = listOf(Color(0xFF00F2FE).copy(alpha = 0.4f), Color(0xFF00B4D8).copy(alpha = 0.05f))
                                )
                            )
                    )

                    // Text values inside the ring
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = breathingPhase.uppercase(),
                            color = Color(0xFF00F2FE),
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = String.format("%02d:%02d", timerRemaining / 60, timerRemaining % 60),
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                    }
                }

                // Controls row
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = { viewModel.stopTimer() },
                        modifier = Modifier
                            .size(50.dp)
                            .background(Color.White.copy(alpha = 0.05f), RoundedCornerShape(50.dp))
                    ) {
                        Icon(Icons.Filled.Clear, contentDescription = "Stop", tint = Color.Red)
                    }
                    Spacer(modifier = Modifier.width(24.dp))
                    FloatingActionButton(
                        onClick = { if (isPaused) viewModel.startTimer() else viewModel.pauseTimer() },
                        containerColor = Color(0xFF00F2FE),
                        contentColor = Color.Black,
                        shape = RoundedCornerShape(50.dp),
                        modifier = Modifier.size(60.dp)
                    ) {
                        Icon(
                            imageVector = if (isPaused) Icons.Filled.PlayArrow else Icons.Filled.Notifications,
                            contentDescription = "Play/Pause"
                        )
                    }
                }
            } else {
                // Setup Meditation Configuration panel
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1.5f),
                    colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.03f)),
                    shape = RoundedCornerShape(24.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(20.dp),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text("Select Pacing Technique", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                            Spacer(modifier = Modifier.height(8.dp))
                            
                            // Horizontal scroll chips or column
                            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                patterns.chunked(3).forEach { rowList ->
                                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                        rowList.forEach { pattern ->
                                            val isSelected = selectedPattern == pattern
                                            Box(
                                                modifier = Modifier
                                                    .clip(RoundedCornerShape(16.dp))
                                                    .background(if (isSelected) Color(0xFF00F2FE) else Color.White.copy(alpha = 0.05f))
                                                    .clickable { viewModel.selectPattern(pattern) }
                                                    .padding(horizontal = 12.dp, vertical = 6.dp)
                                            ) {
                                                Text(
                                                    text = pattern,
                                                    color = if (isSelected) Color.Black else Color.White,
                                                    fontSize = 12.sp
                                                )
                                            }
                                        }
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(20.dp))

                            Text("Session Duration", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                durations.forEach { (label, secs) ->
                                    val isSelected = totalDuration == secs
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(16.dp))
                                            .background(if (isSelected) Color(0xFF00F2FE) else Color.White.copy(alpha = 0.05f))
                                            .clickable { viewModel.selectDuration(secs) }
                                            .padding(horizontal = 14.dp, vertical = 6.dp)
                                    ) {
                                        Text(
                                            text = label,
                                            color = if (isSelected) Color.Black else Color.White,
                                            fontSize = 13.sp
                                        )
                                    }
                                }
                            }
                        }

                        Button(
                            onClick = { viewModel.startTimer() },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00F2FE), contentColor = Color.Black),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                        ) {
                            Icon(Icons.Filled.Favorite, contentDescription = null)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Begin Session", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // History Log section
            Text("Session History Log", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))

            if (isLoading) {
                Box(modifier = Modifier.fillMaxWidth().weight(1f), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Color(0xFF00F2FE))
                }
            } else if (sessions.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color.White.copy(alpha = 0.03f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No meditation sessions logged yet.", color = Color.White.copy(alpha = 0.5f), fontSize = 13.sp)
                }
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(sessions) { item ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(16.dp))
                                .background(Color.White.copy(alpha = 0.03f))
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(Color(0xFF00F2FE).copy(alpha = 0.1f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Filled.Favorite, contentDescription = null, tint = Color(0xFF00F2FE))
                            }

                            Spacer(modifier = Modifier.width(16.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Text(item.breathingType, color = Color.White, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                                val dateStr = dateFormat.format(Date(item.timestamp * 1000))
                                Text(dateStr, color = Color.White.copy(alpha = 0.5f), fontSize = 12.sp)
                            }

                            Column(horizontalAlignment = Alignment.End) {
                                Text("+${item.xpReward} XP", color = Color(0xFF00F2FE), fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                Text("+${item.coinsReward} Coins", color = Color(0xFFFFD700), fontSize = 11.sp)
                            }
                        }
                    }
                }
            }
        }
    }
}
