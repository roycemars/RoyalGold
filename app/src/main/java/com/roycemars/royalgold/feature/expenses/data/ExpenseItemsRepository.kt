package com.roycemars.royalgold.feature.expenses.data

import com.roycemars.royalgold.feature.expenses.domain.ExpenseItem

interface ExpenseItemsRepository {
    fun getExpenseItemsList(): List<ExpenseItem>
}