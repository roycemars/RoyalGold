package com.roycemars.royalgold.view.views

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.roycemars.royalgold.view.theme.RoyalGoldTheme

// Define your colors (extract these from the screenshot or your theme)
val OrangeSegment = Color(0xFFFFA726) // Approximate
val BlueSegment = Color(0xFF64B5F6)   // Approximate
val PinkSegment = Color(0xFFF06292)   // Approximate
val PurpleSegment = Color(0xFFBA68C8) // Approximate
val GraySegment = Color(0xFF90A4AE)   // Approximate

data class ProgressSegment(
    val name: String,
    val value: Float,
    val color: Color,
    val icon: ImageVector? = null // Optional icon for the center
)

@Composable
fun SegmentedCircularProgressBar(
    modifier: Modifier = Modifier,
    segments: List<ProgressSegment>,
    strokeWidth: Dp = 20.dp,
    backgroundArcColor: Color = Color.DarkGray.copy(alpha = 0.3f), // Faint background for the track
    totalValue: Float = segments.sumOf { it.value.toDouble() }
        .toFloat(), // Calculate total if not provided
    centerContent: @Composable ColumnScope.() -> Unit
) {
    val totalSweepAngle = 360f
    var startAngle = -90f // Start from the top

    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val diameter = size.minDimension
            val radius = diameter / 2f
            val arcRadius = radius - strokeWidth.toPx() / 2f // Adjust radius for stroke width

            // Draw background track arc
            drawArc(
                color = backgroundArcColor,
                startAngle = -90f,
                sweepAngle = 360f,
                useCenter = false,
                topLeft = Offset(
                    (size.width - 2 * arcRadius) / 2f,
                    (size.height - 2 * arcRadius) / 2f
                ),
                size = Size(arcRadius * 2, arcRadius * 2),
                style = Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Round)
            )

            segments.forEach { segment ->
                val sweepAngle = (segment.value / totalValue) * totalSweepAngle
                drawArc(
                    color = segment.color,
                    startAngle = startAngle,
                    sweepAngle = sweepAngle - 2f, // Small gap between segments
                    useCenter = false,
                    topLeft = Offset(
                        (size.width - 2 * arcRadius) / 2f,
                        (size.height - 2 * arcRadius) / 2f
                    ),
                    size = Size(arcRadius * 2, arcRadius * 2),
                    style = Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Round)
                )
                startAngle += sweepAngle
            }
        }
        // Content in the center of the circle
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            centerContent()
        }
    }
}

@Composable
fun PieChart(modifier: Modifier = Modifier) {
    val segments = listOf(
        ProgressSegment("Housing", 2134f, OrangeSegment, Icons.Filled.Home),
        ProgressSegment("Food & drinks", 891f, BlueSegment),
        ProgressSegment("Entertainment", 497f, PinkSegment),
        ProgressSegment("Transportation", 344f, PurpleSegment),
        ProgressSegment("Miscellaneous", 100f, GraySegment)
    )

    val totalExpenses = segments.sumOf { it.value.toDouble() }.toFloat()
    // Find the segment with the highest value for the center display
    val largestSegment = segments.maxByOrNull { it.value } ?: segments.first()
    val cardBackgroundColor = MaterialTheme.colorScheme.surfaceVariant

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(containerColor = cardBackgroundColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header "EXPENSES"
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "EXPENSES",
                    color = MaterialTheme.colorScheme.onSurfaceVariant, // USE THEME COLOR (or onSurfaceSecondary if defined)
                    style = MaterialTheme.typography.labelSmall, // Use theme typography
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
                Icon(
                    Icons.Filled.ArrowDropDown, // Placeholder, replace with your actual dropdown icon
                    contentDescription = "Filter expenses",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant, // USE THEME COLOR
                    modifier = Modifier.size(18.dp)
                )
            }
            Spacer(modifier = Modifier.height(24.dp))

            SegmentedCircularProgressBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f) // Ensure it's a circle
                    .padding(horizontal = 24.dp), // Padding to not touch card edges
                segments = segments,
                strokeWidth = 22.dp, // Adjust as needed
                totalValue = totalExpenses,
                backgroundArcColor = Color.DarkGray.copy(alpha = 0.1f)
            ) {
                // Center Content
                Box(
                    modifier = Modifier
                        .size(60.dp) // Adjust size of the inner circle icon background
                        .background(
                            MaterialTheme.colorScheme.surfaceVariant, // USE THEME COLOR (was CenterCircleBackground)
                            shape = CircleShape
                        )
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = largestSegment.icon ?: Icons.Filled.Home, // Fallback icon
                        contentDescription = largestSegment.name,
                        tint = largestSegment.color, // Icon color matches the largest segment
                        modifier = Modifier.size(32.dp)
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "$%,.0f".format(largestSegment.value), // Formatted value
                    color = MaterialTheme.colorScheme.onSurface, // USE THEME COLOR (was TextPrimaryDark)
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = largestSegment.name.uppercase(),
                    color = MaterialTheme.colorScheme.onSurfaceVariant, // USE THEME COLOR (was TextSecondaryDark)
                    fontSize = 12.sp,
                    letterSpacing = 0.5.sp
                )
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF1E1E2C)
@Composable
fun PieChartPreview() {
    RoyalGoldTheme(darkTheme = true) { // Explicitly use dark theme for this preview
        PieChart()
    }
}