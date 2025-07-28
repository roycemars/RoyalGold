package com.roycemars.royalgold.core.ui.charts // Or your actual package

import android.content.res.Configuration
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.isSystemInDarkTheme // Needed if you want to check system theme directly
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
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
import com.roycemars.royalgold.core.ui.theme.RoyalGoldTheme

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
    maxBarValue: Float,
    barCornerRadius: Dp = 12.dp,
    barWidthFraction: Float = 0.6f,
    chartHeight: Dp = 250.dp,
    gridLineCount: Int = 5,
    darkTheme: Boolean = isSystemInDarkTheme()
) {
    val contentColor: Color = MaterialTheme.colorScheme.primary
    val gridLineColorForChart: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
    val labelTextColor = contentColor
    val amountTextColor = contentColor

    ElevatedCard(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(chartHeight)
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
                    val stepSize = size.height / (gridLineCount + 1)
                    for (i in 1..gridLineCount) {
                        drawLine(
                            color = gridLineColorForChart,
                            start = Offset(0f, stepSize * i),
                            end = Offset(size.width, stepSize * i),
                            strokeWidth = 1.dp.toPx(),
                            pathEffect = pathEffect
                        )
                    }
                }

                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.Bottom
                ) {
                    barDataList.forEach { data ->
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                        ) {
                            Canvas(modifier = Modifier.fillMaxSize()) {
                                val canvasWidth = size.width
                                val actualBarDrawingWidth = canvasWidth * barWidthFraction
                                val barHeightPx = (data.value / maxBarValue).coerceAtMost(1f) * size.height
                                drawRoundRect(
                                    color = data.color,
                                    topLeft = Offset(
                                        x = (canvasWidth - actualBarDrawingWidth) / 2,
                                        y = size.height - barHeightPx
                                    ),
                                    size = Size(actualBarDrawingWidth, barHeightPx),
                                    cornerRadius = CornerRadius(barCornerRadius.toPx())
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                barDataList.forEach { data ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = data.amountText,
                            color = amountTextColor,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            text = data.label.uppercase(),
                            color = labelTextColor.copy(alpha = 0.7f),
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Medium,
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun BarChart(darkTheme: Boolean = isSystemInDarkTheme()) { // Pass darkTheme to the sample
    val sampleData = listOf(
        BarData(4593f, "INCOME", "$4,593", Color(0xFF49C6B2)),
        BarData(4466f, "EXPENSES", "$4,466", Color(0xFFF789BA)),
        BarData(127f, "LEFT", "$127", Color(0xFFAC6FF1))
    )
    val maxVal = (sampleData.maxOfOrNull { it.value } ?: 1f) * 1.1f
    BarChart(
        barDataList = sampleData,
        maxBarValue = maxVal.coerceAtLeast(1f),
        darkTheme = darkTheme // Pass the theme state
    )
}

@Preview
@Composable
fun BarChartPreviewLight() {
    RoyalGoldTheme(darkTheme = false,) {
        BarChart(darkTheme = false)
    }
}

@Preview
@Composable
fun BarChartPreviewDark() {
    RoyalGoldTheme(darkTheme = true) {
        BarChart(darkTheme = true)
    }
}