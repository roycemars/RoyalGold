package com.roycemars.royalgold.data.expenses

import com.roycemars.royalgold.model.expenses.ExpenseItem

interface ExpenseItemsProvider {
    fun getExpenseItems(): List<ExpenseItem>
}