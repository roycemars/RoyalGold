package com.roycemars.royalgold.feature.market.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.roycemars.royalgold.core.ui.theme.RoyalGoldTheme
import com.roycemars.royalgold.feature.market.domain.Crypto
import java.util.Calendar
import java.util.Date
import kotlin.Int
import kotlin.String

@Composable
fun CryptoItem(
    crypto: Crypto,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(crypto.symbol)
            Text(crypto.price.toString())
        }
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
                lastUpdated = dateInMillis
            ),
            modifier = Modifier.fillMaxWidth()
        )
    }
}