package com.roycemars.royalgold.ui.views.charts

import android.annotation.SuppressLint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.copy

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun LineChartPlaceholder(
    modifier: Modifier = Modifier,
    data: List<Float> = emptyList(), // Representing Y values
    lineColor: Color = Color(0xFF7E57C2), // A purplish color like the screenshot
    gradientFill: Brush? = Brush.verticalGradient( // Optional gradient fill
        colors = listOf(lineColor.copy(alpha = 0.3f), Color.Transparent)
    ),
    showYAxisLabels: Boolean = true,
    yAxisLabelColor: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
) {
    if (data.isEmpty()) {
        Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("No chart data", color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        return
    }

    val maxValue = data.maxOrNull() ?: 0f
    val minValue = data.minOrNull() ?: 0f

    BoxWithConstraints(modifier = modifier) {
        val chartHeight = constraints.maxHeight.toFloat()
        val chartWidth = constraints.maxWidth.toFloat()
        val stepX = chartWidth / (data.size - 1).coerceAtLeast(1)

        Canvas(modifier = Modifier.fillMaxSize()) {
            val path = Path()
            data.forEachIndexed { index, value ->
                val x = index * stepX
                val y = chartHeight - ((value - minValue) / (maxValue - minValue).coerceAtLeast(0.001f)) * chartHeight
                if (index == 0) {
                    path.moveTo(x, y.coerceIn(0f, chartHeight))
                } else {
                    path.lineTo(x, y.coerceIn(0f, chartHeight))
                }
            }

            // Optional gradient fill
            gradientFill?.let {
                val fillPath = path.copy() // Create a copy for filling
                fillPath.lineTo(chartWidth, chartHeight)
                fillPath.lineTo(0f, chartHeight)
                fillPath.close()
                drawPath(
                    path = fillPath,
                    brush = it
                )
            }

            // Draw the line
            drawPath(
                path = path,
                color = lineColor,
                style = Stroke(width = 3.dp.toPx(), cap = StrokeCap.Round)
            )

            // Draw a circle at the last data point
            if (data.isNotEmpty()) {
                val lastX = (data.size - 1) * stepX
                val lastY = chartHeight - ((data.last() - minValue) / (maxValue - minValue).coerceAtLeast(0.001f)) * chartHeight
                drawCircle(
                    color = lineColor,
                    radius = 5.dp.toPx(),
                    center = Offset(lastX, lastY.coerceIn(0f, chartHeight))
                )
                drawCircle(
                    color = Color.White.copy(alpha = 0.3f), // Outer glow for the circle
                    radius = 8.dp.toPx(),
                    center = Offset(lastX, lastY.coerceIn(0f, chartHeight))
                )
            }

            // Draw Y-axis labels (simplified)
            if (showYAxisLabels) {
                val topLabel = String.format("$%,.2f", maxValue)
                val bottomLabel = String.format("$%,.2f", minValue)
                // Note: Text drawing on Canvas is more involved for advanced styling.
                // This is a very basic way. For better text, use Text composables outside or Android's StaticLayout.
            }
        }
        // Y-axis labels using Text composables for better control (overlayed)
        if (showYAxisLabels) {
            Text(
                text = String.format("$%,.2f", maxValue),
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(start = 8.dp, top = 4.dp),
                fontSize = 10.sp,
                color = yAxisLabelColor
            )
            Text(
                text = String.format("$%,.2f", minValue),
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(start = 8.dp, bottom = 4.dp),
                fontSize = 10.sp,
                color = yAxisLabelColor
            )
        }
    }
}