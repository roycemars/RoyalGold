package com.roycemars.royalgold.view.screens // Adjust package name

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.roycemars.royalgold.view.views.ExpensesCircularChartCard

@Composable
fun BudgetScreen() {
        Box(modifier = Modifier
            .fillMaxSize(), contentAlignment = Alignment.Center) {
            ExpensesCircularChartCard()
        }
}