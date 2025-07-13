package com.roycemars.royalgold.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.roycemars.royalgold.R
import com.roycemars.royalgold.ui.screens.expenses.ExpensesScreenViewModel
import com.roycemars.royalgold.ui.theme.Gold

@Composable
fun ExpensesListScreen(
    viewModel: ExpensesScreenViewModel = hiltViewModel()
) {
    val TAG = "ExpensesListScreen"
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val expenseItemsList = remember { viewModel.expenseItemsList }
    val receiptScanner = viewModel.receiptScannerImpl

    Scaffold(floatingActionButton = {
        LargeFloatingActionButton(
            onClick = { Log.d(TAG, "FAB clicked") },
            containerColor = Gold
        ) {
            Icon(Icons.Filled.Camera, contentDescription = "Add")
        }
    }) { innerPadding ->
        Text("Expenses List", modifier = Modifier.padding(innerPadding))
    }

}