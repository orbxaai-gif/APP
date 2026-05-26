package com.example.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.ui.theme.DeepBlack
import com.example.ui.theme.PremiumGold
import com.example.ui.theme.TextMuted
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

val BrandGold = Color(0xFFC7A252)
val BrandGoldDark = Color(0xFF9E7B35)
val GlassDark = Color(0xFF0A1210).copy(alpha = 0.8f) // Dark greenish tint
val InputBackground = Color(0xFF14171A)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = DeepBlack
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
            // Dark/Greenish Gradient Overlay
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFF051C14).copy(alpha = 0.85f),
                                Color(0xFF02070A).copy(alpha = 0.95f)
                            )
                        )
                    )
            )

        // Main Content Card
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = 24.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(24.dp))
                .border(
                    width = 1.dp,
                    color = BrandGold.copy(alpha = 0.3f),
                    shape = RoundedCornerShape(24.dp)
                )
                .background(GlassDark)
                .padding(horizontal = 32.dp, vertical = 48.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // App Logo Box
            Box(
                modifier = Modifier
                    .size(96.dp)
                    .clip(androidx.compose.foundation.shape.CircleShape)
                    .background(DeepBlack)
                    .border(
                        width = 2.dp,
                        color = BrandGold.copy(alpha = 0.8f),
                        shape = androidx.compose.foundation.shape.CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Filled.Home,
                    contentDescription = "App Logo",
                    modifier = Modifier.size(48.dp),
                    tint = BrandGold
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Title
            Text(
                text = "ORAN ESTATE PORTAL",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.ExtraBold,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "بوابة العقارات وهران",
                color = BrandGold,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.5.sp,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            // Email Address Input
            Column(modifier = Modifier.fillMaxWidth()) {
                Text("Email Address", color = Color.White, fontSize = 12.sp, modifier = Modifier.padding(bottom = 8.dp))
                TextField(
                    value = email,
                    onValueChange = { email = it },
                    placeholder = { Text("investor@estateflow.ai", color = TextMuted) },
                    leadingIcon = { Icon(Icons.Outlined.Email, null, tint = BrandGold) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp)),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = InputBackground,
                        unfocusedContainerColor = InputBackground,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    singleLine = true
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Password Input
            Column(modifier = Modifier.fillMaxWidth()) {
                Text("Secure Password", color = Color.White, fontSize = 12.sp, modifier = Modifier.padding(bottom = 8.dp))
                TextField(
                    value = password,
                    onValueChange = { password = it },
                    placeholder = { Text("••••••••", color = TextMuted) },
                    leadingIcon = { Icon(Icons.Outlined.Lock, null, tint = BrandGold) },
                    trailingIcon = { 
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(Icons.Outlined.Visibility, null, tint = TextMuted)
                        } 
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp)),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = InputBackground,
                        unfocusedContainerColor = InputBackground,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    singleLine = true
                )
            }

            // Forgot Credentials
            Text(
                text = "Forgot Credentials?",
                color = BrandGold,
                fontSize = 12.sp,
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(top = 12.dp, bottom = 24.dp)
                    .clickable { /* Handle forgot password */ }
            )

            // Dynamic Gradient Button
            val gradientBackground = Brush.horizontalGradient(
                colors = listOf(Color(0xFFE5C058), Color(0xFFB58D2B))
            )
            Button(
                onClick = {
                    if (!isLoading) {
                        isLoading = true
                        scope.launch {
                            delay(1000)
                            navController.navigate("main") {
                                popUpTo("login") { inclusive = true }
                            }
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .background(gradientBackground, RoundedCornerShape(12.dp)),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent, // Let the background modifier show
                    contentColor = DeepBlack
                ),
                contentPadding = PaddingValues()
            ) {
                if (isLoading) {
                    CircularProgressIndicator(color = DeepBlack, modifier = Modifier.size(24.dp))
                } else {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Enter Dashboard", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null)
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Divider OR AUTHENTICATE WITH
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                HorizontalDivider(modifier = Modifier.weight(1f), color = Color.White.copy(alpha=0.1f))
                Text(
                    text = "OR AUTHENTICATE WITH",
                    color = TextMuted,
                    fontSize = 10.sp,
                    letterSpacing = 1.sp,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                HorizontalDivider(modifier = Modifier.weight(1f), color = Color.White.copy(alpha=0.1f))
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Google Button
            OutlinedButton(
                onClick = { /* Google Sign In */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = InputBackground, 
                    contentColor = Color.White
                ),
                border = null
            ) {
                // Colored "G" logo simulation
                Text("G", color = BrandGold, fontWeight = FontWeight.ExtraBold, fontSize = 20.sp)
                Spacer(modifier = Modifier.width(12.dp))
                Text("Continue with Google", fontWeight = FontWeight.SemiBold)
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Apply for Access Footer
            Row {
                Text(text = "New investor? ", color = TextMuted, fontSize = 14.sp)
                Text(
                    text = "Apply for Access",
                    color = BrandGold,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable { /* handle navigation */ }
                )
            }
        }
    }
}
}
