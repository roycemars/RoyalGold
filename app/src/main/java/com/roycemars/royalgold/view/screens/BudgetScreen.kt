package com.roycemars.royalgold.view.screens // Adjust package name

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.roycemars.royalgold.view.views.BarChart
import com.roycemars.royalgold.view.views.PieChart

@Composable
fun BudgetScreen() {
    val systemIsDark = isSystemInDarkTheme()

        Box(modifier = Modifier
            .fillMaxSize(), contentAlignment = Alignment.Center) {
            Column {
                PieChart()
                BarChart(darkTheme = systemIsDark)
            }
        }
}