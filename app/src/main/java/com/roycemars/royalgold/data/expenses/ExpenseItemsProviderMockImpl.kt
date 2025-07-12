package com.roycemars.royalgold.data.expenses

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AreaChart
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.EnergySavingsLeaf
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.ShoppingBasket
import androidx.compose.material.icons.filled.Stadium
import com.roycemars.royalgold.model.expenses.ExpenseItem
import javax.inject.Inject

class ExpenseItemsProviderMockImpl @Inject constructor() : ExpenseItemsProvider {
    override fun getExpenseItemsList() = listOf(
        ExpenseItem(1, "CAR SERVICE", "$150", Icons.Filled.DirectionsCar),
        ExpenseItem(2, "GROCERIES", "$250", Icons.Filled.ShoppingBasket),
        ExpenseItem(3, "UTILITIES", "$80", Icons.Filled.LocalFireDepartment),
        ExpenseItem(4, "OUTING", "$120", Icons.Filled.Stadium),
        ExpenseItem(5, "RESTOCKING", "$60", Icons.Filled.EnergySavingsLeaf),
        ExpenseItem(6, "UTILITIES", "$80", Icons.Filled.LocalFireDepartment),
        ExpenseItem(7, "INVESTED", "$2,000", Icons.Filled.AreaChart, goal = "$20,000", progress = 0.1f),
    )
}