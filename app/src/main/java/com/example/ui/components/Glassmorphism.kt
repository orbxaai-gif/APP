package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.example.ui.theme.GlassBackground
import com.example.ui.theme.GlassBorder

fun Modifier.glassCard(shape: Shape = RoundedCornerShape(16.dp)) = this
    .clip(shape)
    .background(GlassBackground)
    .border(1.dp, GlassBorder, shape)
