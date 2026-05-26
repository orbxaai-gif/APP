package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke

@Composable
fun SparklineChart(data: List<Float>, color: Color, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        if (data.isEmpty() || size.width <= 0f || size.height <= 0f) return@Canvas
        val maxPoint: Float = data.maxOrNull() ?: 1f
        val minPoint: Float = data.minOrNull() ?: 0f
        val range: Float = (maxPoint - minPoint).takeIf { it > 0f } ?: 1f
        
        val stepX = size.width / (data.size - 1).coerceAtLeast(1).toFloat()
        val path = Path()

        
        data.forEachIndexed { index, value ->
            val x = index * stepX
            val y = size.height - ((value - minPoint) / range * size.height)
            if (index == 0) path.moveTo(x, y) else path.lineTo(x, y)
        }
        
        drawPath(
            path = path,
            color = color,
            style = Stroke(width = 4f, cap = StrokeCap.Round, join = androidx.compose.ui.graphics.StrokeJoin.Round)
        )
    }
}

@Composable
fun SimpleAreaChart(data: List<Float>, color: Color, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        if (data.isEmpty() || size.width <= 0f || size.height <= 0f) return@Canvas
        val maxPoint = data.maxOrNull()?.takeIf { it > 0f } ?: 1f
        val stepX = size.width / (data.size - 1).coerceAtLeast(1).toFloat()
        
        val linePath = Path()
        val fillPath = Path()
        
        fillPath.moveTo(0f, size.height)
        
        data.forEachIndexed { index, value ->
            val x = index * stepX
            val y = size.height - (value / maxPoint * size.height)
            if (index == 0) {
                linePath.moveTo(x, y)
                fillPath.lineTo(x, y)
            } else {
                linePath.lineTo(x, y)
                fillPath.lineTo(x, y)
            }
        }
        
        fillPath.lineTo(size.width, size.height)
        fillPath.close()
        
        drawPath(
            path = fillPath,
            brush = Brush.verticalGradient(listOf(color.copy(alpha = 0.3f), Color.Transparent))
        )
        drawPath(
            path = linePath,
            color = color,
            style = Stroke(width = 6f, cap = StrokeCap.Round, join = androidx.compose.ui.graphics.StrokeJoin.Round)
        )
    }
}
