package com.auraai.ui.contacts

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.auraai.domain.model.Contact

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SupportContactsScreen(
    viewModel: ContactsViewModel,
    onBackClick: () -> Unit
) {
    val context = LocalContext.current
    val contacts by viewModel.contacts.collectAsState()
    val deviceContacts by viewModel.deviceContacts.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    var showAddDialog by remember { mutableStateOf(false) }
    var showImportDialog by remember { mutableStateOf(false) }

    var nameInput by remember { mutableStateOf("") }
    var phoneInput by remember { mutableStateOf("") }
    var emailInput by remember { mutableStateOf("") }
    var relationshipInput by remember { mutableStateOf("Friend") }
    var isFavoriteInput by remember { mutableStateOf(false) }
    var isEmergencyInput by remember { mutableStateOf(false) }

    var editingContactId by remember { mutableStateOf<String?>(null) }
    var importSearchQuery by remember { mutableStateOf("") }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            viewModel.loadDeviceContacts(context)
            showImportDialog = true
        }
    }

    LaunchedEffect(Unit) {
        viewModel.loadContacts()
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
                    text = "My Support Circle",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Row {
                    IconButton(onClick = {
                        val permission = Manifest.permission.READ_CONTACTS
                        if (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED) {
                            viewModel.loadDeviceContacts(context)
                            showImportDialog = true
                        } else {
                            permissionLauncher.launch(permission)
                        }
                    }) {
                        Icon(Icons.Filled.Person, contentDescription = "Import System Contact", tint = Color(0xFF00F2FE))
                    }
                    IconButton(onClick = {
                        nameInput = ""
                        phoneInput = ""
                        emailInput = ""
                        relationshipInput = "Friend"
                        isFavoriteInput = false
                        isEmergencyInput = false
                        editingContactId = null
                        showAddDialog = true
                    }) {
                        Icon(Icons.Filled.Add, contentDescription = "Add Contact", tint = Color(0xFF00F2FE))
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Emergency alert banner if emergency contacts exist
            val emergencies = contacts.filter { it.isEmergency }
            if (emergencies.isNotEmpty()) {
                val firstEmergency = emergencies.first()
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color(0x22FF5252))
                        .clickable {
                            val intent = Intent(Intent.ACTION_DIAL).apply {
                                data = Uri.parse("tel:${firstEmergency.phone}")
                            }
                            context.startActivity(intent)
                        }
                        .padding(16.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Filled.Warning, contentDescription = null, tint = Color(0xFFFF5252), modifier = Modifier.size(32.dp))
                        Spacer(modifier = Modifier.width(16.dp))
                        Column {
                            Text("Emergency Support Alert", color = Color(0xFFFF5252), fontWeight = FontWeight.Bold, fontSize = 15.sp)
                            Text("Tap to call ${firstEmergency.name} immediately.", color = Color.White.copy(alpha = 0.8f), fontSize = 13.sp)
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            if (isLoading) {
                Box(modifier = Modifier.fillMaxWidth().weight(1f), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Color(0xFF00F2FE))
                }
            } else if (contacts.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Filled.Person, contentDescription = null, tint = Color.White.copy(alpha = 0.2f), modifier = Modifier.size(64.dp))
                        Spacer(modifier = Modifier.height(12.dp))
                        Text("No contacts in your support circle yet.", color = Color.White.copy(alpha = 0.5f), fontSize = 14.sp)
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(contacts) { contact ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(16.dp))
                                .background(Color.White.copy(alpha = 0.03f))
                                .clickable {
                                    nameInput = contact.name
                                    phoneInput = contact.phone
                                    emailInput = contact.email ?: ""
                                    relationshipInput = contact.relationshipLabel
                                    isFavoriteInput = contact.isFavorite
                                    isEmergencyInput = contact.isEmergency
                                    editingContactId = contact.contactId
                                    showAddDialog = true
                                }
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(45.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(
                                        if (contact.isEmergency) Color(0xFFFF5252)
                                        else Color.White.copy(alpha = 0.1f)
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = contact.name.take(1).uppercase(),
                                    color = Color.White,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            Spacer(modifier = Modifier.width(16.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(contact.name, color = Color.White, fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
                                    if (contact.isFavorite) {
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Icon(Icons.Filled.Star, contentDescription = null, tint = Color(0xFFFFD700), modifier = Modifier.size(16.dp))
                                    }
                                }
                                Text(
                                    text = "${contact.relationshipLabel} • ${contact.phone}",
                                    color = Color.White.copy(alpha = 0.5f),
                                    fontSize = 13.sp
                                )
                            }

                            Row {
                                IconButton(onClick = {
                                    val intent = Intent(Intent.ACTION_DIAL).apply {
                                        data = Uri.parse("tel:${contact.phone}")
                                    }
                                    context.startActivity(intent)
                                }) {
                                    Icon(Icons.Filled.Phone, contentDescription = "Call", tint = Color(0xFF00F2FE))
                                }
                                IconButton(onClick = {
                                    val intent = Intent(Intent.ACTION_SENDTO).apply {
                                        data = Uri.parse("smsto:${contact.phone}")
                                    }
                                    context.startActivity(intent)
                                }) {
                                    Icon(Icons.Filled.Email, contentDescription = "SMS", tint = Color(0xFFFFD700))
                                }
                                IconButton(onClick = { viewModel.toggleFavorite(contact.contactId) }) {
                                    Icon(
                                        imageVector = if (contact.isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                                        contentDescription = "Favorite",
                                        tint = if (contact.isFavorite) Color.Red else Color.White.copy(alpha = 0.5f)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // Add/Edit Dialog
        if (showAddDialog) {
            AlertDialog(
                onDismissRequest = { showAddDialog = false },
                title = { Text(if (editingContactId != null) "Edit Contact" else "Add Support Contact") },
                text = {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        TextField(
                            value = nameInput,
                            onValueChange = { nameInput = it },
                            label = { Text("Name") }
                        )
                        TextField(
                            value = phoneInput,
                            onValueChange = { phoneInput = it },
                            label = { Text("Phone Number") }
                        )
                        TextField(
                            value = emailInput,
                            onValueChange = { emailInput = it },
                            label = { Text("Email (Optional)") }
                        )
                        TextField(
                            value = relationshipInput,
                            onValueChange = { relationshipInput = it },
                            label = { Text("Relationship (e.g. Friend, Therapist)") }
                        )
                        
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Checkbox(checked = isFavoriteInput, onCheckedChange = { isFavoriteInput = it })
                            Text("Favorite Contact")
                        }
                        
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Checkbox(checked = isEmergencyInput, onCheckedChange = { isEmergencyInput = it })
                            Text("Emergency Contact")
                        }
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            if (editingContactId != null) {
                                viewModel.editContact(editingContactId!!, nameInput, phoneInput, emailInput.takeIf { it.isNotBlank() }, relationshipInput, isFavoriteInput, isEmergencyInput)
                            } else {
                                viewModel.addContact(nameInput, phoneInput, emailInput.takeIf { it.isNotBlank() }, relationshipInput, isFavoriteInput, isEmergencyInput)
                            }
                            showAddDialog = false
                        }
                    ) {
                        Text("Save")
                    }
                },
                dismissButton = {
                    Row {
                        if (editingContactId != null) {
                            TextButton(
                                onClick = {
                                    viewModel.removeContact(editingContactId!!)
                                    showAddDialog = false
                                },
                                colors = ButtonDefaults.textButtonColors(contentColor = Color.Red)
                            ) {
                                Text("Delete")
                            }
                        }
                        TextButton(onClick = { showAddDialog = false }) {
                            Text("Cancel")
                        }
                    }
                }
            )
        }

        // Import Native System Contacts Dialog
        if (showImportDialog) {
            val filtered = deviceContacts.filter {
                it.name.contains(importSearchQuery, ignoreCase = true) ||
                        it.phone.contains(importSearchQuery, ignoreCase = true)
            }

            AlertDialog(
                onDismissRequest = { showImportDialog = false },
                title = { Text("Import from Device Contacts") },
                text = {
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        TextField(
                            value = importSearchQuery,
                            onValueChange = { importSearchQuery = it },
                            label = { Text("Search Native Contacts") },
                            modifier = Modifier.fillMaxWidth(),
                            leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) }
                        )

                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(250.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(filtered) { devC ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(Color.White.copy(alpha = 0.02f), RoundedCornerShape(8.dp))
                                        .clickable {
                                            nameInput = devC.name
                                            phoneInput = devC.phone
                                            emailInput = ""
                                            relationshipInput = "Friend"
                                            isFavoriteInput = false
                                            isEmergencyInput = false
                                            editingContactId = null
                                            showImportDialog = false
                                            showAddDialog = true
                                        }
                                        .padding(12.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(Icons.Filled.Person, contentDescription = null, tint = Color(0xFF00F2FE), modifier = Modifier.size(24.dp))
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Column {
                                        Text(devC.name, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                        Text(devC.phone, color = Color.White.copy(alpha = 0.5f), fontSize = 12.sp)
                                    }
                                }
                            }
                        }
                    }
                },
                confirmButton = {},
                dismissButton = {
                    TextButton(onClick = { showImportDialog = false }) {
                        Text("Close")
                    }
                }
            )
        }
    }
}
