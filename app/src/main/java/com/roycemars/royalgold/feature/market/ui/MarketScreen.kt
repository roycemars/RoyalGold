package com.roycemars.royalgold.feature.market.ui

import android.util.Log
import android.widget.Toast
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.roycemars.royalgold.BuildConfig
import com.roycemars.royalgold.feature.market.domain.Crypto

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MarketScreen(
    cryptoListings: LazyPagingItems<Crypto>
) {
    Log.d("MarketScreen", "BuildConfig.API_KEY=${BuildConfig.COINMARKETCAP_API_KEY}")

    val context = LocalContext.current
    LaunchedEffect(key1 = cryptoListings.loadState) {
        if (cryptoListings.loadState.refresh is LoadState.Error) {
            Toast.makeText(
                context,
                "Error: " + (cryptoListings.loadState.refresh as LoadState.Error).error.message,
                Toast.LENGTH_LONG
            ).show()
        }
    }
}