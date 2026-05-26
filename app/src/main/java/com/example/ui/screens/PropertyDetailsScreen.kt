package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.ui.components.glassCard
import com.example.ui.theme.*

@Composable
fun PropertyDetailsScreen(propertyId: String?, navController: NavController, viewModel: AppViewModel) {
    val allProps by viewModel.properties.collectAsState()
    val property = allProps.find { it.id == propertyId }

    if (property == null) {
        Box(modifier = Modifier.fillMaxSize().background(DeepBlack), contentAlignment = Alignment.Center) {
            Text("Property not found", color = TextMuted)
        }
        return
    }

    Scaffold(
        containerColor = DeepBlack,
        bottomBar = {
            Surface(color = DarkGray, modifier = Modifier.fillMaxWidth()) {
                Row(modifier = Modifier.padding(16.dp), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    Button(onClick = {}, modifier = Modifier.weight(1f).height(50.dp), colors = ButtonDefaults.buttonColors(containerColor = PremiumGold, contentColor = DeepBlack)) {
                        Text("Book Visit", fontWeight = FontWeight.Bold)
                    }
                    Button(onClick = {}, modifier = Modifier.weight(1f).height(50.dp), colors = ButtonDefaults.buttonColors(containerColor = ElectricBlue, contentColor = TextWhite)) {
                        Icon(Icons.Filled.Phone, null)
                        Spacer(Modifier.width(8.dp))
                        Text("Contact", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.fillMaxSize().padding(paddingValues).verticalScroll(rememberScrollState())
        ) {
            Box(modifier = Modifier.fillMaxWidth().height(350.dp)) {
                AsyncImage(
                    model = property.images.firstOrNull(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                IconButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier.padding(16.dp).glassCard().align(Alignment.TopStart)
                ) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back", tint = TextWhite)
                }
            }
            
            Column(modifier = Modifier.padding(24.dp)) {
                Text(
                    text = "$${property.price.toInt()}",
                    style = MaterialTheme.typography.displaySmall,
                    color = PremiumGold,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(property.title, style = MaterialTheme.typography.headlineSmall, color = TextWhite, fontWeight = FontWeight.Bold)
                Text(property.location, style = MaterialTheme.typography.bodyLarge, color = TextMuted)
                
                Spacer(modifier = Modifier.height(24.dp))
                
                Row(modifier = Modifier.fillMaxWidth().glassCard().padding(16.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                    InfoStat(title = "Area", value = "${property.area} sqft")
                    InfoStat(title = "Rooms", value = "${property.rooms}")
                    InfoStat(title = "Baths", value = "${property.bathrooms}")
                }
                
                Spacer(modifier = Modifier.height(24.dp))
                
                Text("Description", style = MaterialTheme.typography.titleMedium, color = TextWhite, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Text(property.description, color = TextMuted, style = MaterialTheme.typography.bodyMedium, lineHeight = androidx.compose.ui.unit.TextUnit(24f, androidx.compose.ui.unit.TextUnitType.Sp))
            }
        }
    }
}

@Composable
fun InfoStat(title: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(title, color = TextMuted, style = MaterialTheme.typography.labelMedium)
        Spacer(Modifier.height(4.dp))
        Text(value, color = TextWhite, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
    }
}
