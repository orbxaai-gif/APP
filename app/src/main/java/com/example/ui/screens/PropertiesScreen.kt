package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.model.*
import com.example.ui.components.glassCard
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PropertiesScreen(navController: NavController, viewModel: AppViewModel) {
    val properties by viewModel.filteredProperties.collectAsState(initial = emptyList())
    val searchQuery by viewModel.searchQuery.collectAsState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("add") },
                containerColor = PremiumGold,
                contentColor = DeepBlack
            ) {
                Icon(Icons.Filled.Add, "Add Property")
            }
        },
        containerColor = DeepBlack
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp)
        ) {
            Text("Properties", style = MaterialTheme.typography.headlineMedium, color = TextWhite, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = searchQuery,
                onValueChange = viewModel::onSearch,
                modifier = Modifier.fillMaxWidth().glassCard(),
                placeholder = { Text("Search properties...") },
                leadingIcon = { Icon(Icons.Filled.Search, null, tint = TextMuted) },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    cursorColor = PremiumGold,
                    focusedTextColor = TextWhite,
                    unfocusedTextColor = TextWhite
                )
            )
            Spacer(modifier = Modifier.height(24.dp))
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(properties) { property ->
                    PropertyCardPremium(property) {
                        navController.navigate("details/${property.id}")
                    }
                }
            }
        }
    }
}

@Composable
fun PropertyCardPremium(property: Property, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .glassCard()
            .clickable(onClick = onClick)
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = property.images.firstOrNull(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(100.dp).clip(RoundedCornerShape(12.dp))
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(
                    text = if(property.status == PropertyStatus.FOR_RENT) "$${property.price}/mo" else "$${property.price}",
                    style = MaterialTheme.typography.titleMedium,
                    color = PremiumGold,
                    fontWeight = FontWeight.Bold
                )
                Box(
                    modifier = Modifier
                        .background(if(property.status == PropertyStatus.FOR_SALE) PremiumGold.copy(alpha=0.2f) else ElectricBlue.copy(alpha=0.2f), RoundedCornerShape(4.dp))
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(property.status.getLabel(), color = if(property.status == PropertyStatus.FOR_SALE) PremiumGold else ElectricBlue, style = MaterialTheme.typography.labelSmall)
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(property.title, style = MaterialTheme.typography.bodyLarge, color = TextWhite, fontWeight = FontWeight.Bold, maxLines = 1)
            Spacer(modifier = Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Filled.LocationOn, null, tint = TextMuted, modifier = Modifier.size(14.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text(property.location, color = TextMuted, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}
