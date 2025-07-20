package com.roycemars.royalgold.core.ui.charts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AreaChart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.roycemars.royalgold.core.ui.theme.RoyalGoldTheme
import kotlin.random.Random

/**
 * TODO: Replace with MPAndroidChart
 */

@Composable
fun LineChartCard(
    modifier: Modifier = Modifier,
    assetIcon: ImageVector, // Example: Icons.Filled.CurrencyExchange
    assetName: String,
    currentPrice: String,
    chartData: List<Float>,
    selectedTimeRange: String,
    timeRanges: List<String>,
    onTimeRangeSelected: (String) -> Unit
) {
    ElevatedCard(
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = Color(0xFF2C2B3C) // Dark purple-ish background from screenshot
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Icon
            Icon(
                imageVector = assetIcon,
                contentDescription = "$assetName icon",
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        Color.White.copy(alpha = 0.1f),
                        CircleShape
                    ) // Placeholder for icon background
                    .padding(8.dp),
                tint = Color.White // Or a specific icon tint
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Price
            Text(
                text = currentPrice,
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimaryContainer // Assuming onPrimaryContainer is light on this dark bg
            )

            // Asset Name
            Text(
                text = assetName,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Chart Placeholder
            LineChartPlaceholder(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                data = chartData,
                lineColor = Color(0xFF8A5DFF), // Screenshot's line color
                gradientFill = Brush.verticalGradient(
                    colors = listOf(Color(0xFF8A5DFF).copy(alpha = 0.2f),
                        Color.Transparent)
                ),
                yAxisLabelColor = Color.White.copy(alpha = 0.5f)
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Time Range Selector
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                timeRanges.forEach { range ->
                    TimeRangeButton(
                        text = range,
                        isSelected = range == selectedTimeRange,
                        onClick = { onTimeRangeSelected(range) }
                    )
                }
            }
        }
    }
}

@Composable
fun TimeRangeButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor = if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.15f) else Color.Transparent
    val textColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
    val indicatorColor = if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent

    Button(
        onClick = onClick,
        shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = textColor
        ),
        elevation = ButtonDefaults.buttonElevation(0.dp, 0.dp, 0.dp, 0.dp, 0.dp), // No elevation
        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (text == "LIVE" && isSelected) { // Show dot only for "LIVE" and selected
                Box(
                    modifier = Modifier
                        .size(6.dp)
                        .background(indicatorColor, CircleShape)
                )
                Spacer(modifier = Modifier.width(6.dp))
            }
            Text(text, fontSize = 13.sp, fontWeight = if (isSelected) FontWeight.Medium else FontWeight.Normal)
        }
    }
}

@Composable
fun LineChartCard() {
    var selectedTimeRange by remember { mutableStateOf("1D") }
    val timeRanges = listOf("LIVE", "1D", "7D", "1M", "3M", "6M", "1Y")

    // Dummy data for the chart - replace with actual dynamic data
    val chartDataPoints = remember(selectedTimeRange) {
        // Simulate data changing based on time range
        when (selectedTimeRange) {
            "LIVE" -> List(20) { Random.nextFloat() * 50 + 2500 }
            "1D" -> List(24) { Random.nextFloat() * 100 + 2480 }
            "7D" -> List(7 * 4) { Random.nextFloat() * 150 + 2450 }
            "1M" -> List(30) { Random.nextFloat() * 200 + 2400 }
            else -> List(50) { Random.nextFloat() * 300 + 2300 }
        }
    }

    LineChartCard(
        assetIcon = Icons.Filled.AreaChart,
        assetName = "Ethereum",
        currentPrice = "$2,552.17",
        chartData = chartDataPoints,
        selectedTimeRange = selectedTimeRange,
        timeRanges = timeRanges,
        onTimeRangeSelected = { newRange -> selectedTimeRange = newRange }
    )
}

@Preview(showBackground = true, widthDp = 380)
@Composable
fun CryptoChartCardPreview() {
    RoyalGoldTheme(darkTheme = true) { // Make sure your theme is applied

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF1E1D2B)) // Dark background for the screen
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            LineChartCard()
        }
    }
}