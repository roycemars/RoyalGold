package com.roycemars.royalgold.feature.market.ui

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.roycemars.royalgold.core.ui.MainViewModel
import com.roycemars.royalgold.core.ui.composables.BoxWithGradientBackground
import com.roycemars.royalgold.feature.market.data.mappers.toCrypto

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MarketScreen(
    viewModel: CryptoViewModel = hiltViewModel(),
    mainViewModel: MainViewModel = hiltViewModel()
) {
    val currentIdentifier by mainViewModel.currentThemeIdentifier.collectAsState()
    val cryptoItems = viewModel.cryptoFlow.collectAsLazyPagingItems()

    viewModel.getRecommendations()

    BoxWithGradientBackground(
        appThemeIdentifier = currentIdentifier
    ) {

        LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(
                        horizontal = 16.dp,
                        vertical = 8.dp
                    ),
                    horizontalAlignment = Alignment.CenterHorizontally
        ) {
            stickyHeader {
                HeaderCard(modifier = Modifier.fillMaxWidth())
            }
            items(cryptoItems.itemCount) { index ->
                        val cryptoEntity = cryptoItems[index]
                        if (cryptoEntity != null) {
                            CryptoItem(cryptoEntity.toCrypto(), Modifier.fillMaxWidth())
                        }
                    }

            cryptoItems.apply {
                when {
                    loadState.refresh is LoadState.Loading -> {
                        item { CircularProgressIndicator() }
                    }
                    loadState.append is LoadState.Loading -> {
                        item { CircularProgressIndicator() }
                    }
                }
            }
        }
    }
}