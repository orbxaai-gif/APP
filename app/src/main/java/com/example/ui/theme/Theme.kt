package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val PremiumDarkScheme = darkColorScheme(
    primary = PremiumGold,
    secondary = ElectricBlue,
    background = DeepBlack,
    surface = DarkGray,
    onPrimary = DeepBlack,
    onBackground = TextWhite,
    onSurface = TextWhite,
    surfaceVariant = SoftGray,
    onSurfaceVariant = TextMuted,
    error = ErrorRed
)

@Composable
fun MyApplicationTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = PremiumDarkScheme,
        content = content
    )
}
