package com.roycemars.royalgold.feature.market.ui

import android.util.Log
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import com.roycemars.royalgold.BuildConfig

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MarketScreen() {
    Log.d("MarketScreen", "BuildConfig.API_KEY=${BuildConfig.COINMARKETCAP_API_KEY}")
}