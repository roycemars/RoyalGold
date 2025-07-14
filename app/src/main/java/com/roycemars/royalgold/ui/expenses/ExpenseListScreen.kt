package com.roycemars.royalgold.ui.expenses

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.roycemars.royalgold.ui.theme.Gold

/**
 * TODO: Implement list of expenses backed with database and camerax scanner for receipts
 */

@Composable
fun ExpenseListScreen(
    viewModel: ExpensesScreenViewModel = hiltViewModel()
) {
    val TAG = "ExpensesListScreen"
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val expenseItemsList = remember { viewModel.expenseItemsList }
    val receiptScanner = viewModel.receiptScannerImpl

    Scaffold(floatingActionButton = {
        FloatingActionButton(
            onClick = { Log.d(TAG, "FAB clicked") },
            containerColor = Gold
        ) {
            Icon(Icons.Filled.Camera, contentDescription = "Add")
        }
    }) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            LazyColumn(modifier = Modifier.fillMaxSize(),
            ) {
                items(expenseItemsList) { wallet ->
                    ExpenseListItem(item = wallet)
                }
            }
        }
    }

}