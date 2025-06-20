package com.roycemars.royalgold.view.views

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.roycemars.royalgold.view.theme.RoyalGoldTheme

// Data class to hold information for each bar
data class BarData(
    val value: Float,
    val label: String,
    val amountText: String,
    val color: Color
)

@Composable
fun BarChart(
    modifier: Modifier = Modifier,
    barDataList: List<BarData>,
    maxBarValue: Float, // The maximum value any bar can represent for scaling
    cardColor: Color = Color(0xFF2C333A), // Dark background color from image
    gridLineColor: Color = Color.Gray.copy(alpha = 0.3f),
    labelTextColor: Color = Color.White,
    amountTextColor: Color = Color.White,
    barCornerRadius: Dp = 12.dp,
    barWidthFraction: Float = 0.6f, // How much of the available space each bar takes
    chartHeight: Dp = 150.dp,
    gridLineCount: Int = 5
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = cardColor)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            // Chart Area
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(chartHeight)
            ) {
                // Background Grid Lines
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
                    val stepSize = size.height / (gridLineCount + 1)
                    for (i in 1..gridLineCount) {
                        drawLine(
                            color = gridLineColor,
                            start = Offset(0f, stepSize * i),
                            end = Offset(size.width, stepSize * i),
                            strokeWidth = 1.dp.toPx(),
                            pathEffect = pathEffect
                        )
                    }
                }

                // Bars
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.Bottom // Align bars to the bottom
                ) {
//                    val barSlotWidth = size.width / barDataList.size
//                    val actualBarWidth = barSlotWidth * barWidthFraction

                    barDataList.forEach { data ->
//                        val barHeight = (data.value / maxBarValue) * chartHeight.toPx()
                        Canvas(
                            modifier = Modifier.fillMaxSize() // Convert Px to Dp for Modifier
                                .height(chartHeight) // Canvas takes full chart height
                        ) {
                            // 'size' here is the size of this specific Canvas.
                            val canvasWidth = size.width
                            val actualBarDrawingWidth = canvasWidth * barWidthFraction
                            val barHeightPx = (data.value / maxBarValue) * size.height // Use Canvas's height

                            drawRoundRect(
                                color = data.color,
                                topLeft = Offset(
                                    x = (size.width - actualBarDrawingWidth) / 2, // Center the bar
                                    y = size.height - barHeightPx // Draw from bottom up
                                ),
                                size = Size(actualBarDrawingWidth, barHeightPx),
                                cornerRadius = CornerRadius(barCornerRadius.toPx())
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Labels and Amounts
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                barDataList.forEach { data ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.weight(1f) // Distribute space equally
                    ) {
                        Text(
                            text = data.amountText,
                            color = amountTextColor,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = data.label.uppercase(),
                            color = labelTextColor.copy(alpha = 0.7f), // Slightly dimmer label
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Medium,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun BarChart() {
    val sampleData = listOf(
        BarData(
            value = 4593f,
            label = "INCOME",
            amountText = "$4,593",
            color = Color(0xFF49C6B2) // Teal
        ),
        BarData(
            value = 4466f,
            label = "EXPENSES",
            amountText = "$4,466",
            color = Color(0xFFF789BA) // Pink
        ),
        BarData(
            value = 127f,
            label = "LEFT",
            amountText = "$127",
            color = Color(0xFFAC6FF1) // Purple
        )
    )
    val maxVal = sampleData.maxOfOrNull { it.value } ?: 5000f // Determine max for scaling

    BarChart(barDataList = sampleData, maxBarValue = maxVal)
}

@Preview(showBackground = true, backgroundColor = 0xFF1E1E1E)
@Composable
fun BarChartPreview() {
    RoyalGoldTheme(darkTheme = true) {
        BarChart()
    }
}