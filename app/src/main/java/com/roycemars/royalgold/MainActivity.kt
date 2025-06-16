package com.roycemars.royalgold

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.roycemars.royalgold.ui.theme.RoyalGoldTheme
import com.roycemars.royalgold.ui.views.ExpensesCircularChartCard

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RoyalGoldTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ChartsScreen(innerPadding)
                }
            }
        }
    }
}

@Composable
fun ChartsScreen(innerPadding : PaddingValues) {
    Box(modifier = Modifier.padding(innerPadding).fillMaxSize(), contentAlignment = Alignment.Center) {
        ExpensesCircularChartCard()
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    RoyalGoldTheme {
        ChartsScreen(PaddingValues(0.dp))
    }
}