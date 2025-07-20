package com.roycemars.royalgold.feature.expenses.domain

import androidx.compose.ui.graphics.vector.ImageVector

data class ExpenseItem(
    val id: Int,
    val name: String,
    val amount: String,
    val icon: ImageVector,
    val goal: String? = null,
    val progress: Float? = null
)