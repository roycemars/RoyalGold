package com.roycemars.royalgold.data.expenses

import com.roycemars.royalgold.model.expenses.ExpenseItem

interface ExpenseItemsProvider {
    fun getExpenseItemsList(): List<ExpenseItem>
}