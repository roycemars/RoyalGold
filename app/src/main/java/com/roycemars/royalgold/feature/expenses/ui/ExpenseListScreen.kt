package com.roycemars.royalgold.feature.expenses.ui

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
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.compose.primaryLight

/**
 * TODO: Implement list of expenses backed with database and camerax scanner for receipts
 */

@Composable
fun ExpenseListScreen(
    viewModel: ExpensesScreenViewModel = hiltViewModel()
) {
    val TAG = "ExpensesListScreen"
    val expenseItemsList = remember { viewModel.expenseItemsList }

    Scaffold(floatingActionButton = {
        FloatingActionButton(
            onClick = {
                Log.d(TAG, "FAB clicked")
                      },
            containerColor = primaryLight
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