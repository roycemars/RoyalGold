package com.roycemars.royalgold.di

import com.roycemars.royalgold.data.expenses.ExpenseItemsProvider
import com.roycemars.royalgold.data.expenses.ExpenseItemsProviderMockImpl
import com.roycemars.royalgold.ui.scanner.ReceiptScanner
import com.roycemars.royalgold.ui.scanner.ReceiptScannerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {
    @Binds
    @Singleton
    abstract fun bindExpenseItemsProvider(expenseItemsProviderMockImpl: ExpenseItemsProviderMockImpl): ExpenseItemsProvider

    @Binds
    @Singleton
    abstract fun bindReceiptScanner(receiptScannerImpl: ReceiptScannerImpl): ReceiptScanner
}