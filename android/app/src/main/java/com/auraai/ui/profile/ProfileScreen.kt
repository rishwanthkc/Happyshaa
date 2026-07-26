package com.auraai.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Face
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
import com.auraai.domain.model.User
import com.auraai.ui.theme.LightBg

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    user: User,
    coins: Int,
    themeMode: String,
    onThemeChange: (String) -> Unit,
    onBackClick: () -> Unit
) {
    val isLight = MaterialTheme.colorScheme.background == LightBg
    
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

    val onBgColor = MaterialTheme.colorScheme.onBackground
    val cardContainerBg = if (isLight) Color.Black.copy(alpha = 0.03f) else Color.White.copy(alpha = 0.03f)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Profile & Settings", color = onBgColor, fontWeight = FontWeight.Bold, fontSize = 20.sp) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back", tint = onBgColor)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        },
        containerColor = Color.Transparent
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(bgGradient)
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // User Avatar Card
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(RoundedCornerShape(50.dp))
                        .background(Color(0xFF00F2FE).copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = (user.displayName ?: "A").substring(0, 1).uppercase(),
                        color = Color(0xFF00F2FE),
                        fontSize = 36.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = user.displayName ?: "Happyshaa Member",
                    color = onBgColor,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = user.email,
                    color = onBgColor.copy(alpha = 0.6f),
                    fontSize = 14.sp
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Stats row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Card(
                        modifier = Modifier.weight(1f),
                        colors = CardDefaults.cardColors(containerColor = cardContainerBg)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(Icons.Filled.Star, contentDescription = null, tint = Color(0xFFFFD700))
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("Happyshaa Coins", color = onBgColor.copy(alpha = 0.6f), fontSize = 11.sp)
                            Text("$coins 🪙", color = onBgColor, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        }
                    }

                    Card(
                        modifier = Modifier.weight(1f),
                        colors = CardDefaults.cardColors(containerColor = cardContainerBg)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(Icons.Filled.Face, contentDescription = null, tint = Color(0xFF00F2FE))
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("Happyshaa Level", color = onBgColor.copy(alpha = 0.6f), fontSize = 11.sp)
                            Text("Level 1", color = onBgColor, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Theme Settings Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = cardContainerBg),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Text(
                            text = "Display Theme Settings",
                            color = onBgColor,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                        Text(
                            text = "Choose light, dark, or system default setting",
                            color = onBgColor.copy(alpha = 0.6f),
                            fontSize = 12.sp
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            listOf("LIGHT", "DARK", "SYSTEM").forEach { mode ->
                                val active = themeMode == mode
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .clip(RoundedCornerShape(10.dp))
                                        .background(if (active) Color(0xFF00F2FE) else onBgColor.copy(alpha = 0.05f))
                                        .clickable { onThemeChange(mode) }
                                        .padding(vertical = 12.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = mode,
                                        color = if (active) Color.Black else onBgColor,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.sp
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
