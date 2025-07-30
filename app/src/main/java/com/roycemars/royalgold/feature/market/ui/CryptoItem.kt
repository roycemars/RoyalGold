package com.roycemars.royalgold.feature.market.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.roycemars.royalgold.core.ui.theme.RoyalGoldTheme
import com.roycemars.royalgold.core.ui.theme.tickerColors
import com.roycemars.royalgold.feature.market.domain.Crypto
import java.util.Calendar
import kotlin.String
import kotlin.text.format

@SuppressLint("DefaultLocale")
@Composable
fun CryptoItem(
    crypto: Crypto,
    modifier: Modifier = Modifier
) {
    ElevatedCard(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(crypto.symbol, fontWeight = FontWeight.Bold)
            Text(text = String.format("%.2f", crypto.price), fontWeight = FontWeight.Bold)
            Ticker(crypto.percentChange1h)
            Ticker(crypto.percentChange24h)
        }
    }
}

@Composable
fun HeaderCard(modifier: Modifier = Modifier) {
    ElevatedCard(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Symbol", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
            Text("Price", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
            Text("1h", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
            Text("24h", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
        }
    }
}

@SuppressLint("DefaultLocale")
@Composable
fun Ticker(inPercentChange: Double?) {
    val percentChange = inPercentChange ?: 0.0 // Handle null

    val tickerColors = tickerColors()
    val backgroundColor = when {
        percentChange < 0 -> tickerColors.tickerRed.copy(alpha = 0.7f)
        percentChange > 0 -> tickerColors.tickerGreen.copy(alpha = 0.7f)
        else -> Color.Transparent
    }
    val textColor = if (percentChange != 0.0) Color.White else Color.Black

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .background(backgroundColor)
            .padding(horizontal = 6.dp, vertical = 2.dp)
    ) {
        Text(
            text = "${String.format("%.2f", percentChange)}%",
            fontWeight = FontWeight.Bold,
            color = textColor
        )
    }
}

@Preview
@Composable
fun CryptoItemPreview() {
    RoyalGoldTheme {
        val calendar = Calendar.getInstance().apply {
            set(2025, Calendar.JULY, 23) // Month is 0-indexed (JULY is 6)
        }
        val dateInMillis = calendar.timeInMillis

        CryptoItem(
            crypto = Crypto(
                id = 1,
                name = "Bitcoin",
                symbol = "BTC",
                price = 118000.0,
                percentChange1h = -2.0,
                percentChange24h = 5.0,
                percentChange7d = 20.0,
                percentChange30d = 30.0,
                lastUpdated = dateInMillis
            ),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview
@Composable
fun HeaderCardPreview() {
    RoyalGoldTheme {
        HeaderCard()
    }
}