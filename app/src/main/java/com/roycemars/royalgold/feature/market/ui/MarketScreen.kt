package com.roycemars.royalgold.feature.market.ui

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.roycemars.royalgold.BuildConfig
import com.roycemars.royalgold.feature.market.domain.Crypto

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MarketScreen(
    viewModel: CryptoViewModel = hiltViewModel()
) {
    val cryptoListings = viewModel.cryptoPagingFlow.collectAsLazyPagingItems()
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

    Box(modifier = Modifier.fillMaxSize()) {
        if(cryptoListings.loadState.refresh is LoadState.Loading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
//            items(
//                items = cryptoListings,
//                key = { crypto: Crypto -> crypto.id } ,
//            ) { crypto: Crypto ->
//                crypto?.let {
//                    CryptoItem(
//                        crypto = crypto,
//                        modifier = Modifier.fillMaxWidth()
//                    )
//                }
//            }
            item {
                if (cryptoListings.loadState.append is LoadState.Loading) {
                    CircularProgressIndicator()
                }
            }
        }
        }
    }
}