package com.roycemars.royalgold.ui.screens.expenses

import androidx.lifecycle.ViewModel
import com.roycemars.royalgold.data.expenses.ExpenseItemsProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ExpensesScreenViewModel @Inject constructor(
    expenseItemsProvider: ExpenseItemsProvider
) : ViewModel() {
    val expenseItemsList = expenseItemsProvider.getExpenseItemsList()
}