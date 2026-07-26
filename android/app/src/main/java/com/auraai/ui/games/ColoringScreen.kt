package com.auraai.ui.games

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.widget.Toast
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.io.File
import java.io.FileOutputStream

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ColoringScreen(
    viewModel: GamesViewModel,
    onBackClick: () -> Unit
) {
    val context = LocalContext.current
    val progressEvent by viewModel.progressEvent.collectAsState()

    val themes = listOf(
        ThemeOutline("Zen Mandala", "🌺"),
        ThemeOutline("Forest Whispers", "🌲"),
        ThemeOutline("Cosmic Moon", "🌙"),
        ThemeOutline("Calm Ocean", "🌊")
    )
    var selectedTheme by remember { mutableStateOf(themes.first()) }

    // Colors
    val palette = listOf(
        Color(0xFF00F2FE), Color(0xFF00FF87), Color(0xFFFFD700),
        Color(0xFFFF5252), Color(0xFF9D4EDD), Color(0xFFFF70A6),
        Color(0xFF4EA8DE), Color(0xFFFFFFFF), Color(0xFF0F0C1B)
    )
    var selectedColor by remember { mutableStateOf(palette.first()) }

    // Canvas Paths lists for Undo/Redo
    val paths = remember { mutableStateListOf<ColoredPath>() }
    val redoPaths = remember { mutableStateListOf<ColoredPath>() }

    var currentPathPoints = remember { mutableStateListOf<Offset>() }
    var brushSize by remember { mutableStateOf(12f) }
    var isEraser by remember { mutableStateOf(false) }

    var showCongratsDialog by remember { mutableStateOf(false) }

    fun clearCanvas() {
        paths.clear()
        redoPaths.clear()
        currentPathPoints.clear()
        viewModel.clearProgressEvent()
    }

    fun exportDrawing() {
        try {
            val bitmap = Bitmap.createBitmap(800, 800, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)
            canvas.drawColor(android.graphics.Color.parseColor("#0F0C1B"))

            // Draw paths onto Bitmap
            val paint = Paint().apply {
                isAntiAlias = true
                strokeCap = Paint.Cap.ROUND
            }

            for (coloredPath in paths) {
                paint.color = if (coloredPath.isEraser) {
                    android.graphics.Color.parseColor("#0F0C1B")
                } else {
                    coloredPath.color.toArgb()
                }
                paint.strokeWidth = coloredPath.width

                for (i in 0 until coloredPath.points.size - 1) {
                    val p1 = coloredPath.points[i]
                    val p2 = coloredPath.points[i + 1]
                    canvas.drawLine(p1.x, p1.y, p2.x, p2.y, paint)
                }
            }

            // Save file
            val fileDir = File(context.cacheDir, "artwork").apply { mkdirs() }
            val file = File(fileDir, "zen_art_${System.currentTimeMillis()}.png")
            FileOutputStream(file).use { out ->
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
            }

            Toast.makeText(context, "Artwork exported successfully!", Toast.LENGTH_SHORT).show()

            // Submit score to reward coins & XP
            viewModel.submitScore("coloring", 25)
            showCongratsDialog = true
        } catch (e: Exception) {
            Toast.makeText(context, "Export failed: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
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
                .padding(16.dp)
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
                    text = "Zen Coloring Canvas",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                IconButton(onClick = { clearCanvas() }) {
                    Icon(Icons.Filled.Delete, contentDescription = "Clear", tint = Color.Red)
                }
            }

            // Outline categories Selector
            LazyRow(
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(themes) { outline ->
                    FilterChip(
                        selected = selectedTheme == outline,
                        onClick = { selectedTheme = outline },
                        label = { Text("${outline.emoji} ${outline.name}") },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = Color(0xFF00F2FE).copy(alpha = 0.2f),
                            selectedLabelColor = Color(0xFF00F2FE)
                        )
                    )
                }
            }

            // Toolbar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White.copy(alpha = 0.03f), RoundedCornerShape(12.dp))
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    IconButton(
                        onClick = {
                            if (paths.isNotEmpty()) {
                                val popped = paths.removeAt(paths.lastIndex)
                                redoPaths.add(popped)
                            }
                        },
                        enabled = paths.isNotEmpty()
                    ) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Undo", tint = if (paths.isNotEmpty()) Color.White else Color.White.copy(alpha = 0.3f))
                    }
                    IconButton(
                        onClick = {
                            if (redoPaths.isNotEmpty()) {
                                val popped = redoPaths.removeAt(redoPaths.lastIndex)
                                paths.add(popped)
                            }
                        },
                        enabled = redoPaths.isNotEmpty()
                    ) {
                        Icon(Icons.Filled.Refresh, contentDescription = "Redo", tint = if (redoPaths.isNotEmpty()) Color.White else Color.White.copy(alpha = 0.3f))
                    }
                }

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                    Text("Brush Size", color = Color.White.copy(alpha = 0.6f), fontSize = 11.sp)
                    Slider(
                        value = brushSize,
                        onValueChange = { brushSize = it },
                        valueRange = 4f..40f,
                        modifier = Modifier.width(100.dp)
                    )
                }

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    IconButton(onClick = { isEraser = false }) {
                        Icon(Icons.Filled.Edit, contentDescription = "Brush", tint = if (!isEraser) Color(0xFF00F2FE) else Color.White)
                    }
                    IconButton(onClick = { isEraser = true }) {
                        Icon(Icons.Filled.Clear, contentDescription = "Eraser", tint = if (isEraser) Color(0xFF00F2FE) else Color.White)
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Main Coloring Canvas Box
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color(0xFF0C091A))
                    .border(1.dp, Color.White.copy(alpha = 0.1f), RoundedCornerShape(20.dp))
                    .pointerInput(Unit) {
                        detectDragGestures(
                            onDragStart = { offset ->
                                currentPathPoints = mutableStateListOf(offset)
                            },
                            onDrag = { change, dragAmount ->
                                change.consume()
                                currentPathPoints.add(change.position)
                            },
                            onDragEnd = {
                                if (currentPathPoints.isNotEmpty()) {
                                    paths.add(
                                        ColoredPath(
                                            points = currentPathPoints.toList(),
                                            color = selectedColor,
                                            width = brushSize,
                                            isEraser = isEraser
                                        )
                                    )
                                    redoPaths.clear()
                                    currentPathPoints = mutableStateListOf()
                                }
                            }
                        )
                    }
            ) {
                // Background outline simulation
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = selectedTheme.emoji,
                        fontSize = 180.sp,
                        modifier = Modifier.background(Color.Transparent)
                    )
                }

                // Drawing Canvas
                Canvas(modifier = Modifier.fillMaxSize()) {
                    // Draw saved paths
                    paths.forEach { coloredPath ->
                        val strokeColor = if (coloredPath.isEraser) Color(0xFF0C091A) else coloredPath.color
                        for (i in 0 until coloredPath.points.size - 1) {
                            drawLine(
                                color = strokeColor,
                                start = coloredPath.points[i],
                                end = coloredPath.points[i + 1],
                                strokeWidth = coloredPath.width,
                                cap = StrokeCap.Round
                            )
                        }
                    }

                    // Draw current path active drag points
                    if (currentPathPoints.size > 1) {
                        val strokeColor = if (isEraser) Color(0xFF0C091A) else selectedColor
                        for (i in 0 until currentPathPoints.size - 1) {
                            drawLine(
                                color = strokeColor,
                                start = currentPathPoints[i],
                                end = currentPathPoints[i + 1],
                                strokeWidth = brushSize,
                                cap = StrokeCap.Round
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Color Palette Selector Row
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(palette) { color ->
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(color)
                            .border(
                                width = if (selectedColor == color && !isEraser) 3.dp else 1.dp,
                                color = if (selectedColor == color && !isEraser) Color(0xFF00F2FE) else Color.White.copy(alpha = 0.2f),
                                shape = CircleShape
                            )
                            .clickable {
                                selectedColor = color
                                isEraser = false
                            }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Complete Challenge & Export Button
            Button(
                onClick = { exportDrawing() },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00F2FE), contentColor = Color.Black),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp)
            ) {
                Icon(Icons.Filled.Done, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Submit Challenge & Export PNG", fontWeight = FontWeight.Bold)
            }
        }

        // Completion Dialog
        if (showCongratsDialog) {
            AlertDialog(
                onDismissRequest = {
                    showCongratsDialog = false
                    clearCanvas()
                },
                title = { Text("Coloring Challenge Complete!") },
                text = {
                    Column {
                        Text("You coloring artwork was saved and registered inside your gallery successfully.")
                        
                        progressEvent?.let { event ->
                            Spacer(modifier = Modifier.height(12.dp))
                            Text("Rewards Earned:", fontWeight = FontWeight.Bold, color = Color(0xFFFFD700))
                            Text("• XP Earned: +${event.xpEarned} XP")
                            Text("• Coins Earned: +${event.coinsEarned} Coins")
                            Text("• Current Wallet Balance: ${event.newBalance} Coins")
                        }
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            showCongratsDialog = false
                            clearCanvas()
                        }
                    ) {
                        Text("Great")
                    }
                }
            )
        }
    }
}

data class ColoredPath(
    val points: List<Offset>,
    val color: Color,
    val width: Float,
    val isEraser: Boolean
)

data class ThemeOutline(
    val name: String,
    val emoji: String
)
