package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.ui.components.SimpleAreaChart
import com.example.ui.components.SparklineChart
import com.example.ui.components.glassCard
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(navController: NavController, viewModel: AppViewModel) {
    val revenueData = listOf(10f, 25f, 15f, 40f, 35f, 60f, 50f, 80f, 75f, 100f)
    
    LazyColumn(
        modifier = Modifier.fillMaxSize().background(DeepBlack),
        contentPadding = PaddingValues(24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text("Dashboard", style = MaterialTheme.typography.headlineMedium, color = TextWhite, fontWeight = FontWeight.Bold)
                    Text("Welcome back to your command center.", style = MaterialTheme.typography.bodyMedium, color = TextMuted)
                }
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = {}, modifier = Modifier.glassCard(CircleShape)) {
                        Icon(Icons.Filled.Notifications, null, tint = TextWhite)
                    }
                    Box(modifier = Modifier.size(40.dp).clip(CircleShape).background(PremiumGold), contentAlignment = Alignment.Center) {
                        Text("SJ", color = DeepBlack, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        item {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                KPICard(modifier = Modifier.weight(1f), title = "Total Properties", value = "124", change = "+12%", isPositive = true)
                KPICard(modifier = Modifier.weight(1f), title = "Total Revenue", value = "$3.2M", change = "+8.5%", isPositive = true)
            }
        }

        item {
            Column(
                modifier = Modifier.fillMaxWidth().glassCard().padding(24.dp)
            ) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Revenue Overview", style = MaterialTheme.typography.titleMedium, color = TextWhite, fontWeight = FontWeight.Bold)
                    Text("Last 12 Months", style = MaterialTheme.typography.bodySmall, color = TextMuted)
                }
                Spacer(modifier = Modifier.height(24.dp))
                SimpleAreaChart(data = revenueData, color = ElectricBlue, modifier = Modifier.fillMaxWidth().height(150.dp))
            }
        }
        
        item {
            Text("Recent Activity", style = MaterialTheme.typography.titleMedium, color = TextWhite, fontWeight = FontWeight.Bold)
        }
        
        items(3) { index ->
            Row(
                modifier = Modifier.fillMaxWidth().glassCard().padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(modifier = Modifier.size(12.dp).clip(CircleShape).background(if (index == 0) PremiumGold else ElectricBlue))
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(if (index == 0) "New Client Added" else "Property Sold", color = TextWhite, fontWeight = FontWeight.Bold)
                    Text("2 hours ago", color = TextMuted, style = MaterialTheme.typography.labelSmall)
                }
            }
        }
    }
}

@Composable
fun KPICard(modifier: Modifier = Modifier, title: String, value: String, change: String, isPositive: Boolean) {
    Column(
        modifier = modifier.glassCard().padding(20.dp)
    ) {
        Text(title, style = MaterialTheme.typography.bodyMedium, color = TextMuted)
        Spacer(modifier = Modifier.height(8.dp))
        Text(value, style = MaterialTheme.typography.headlineSmall, color = TextWhite, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(change, color = if (isPositive) SuccessGreen else ErrorRed, style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.weight(1f))
            SparklineChart(
                data = listOf(2f, 3f, 2.5f, 5f, 4f, 6f),
                color = if (isPositive) SuccessGreen else ErrorRed,
                modifier = Modifier.width(40.dp).height(20.dp)
            )
        }
    }
}
