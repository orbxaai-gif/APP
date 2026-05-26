package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.model.*
import com.example.ui.components.glassCard
import com.example.ui.theme.*

@Composable
fun ClientsScreen(navController: NavController, viewModel: AppViewModel) {
    val clients by viewModel.clients.collectAsState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { }, containerColor = PremiumGold, contentColor = DeepBlack) {
                Icon(Icons.Filled.Add, "Add Client")
            }
        },
        containerColor = DeepBlack
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp)) {
            Text("Client CRM", style = MaterialTheme.typography.headlineMedium, color = TextWhite, fontWeight = FontWeight.Bold)
            Text("Manage your leads and active deals.", color = TextMuted, style = MaterialTheme.typography.bodyMedium)
            
            Spacer(modifier = Modifier.height(24.dp))
            
            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(clients) { client ->
                    ClientRow(client)
                }
            }
        }
    }
}

@Composable
fun ClientRow(client: Client) {
    Row(
        modifier = Modifier.fillMaxWidth().glassCard().padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = client.avatarUrl,
            contentDescription = null,
            modifier = Modifier.size(48.dp).clip(CircleShape)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(client.name, color = TextWhite, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
            Text(client.phone, color = TextMuted, style = MaterialTheme.typography.bodySmall)
        }
        val statusColor = when(client.status) {
            ClientStatus.ACTIVE -> SuccessGreen
            ClientStatus.POTENTIAL -> ElectricBlue
            ClientStatus.CLOSED -> TextMuted
        }
        Box(modifier = Modifier.background(statusColor.copy(alpha=0.2f), RoundedCornerShape(4.dp)).padding(horizontal = 8.dp, vertical = 4.dp)) {
            Text(client.status.name, color = statusColor, style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
        }
    }
}
