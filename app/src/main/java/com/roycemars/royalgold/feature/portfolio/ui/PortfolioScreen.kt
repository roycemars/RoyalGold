package com.roycemars.royalgold.feature.portfolio.ui // Adjust package name

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.roycemars.royalgold.core.ui.MainViewModel
import com.roycemars.royalgold.core.ui.charts.LineChartCard
import com.roycemars.royalgold.core.ui.composables.BoxWithGradientBackground

/**
 * TODO: Add portfolio database backed with Firebase to keep track of value of different assets
 * Next - add suggestions to reallocate portfolio based on Market Gemini analysis of crypto trends
 */
@Composable
fun PortfolioScreen(
    viewModel: MainViewModel = hiltViewModel()
) {
    val currentIdentifier by viewModel.currentThemeIdentifier.collectAsState()

    BoxWithGradientBackground(
        appThemeIdentifier = currentIdentifier,
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column {
            LineChartCard()
        }
    }
}