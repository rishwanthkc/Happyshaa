package com.auraai.ui.story

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StoryGeneratorScreen(
    viewModel: StoryGeneratorViewModel,
    uid: String = "test_user_id", // Default fallback if needed
    onBackClick: () -> Unit
) {
    val storyText by viewModel.storyText.collectAsState()
    val isGenerating by viewModel.isGenerating.collectAsState()
    val isLoadingHistory by viewModel.isLoadingHistory.collectAsState()
    val storiesHistory by viewModel.storiesHistory.collectAsState()
    val isSpeaking by viewModel.isSpeaking.collectAsState()
    val context = LocalContext.current

    var selectedCategory by remember { mutableStateOf("Sleep") }
    var selectedLength by remember { mutableStateOf("Short") }

    val categories = listOf("Sleep", "Motivation", "Anxiety Relief", "Happiness", "Self Confidence")
    val lengths = listOf("Short", "Medium", "Long")

    LaunchedEffect(uid) {
        viewModel.loadHistory(uid)
    }

    val bgGradient = Brush.verticalGradient(
        colors = listOf(Color(0xFF0F0C20), Color(0xFF1B1437), Color(0xFF0B0914))
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
                IconButton(onClick = {
                    viewModel.stopSpeaking()
                    onBackClick()
                }) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                }
                Text(
                    text = "Aura Story Generator",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Box(modifier = Modifier.size(48.dp)) // Spacer to keep title centered
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Main Editor Section (takes top space) or Story result
            if (storyText.isNotBlank() || isGenerating) {
                // Story result view
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.03f)),
                    shape = RoundedCornerShape(24.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(20.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "$selectedCategory Story ($selectedLength)",
                                color = Color(0xFF00F2FE),
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                            Row {
                                IconButton(onClick = { viewModel.speakStory(storyText) }) {
                                    Icon(
                                        imageVector = Icons.Filled.Notifications,
                                        contentDescription = "Read Aloud",
                                        tint = if (isSpeaking) Color(0xFF00F2FE) else Color.White
                                    )
                                }
                                IconButton(onClick = {
                                    val sendIntent: Intent = Intent().apply {
                                        action = Intent.ACTION_SEND
                                        putExtra(Intent.EXTRA_TEXT, storyText)
                                        type = "text/plain"
                                    }
                                    val shareIntent = Intent.createChooser(sendIntent, null)
                                    context.startActivity(shareIntent)
                                }) {
                                    Icon(Icons.Filled.Share, contentDescription = "Share", tint = Color.White)
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth()
                                .verticalScroll(rememberScrollState())
                        ) {
                            Text(
                                text = storyText.ifBlank { "Tuning calm frequency..." },
                                color = Color.White.copy(alpha = 0.85f),
                                fontSize = 15.sp,
                                lineHeight = 24.sp
                            )
                        }

                        if (isGenerating) {
                            Spacer(modifier = Modifier.height(8.dp))
                            LinearProgressIndicator(
                                color = Color(0xFF00F2FE),
                                modifier = Modifier.fillMaxWidth()
                            )
                        } else {
                            Button(
                                onClick = { viewModel.generateStory(selectedCategory, selectedLength) },
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00F2FE), contentColor = Color.Black),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("Generate Another Story", fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            } else {
                // Setup & Generator Configuration options
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
                            Text("Choose Category", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                            Spacer(modifier = Modifier.height(8.dp))
                            // Chips Row
                            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    categories.take(3).forEach { cat ->
                                        val isSelected = selectedCategory == cat
                                        Box(
                                            modifier = Modifier
                                                .clip(RoundedCornerShape(16.dp))
                                                .background(if (isSelected) Color(0xFF00F2FE) else Color.White.copy(alpha = 0.05f))
                                                .clickable { selectedCategory = cat }
                                                .padding(horizontal = 12.dp, vertical = 6.dp)
                                        ) {
                                            Text(
                                                text = cat,
                                                color = if (isSelected) Color.Black else Color.White,
                                                fontSize = 13.sp
                                            )
                                        }
                                    }
                                }
                                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    categories.drop(3).forEach { cat ->
                                        val isSelected = selectedCategory == cat
                                        Box(
                                            modifier = Modifier
                                                .clip(RoundedCornerShape(16.dp))
                                                .background(if (isSelected) Color(0xFF00F2FE) else Color.White.copy(alpha = 0.05f))
                                                .clickable { selectedCategory = cat }
                                                .padding(horizontal = 12.dp, vertical = 6.dp)
                                        ) {
                                            Text(
                                                text = cat,
                                                color = if (isSelected) Color.Black else Color.White,
                                                fontSize = 13.sp
                                            )
                                        }
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(20.dp))

                            Text("Story Length", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                lengths.forEach { len ->
                                    val isSelected = selectedLength == len
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(16.dp))
                                            .background(if (isSelected) Color(0xFF00F2FE) else Color.White.copy(alpha = 0.05f))
                                            .clickable { selectedLength = len }
                                            .padding(horizontal = 16.dp, vertical = 6.dp)
                                    ) {
                                        Text(
                                            text = len,
                                            color = if (isSelected) Color.Black else Color.White,
                                            fontSize = 13.sp
                                        )
                                    }
                                }
                            }
                        }

                        Button(
                            onClick = { viewModel.generateStory(selectedCategory, selectedLength) },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00F2FE), contentColor = Color.Black),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                        ) {
                            Icon(Icons.Filled.Star, contentDescription = null)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Generate Calming Story", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // History Log section
            Text("Story History Log", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))

            if (isLoadingHistory) {
                Box(modifier = Modifier.fillMaxWidth().weight(1f), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Color(0xFF00F2FE))
                }
            } else if (storiesHistory.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color.White.copy(alpha = 0.03f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No stories generated yet.", color = Color.White.copy(alpha = 0.5f), fontSize = 13.sp)
                }
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(storiesHistory) { item ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(16.dp))
                                .background(Color.White.copy(alpha = 0.03f))
                                .clickable {
                                    // Clicking sets history text to result viewer
                                    viewModel.speakStory("") // stop any speech
                                    selectedCategory = item.category
                                    selectedLength = item.length
                                    viewModel.generateStory(item.category, item.length) // regenerate or load
                                }
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
                                Icon(Icons.Filled.List, contentDescription = null, tint = Color(0xFF00F2FE))
                            }

                            Spacer(modifier = Modifier.width(16.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Text(item.title, color = Color.White, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                                Text("${item.category} • ${item.length}", color = Color.White.copy(alpha = 0.5f), fontSize = 12.sp)
                            }

                            IconButton(onClick = { viewModel.toggleFavorite(item) }) {
                                Icon(
                                    imageVector = if (item.isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                                    contentDescription = "Favorite",
                                    tint = if (item.isFavorite) Color.Red else Color.White.copy(alpha = 0.4f)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
