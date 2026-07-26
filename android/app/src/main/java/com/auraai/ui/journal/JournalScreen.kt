package com.auraai.ui.journal

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.auraai.domain.model.JournalEntry
import com.auraai.domain.model.WeeklyReport
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JournalScreen(
    viewModel: JournalViewModel,
    onBackClick: () -> Unit
) {
    val journals by viewModel.journals.collectAsState()
    val weeklyReports by viewModel.weeklyReports.collectAsState()
    val recentAnalysis by viewModel.recentAnalysis.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    var showComposerDialog by remember { mutableStateOf(false) }
    var titleInput by remember { mutableStateOf("") }
    var contentInput by remember { mutableStateOf("") }

    var selectedSection by remember { mutableStateOf(0) } // 0: Entries, 1: Weekly Reports

    val dateFormat = remember { SimpleDateFormat("MMM d, yyyy • h:mm a", Locale.getDefault()) }

    LaunchedEffect(Unit) {
        viewModel.loadJournalData()
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
                    text = "Aura Wellness Journal",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                IconButton(onClick = {
                    titleInput = ""
                    contentInput = ""
                    showComposerDialog = true
                }) {
                    Icon(Icons.Filled.Edit, contentDescription = "Write Journal", tint = Color(0xFF00F2FE))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Tab bar
            TabRow(
                selectedTabIndex = selectedSection,
                containerColor = Color.Transparent,
                contentColor = Color(0xFF00F2FE),
                modifier = Modifier.fillMaxWidth()
            ) {
                Tab(
                    selected = selectedSection == 0,
                    onClick = { selectedSection = 0 },
                    text = { Text("Daily Reflections", color = Color.White) }
                )
                Tab(
                    selected = selectedSection == 1,
                    onClick = { selectedSection = 1 },
                    text = { Text("Weekly CBT Analytics", color = Color.White) }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (isLoading) {
                Box(modifier = Modifier.fillMaxWidth().weight(1f), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Color(0xFF00F2FE))
                }
            } else {
                if (selectedSection == 0) {
                    // Daily entries
                    if (journals.isEmpty()) {
                        Box(modifier = Modifier.fillMaxWidth().weight(1f), contentAlignment = Alignment.Center) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(Icons.Filled.List, contentDescription = null, tint = Color.White.copy(alpha = 0.2f), modifier = Modifier.size(64.dp))
                                Spacer(modifier = Modifier.height(12.dp))
                                Text("Write down your thoughts and receive empathetic AI reflections.", color = Color.White.copy(alpha = 0.5f), fontSize = 13.sp)
                            }
                        }
                    } else {
                        LazyColumn(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(journals) { entry ->
                                Card(
                                    modifier = Modifier.fillMaxWidth(),
                                    colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.03f)),
                                    shape = RoundedCornerShape(16.dp)
                                ) {
                                    Column(modifier = Modifier.padding(16.dp)) {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Text(
                                                text = entry.title?.takeIf { it.isNotBlank() } ?: "Reflection",
                                                color = Color.White,
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 16.sp
                                            )
                                            val dateStr = dateFormat.format(Date((entry.timestamp * 1000).toLong()))
                                            Text(dateStr, color = Color.White.copy(alpha = 0.4f), fontSize = 12.sp)
                                        }

                                        Spacer(modifier = Modifier.height(8.dp))

                                        Text(entry.content, color = Color.White.copy(alpha = 0.8f), fontSize = 14.sp)

                                        Spacer(modifier = Modifier.height(12.dp))

                                        // AI Reflection box
                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .clip(RoundedCornerShape(12.dp))
                                                .background(Color(0xFF00F2FE).copy(alpha = 0.05f))
                                                .padding(12.dp)
                                        ) {
                                            Column {
                                                Row(verticalAlignment = Alignment.CenterVertically) {
                                                    Icon(Icons.Filled.Star, contentDescription = null, tint = Color(0xFF00F2FE), modifier = Modifier.size(16.dp))
                                                    Spacer(modifier = Modifier.width(6.dp))
                                                    Text("Aura Compassionate Reflection", color = Color(0xFF00F2FE), fontWeight = FontWeight.SemiBold, fontSize = 12.sp)
                                                }
                                                Spacer(modifier = Modifier.height(4.dp))
                                                Text(entry.reflection, color = Color.White.copy(alpha = 0.9f), fontSize = 13.sp)
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                } else {
                    // Weekly progress summary
                    Column(modifier = Modifier.weight(1f)) {
                        Button(
                            onClick = { viewModel.triggerWeeklyCompilation() },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00F2FE), contentColor = Color.Black),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(Icons.Filled.List, contentDescription = null)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Generate Weekly Summary", fontWeight = FontWeight.Bold)
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        if (weeklyReports.isEmpty()) {
                            Box(modifier = Modifier.fillMaxWidth().weight(1f), contentAlignment = Alignment.Center) {
                                Text("No weekly summaries compiled yet.", color = Color.White.copy(alpha = 0.5f), fontSize = 13.sp)
                            }
                        } else {
                            LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                                items(weeklyReports) { report ->
                                    Card(
                                        modifier = Modifier.fillMaxWidth(),
                                        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.03f)),
                                        shape = RoundedCornerShape(20.dp)
                                    ) {
                                        Column(modifier = Modifier.padding(20.dp)) {
                                            val dateStr = dateFormat.format(Date((report.timestamp * 1000).toLong()))
                                            Text("CBT Progress Summary • $dateStr", color = Color(0xFF00F2FE), fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                            
                                            Spacer(modifier = Modifier.height(12.dp))

                                            Row(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalArrangement = Arrangement.SpaceBetween
                                            ) {
                                                Column {
                                                    Text("Dominant Mood", color = Color.White.copy(alpha = 0.5f), fontSize = 12.sp)
                                                    Text(report.dominantMood, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                                                }
                                                Column(horizontalAlignment = Alignment.End) {
                                                    Text("Avg Stress Score", color = Color.White.copy(alpha = 0.5f), fontSize = 12.sp)
                                                    Text(String.format("%.2f", report.averageStressLevel), color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                                                }
                                            }

                                            Spacer(modifier = Modifier.height(12.dp))

                                            Text("Gratitude Summary", color = Color.White.copy(alpha = 0.5f), fontSize = 12.sp)
                                            Text(report.gratitudeSummary, color = Color.White.copy(alpha = 0.8f), fontSize = 13.sp)

                                            Spacer(modifier = Modifier.height(12.dp))

                                            Text("Your Self-Care Strategy Plan", color = Color(0xFFFFD700), fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                                            Spacer(modifier = Modifier.height(4.dp))
                                            report.selfCarePlan.forEach { planItem ->
                                                Row(
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .padding(vertical = 4.dp),
                                                    verticalAlignment = Alignment.CenterVertically
                                                ) {
                                                    Icon(Icons.Filled.List, contentDescription = null, tint = Color.White.copy(alpha = 0.5f), modifier = Modifier.size(18.dp))
                                                    Spacer(modifier = Modifier.width(8.dp))
                                                    Text(planItem, color = Color.White, fontSize = 13.sp)
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // Journal composer dialog
        if (showComposerDialog) {
            val context = androidx.compose.ui.platform.LocalContext.current
            val isRecording by viewModel.isRecording.collectAsState()
            val isPausedRecording by viewModel.isPausedRecording.collectAsState()
            val recordingDurationSeconds by viewModel.recordingDurationSeconds.collectAsState()
            val amplitudeList by viewModel.amplitudeList.collectAsState()
            val isPlayingPreview by viewModel.isPlayingPreview.collectAsState()

            var composeMode by remember { mutableStateOf(0) } // 0: Text, 1: Voice Note

            AlertDialog(
                onDismissRequest = {
                    viewModel.deleteRecording()
                    showComposerDialog = false
                },
                title = { Text("New Daily Reflection") },
                text = {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        TabRow(
                            selectedTabIndex = composeMode,
                            containerColor = Color.Transparent,
                            contentColor = Color(0xFF00F2FE),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Tab(
                                selected = composeMode == 0,
                                onClick = { composeMode = 0 },
                                text = { Text("Write") }
                            )
                            Tab(
                                selected = composeMode == 1,
                                onClick = { composeMode = 1 },
                                text = { Text("Speak") }
                            )
                        }

                        if (composeMode == 0) {
                            TextField(
                                value = titleInput,
                                onValueChange = { titleInput = it },
                                label = { Text("Title (Optional)") },
                                modifier = Modifier.fillMaxWidth()
                            )
                            TextField(
                                value = contentInput,
                                onValueChange = { contentInput = it },
                                label = { Text("What's on your mind today?") },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(150.dp)
                            )
                        } else {
                            TextField(
                                value = titleInput,
                                onValueChange = { titleInput = it },
                                label = { Text("Title (Optional)") },
                                modifier = Modifier.fillMaxWidth()
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color.White.copy(alpha = 0.03f), RoundedCornerShape(16.dp))
                                    .padding(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                if (!isRecording && amplitudeList.isEmpty()) {
                                    IconButton(
                                        onClick = { viewModel.startRecording(context) },
                                        modifier = Modifier
                                            .size(64.dp)
                                            .background(Color(0xFF00F2FE).copy(alpha = 0.1f), RoundedCornerShape(32.dp))
                                    ) {
                                        Icon(Icons.Filled.PlayArrow, contentDescription = "Record", tint = Color(0xFF00F2FE))
                                    }
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text("Tap to record your thoughts", color = Color.White.copy(alpha = 0.5f), fontSize = 12.sp)
                                } else if (isRecording) {
                                    val minutes = recordingDurationSeconds / 60
                                    val seconds = recordingDurationSeconds % 60
                                    Text(
                                        text = String.format("%02d:%02d", minutes, seconds),
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 18.sp
                                    )
                                    Spacer(modifier = Modifier.height(12.dp))

                                    // Waveform
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(40.dp),
                                        horizontalArrangement = Arrangement.Center,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        amplitudeList.forEach { amp ->
                                            Box(
                                                modifier = Modifier
                                                    .padding(horizontal = 1.dp)
                                                    .width(3.dp)
                                                    .height(40.dp * amp)
                                                    .background(Color(0xFF00F2FE), RoundedCornerShape(1.dp))
                                            )
                                        }
                                    }

                                    Spacer(modifier = Modifier.height(12.dp))

                                    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                                        TextButton(
                                            onClick = { if (isPausedRecording) viewModel.resumeRecording() else viewModel.pauseRecording() }
                                        ) {
                                            Text(if (isPausedRecording) "Resume" else "Pause", color = Color.White, fontWeight = FontWeight.Bold)
                                        }
                                        TextButton(onClick = { viewModel.stopRecording() }) {
                                            Text("Stop", color = Color.Red, fontWeight = FontWeight.Bold)
                                        }
                                    }
                                } else {
                                    // Voice note recorded, preview controls
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                                    ) {
                                        Button(
                                            onClick = { if (isPlayingPreview) viewModel.stopPreview() else viewModel.playPreview() },
                                            colors = ButtonDefaults.buttonColors(
                                                containerColor = Color(0xFF00F2FE).copy(alpha = 0.1f),
                                                contentColor = Color(0xFF00F2FE)
                                            )
                                        ) {
                                            Text(if (isPlayingPreview) "Stop Preview" else "Play Preview", fontWeight = FontWeight.Bold)
                                        }
                                        IconButton(onClick = { viewModel.deleteRecording() }) {
                                            Icon(Icons.Filled.Delete, contentDescription = "Delete", tint = Color.Red)
                                        }
                                    }
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text("Voice note recorded successfully", color = Color(0xFF00F2FE), fontSize = 12.sp)
                                }
                            }
                        }
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            if (composeMode == 0) {
                                if (contentInput.isNotBlank()) {
                                    viewModel.submitJournalEntry(titleInput, contentInput, null)
                                }
                            } else {
                                viewModel.submitVoiceJournal(titleInput)
                            }
                            showComposerDialog = false
                        }
                    ) {
                        Text("Analyze & Commit")
                    }
                },
                dismissButton = {
                    TextButton(onClick = {
                        viewModel.deleteRecording()
                        showComposerDialog = false
                    }) {
                        Text("Cancel")
                    }
                }
            )
        }

        // Recent post analysis sheet response
        if (recentAnalysis != null) {
            AlertDialog(
                onDismissRequest = { viewModel.clearRecentAnalysis() },
                title = { Text("Aura Entry Analytics") },
                text = {
                    Column {
                        Text("Detected Sentiment Emotion: ${recentAnalysis!!.detectedEmotion}", fontWeight = FontWeight.Bold, color = Color(0xFF00F2FE))
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Gratitude points identified:")
                        recentAnalysis!!.gratitudeHighlights.forEach { item ->
                            Text("- $item", color = Color.White.copy(alpha = 0.8f))
                        }
                        if (recentAnalysis!!.triggers.isNotEmpty()) {
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("Stress triggers flags:")
                            recentAnalysis!!.triggers.forEach { item ->
                                Text("- $item", color = Color.Red.copy(alpha = 0.8f))
                            }
                        }
                    }
                },
                confirmButton = {
                    TextButton(onClick = { viewModel.clearRecentAnalysis() }) {
                        Text("Excellent")
                    }
                }
            )
        }
    }
}
