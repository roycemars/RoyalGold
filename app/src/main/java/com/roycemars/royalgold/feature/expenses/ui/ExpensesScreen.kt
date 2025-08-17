package com.roycemars.royalgold.feature.expenses.ui

import android.R
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.roycemars.royalgold.core.ui.MainViewModel
import com.roycemars.royalgold.core.ui.composables.BoxWithGradientBackground
//import com.roycemars.royalgold.core.ui.theme.onPrimaryContainerDark
//import com.roycemars.royalgold.core.ui.theme.primaryContainerDark
//import com.roycemars.royalgold.core.ui.theme.primaryLight

/**
 * TODO: Implement list of expenses backed with database and camerax scanner for receipts
 */

@Composable
fun ExpensesScreen(
    viewModel: ExpensesScreenViewModel = hiltViewModel(),
    mainViewModel: MainViewModel = hiltViewModel()
) {
    val TAG = "ExpensesListScreen"
    val currentIdentifier by mainViewModel.currentThemeIdentifier.collectAsState()

    val expenseItemsList = remember { viewModel.expenseItemsList }

    Scaffold(floatingActionButton = {
        FloatingActionButton(
            onClick = {
                Log.d(TAG, "FAB clicked")
                      }
        ) {
            Icon(Icons.Filled.Camera, contentDescription = "Add")
        }
    }) { innerPadding ->
        BoxWithGradientBackground(
            appThemeIdentifier = currentIdentifier,
        ) {
            LazyColumn(modifier = Modifier.fillMaxSize().padding(innerPadding),
            ) {
                items(expenseItemsList) { wallet ->
                    ExpenseListItem(item = wallet)
                }
            }
        }
    }

}