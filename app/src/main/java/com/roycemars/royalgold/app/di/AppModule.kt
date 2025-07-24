package com.roycemars.royalgold.app.di

import com.roycemars.royalgold.feature.expenses.camera.ReceiptScanner
import com.roycemars.royalgold.feature.expenses.camera.ReceiptScannerImpl
import com.roycemars.royalgold.feature.expenses.data.ExpenseItemsRepository
import com.roycemars.royalgold.feature.expenses.data.ExpenseItemsRepositoryMockImpl
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
    abstract fun bindExpenseItemsProvider(expenseItemsProviderMockImpl: ExpenseItemsRepositoryMockImpl): ExpenseItemsRepository

    @Binds
    @Singleton
    abstract fun bindReceiptScanner(receiptScannerImpl: ReceiptScannerImpl): ReceiptScanner
}