package com.roycemars.royalgold.feature.budget.ui // Adjust package name

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.roycemars.royalgold.core.ui.MainViewModel
import com.roycemars.royalgold.core.ui.charts.BarChart
import com.roycemars.royalgold.core.ui.charts.PieChart
import com.roycemars.royalgold.core.ui.composables.BoxWithGradientBackground

/**
 * TODO: add Firebase for remote database sync
 */
@Composable
fun BudgetScreen(
    mainViewModel: MainViewModel = hiltViewModel()
) {
    val currentIdentifier by mainViewModel.currentThemeIdentifier.collectAsState()
    val scrollState = rememberScrollState()

    BoxWithGradientBackground(
        appThemeIdentifier = currentIdentifier,
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                PieChart()
                BarChart()
            }
        }
    }
}