package com.roycemars.royalgold.ui.expenses

import androidx.lifecycle.ViewModel
import com.roycemars.royalgold.data.expenses.ExpenseItemsProvider
import com.roycemars.royalgold.ui.expenses.scanner.ReceiptScanner
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ExpensesScreenViewModel @Inject constructor(
    expenseItemsProvider: ExpenseItemsProvider,
    val receiptScannerImpl: ReceiptScanner
) : ViewModel() {
    val expenseItemsList = expenseItemsProvider.getExpenseItemsList()
}