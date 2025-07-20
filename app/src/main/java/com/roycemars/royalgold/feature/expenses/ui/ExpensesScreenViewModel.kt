package com.roycemars.royalgold.feature.expenses.ui

import androidx.lifecycle.ViewModel
import com.roycemars.royalgold.feature.expenses.data.ExpenseItemsRepository
import com.roycemars.royalgold.feature.expenses.camera.ReceiptScanner
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ExpensesScreenViewModel @Inject constructor(
    expenseItemsRepository: ExpenseItemsRepository,
    val receiptScannerImpl: ReceiptScanner
) : ViewModel() {
    val expenseItemsList = expenseItemsRepository.getExpenseItemsList()
}