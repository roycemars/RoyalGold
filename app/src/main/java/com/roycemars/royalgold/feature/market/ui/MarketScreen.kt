package com.roycemars.royalgold.feature.market.ui

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
    val recommendationState by viewModel.recommendationState.collectAsState()

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
                RecommendationHeader(recommendationState)
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

@Composable
fun RecommendationHeader(state: RecommendationUiState) {
    // Only display the header if there's content or loading/error states to show
    // You can customize the appearance extensively
    when (state) {
        is RecommendationUiState.Success -> {
            if (state.summary.isNotBlank()) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    shape = RoundedCornerShape(8.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Recommendations:",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = state.summary,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }
            }
        }
        is RecommendationUiState.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator() // Or a shimmer placeholder
            }
        }
        is RecommendationUiState.Error -> {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer)
            ) {
                Text(
                    text = state.message,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onErrorContainer,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
        RecommendationUiState.Empty -> {
            // Optionally show nothing or a placeholder if empty
            // Box(modifier = Modifier.height(0.dp)) // Effectively hides it
        }
    }
}