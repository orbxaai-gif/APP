package com.example.ui.components

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.ui.theme.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun AIAssistantPanel(onDismiss: () -> Unit) {
    var query by remember { mutableStateOf("") }
    var response by remember { mutableStateOf("") }
    var isTyping by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
            .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
            .background(DarkGray)
            .padding(24.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.size(12.dp).clip(CircleShape).background(ElectricBlue))
                Spacer(modifier = Modifier.width(12.dp))
                Text("AI Assistant", color = TextWhite, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
            }
            IconButton(onClick = onDismiss) {
                Icon(Icons.Filled.Close, null, tint = TextMuted)
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Box(modifier = Modifier.weight(1f).fillMaxWidth().glassCard().padding(16.dp)) {
            if (response.isNotEmpty()) {
                Text(response, color = TextWhite)
            } else if (isTyping) {
                CircularProgressIndicator(color = ElectricBlue, modifier = Modifier.align(Alignment.Center).size(24.dp))
            } else {
                Text("How can I help you today? Try: 'Write a description for a sea view villa'", color = TextMuted, style = MaterialTheme.typography.bodyMedium)
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                value = query,
                onValueChange = { query = it },
                placeholder = { Text("Ask AI...") },
                modifier = Modifier.weight(1f).glassCard(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    focusedTextColor = TextWhite,
                    unfocusedTextColor = TextWhite
                )
            )
            Spacer(modifier = Modifier.width(16.dp))
            IconButton(
                onClick = {
                    if (query.isNotEmpty()) {
                        isTyping = true
                        response = ""
                        scope.launch {
                            delay(1500) // Artificial delay
                            response = "Here's a premium description:\n\"Experience coastal elegance in this breathtaking sea view villa. Featuring floor-to-ceiling windows and artisanal finishes, this sanctuary redefines modern luxury living.\""
                            isTyping = false
                        }
                    }
                },
                modifier = Modifier.size(56.dp).clip(CircleShape).background(ElectricBlue)
            ) {
                Icon(Icons.AutoMirrored.Filled.Send, null, tint = DeepBlack)
            }
        }
    }
}
