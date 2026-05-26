package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.ui.components.glassCard
import com.example.ui.theme.*

@Composable
fun ProfileScreen(navController: NavController, viewModel: AppViewModel) {
    var darkMode by remember { mutableStateOf(true) }
    var notifications by remember { mutableStateOf(true) }

    Scaffold(containerColor = DeepBlack) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(Modifier.height(32.dp))
            Box(modifier = Modifier.size(100.dp).clip(CircleShape).background(PremiumGold), contentAlignment = Alignment.Center) {
                Text("SJ", color = DeepBlack, style = MaterialTheme.typography.displayMedium, fontWeight = FontWeight.Bold)
            }
            Spacer(Modifier.height(16.dp))
            Text("Sarah Jenks", style = MaterialTheme.typography.headlineMedium, color = TextWhite, fontWeight = FontWeight.Bold)
            Text("sarah@orbitestate.com", color = TextMuted)
            
            Spacer(Modifier.height(48.dp))
            
            Column(modifier = Modifier.fillMaxWidth().glassCard().padding(16.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Dark Mode", color = TextWhite, fontWeight = FontWeight.SemiBold)
                    Switch(checked = darkMode, onCheckedChange = { darkMode = it }, colors = SwitchDefaults.colors(checkedThumbColor = PremiumGold, checkedTrackColor = PremiumGold.copy(alpha=0.3f)))
                }
                HorizontalDivider(color = GlassBorder, modifier = Modifier.padding(vertical = 12.dp))
                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Push Notifications", color = TextWhite, fontWeight = FontWeight.SemiBold)
                    Switch(checked = notifications, onCheckedChange = { notifications = it }, colors = SwitchDefaults.colors(checkedThumbColor = ElectricBlue, checkedTrackColor = ElectricBlue.copy(alpha=0.3f)))
                }
                HorizontalDivider(color = GlassBorder, modifier = Modifier.padding(vertical = 12.dp))
                Row(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Filled.Settings, null, tint = TextMuted)
                    Spacer(Modifier.width(16.dp))
                    Text("Language", color = TextWhite, fontWeight = FontWeight.SemiBold)
                    Spacer(Modifier.weight(1f))
                    Text("English", color = TextMuted)
                }
            }
            
            Spacer(Modifier.weight(1f))
            
            Button(
                onClick = {
                    // Requires exposing outer controller or navigating differently if desired.
                    // For now, since they are nested, popping to root of inner can suffice,
                    // or simulated. However to fix the crash, we shouldn't route to missing 'login'.
                },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = ErrorRed.copy(alpha=0.1f), contentColor = ErrorRed)
            ) {
                Icon(Icons.AutoMirrored.Filled.ExitToApp, null)
                Spacer(Modifier.width(8.dp))
                Text("Secure Logout", fontWeight = FontWeight.Bold)
            }
            Spacer(Modifier.height(32.dp))
        }
    }
}
