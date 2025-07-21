package com.roycemars.royalgold.core.ui.activity

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.roycemars.royalgold.app.navigation.Screen
import com.roycemars.royalgold.app.navigation.bottomNavItems
import com.roycemars.royalgold.core.ui.theme.AppTheme
import com.roycemars.royalgold.feature.budget.ui.BudgetScreen
import com.roycemars.royalgold.feature.expenses.ui.ExpensesScreen
import com.roycemars.royalgold.feature.market.ui.MarketScreen
import com.roycemars.royalgold.feature.portfolio.ui.PortfolioScreen
import com.roycemars.royalgold.feature.settings.SettingsScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import java.util.prefs.Preferences

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MainScreen()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    var currentScreenTitle by remember { mutableStateOf(Screen.Expenses.title) }

    val useDynamicTheme = rememberSaveable { mutableStateOf(false) }

    AppTheme(dynamicColor = useDynamicTheme.value) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(currentScreenTitle) }, // Dynamic title
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary, // Or your desired color
                        titleContentColor = MaterialTheme.colorScheme.onPrimary
                    )
                )
            },
            bottomBar = {
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.surface, // Or your specific bottom bar color
                    contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                ) {
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentDestination = navBackStackEntry?.destination

                    bottomNavItems.forEach { screen ->
                        val selected =
                            currentDestination?.hierarchy?.any { it.route == screen.route } == true
                        NavigationBarItem(
                            selected = selected,
                            onClick = {
                                currentScreenTitle = screen.title // Update title on click
                                navController.navigate(screen.route) {
                                    // Pop up to the start destination of the graph to
                                    // avoid building up a large stack of destinations
                                    // on the back stack as users select items
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    // Avoid multiple copies of the same destination when
                                    // reselecting the same item
                                    launchSingleTop = true
                                    // Restore state when reselecting a previously selected item
                                    restoreState = true
                                }
                            },
                            icon = {
                                Icon(
                                    imageVector = if (selected) screen.selectedIcon else screen.unselectedIcon,
                                    contentDescription = screen.title
                                )
                            },
                            label = { Text(screen.title) },
                            alwaysShowLabel = true, // As seen in your screenshot
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = MaterialTheme.colorScheme.primary, // Color for selected icon
                                selectedTextColor = MaterialTheme.colorScheme.primary, // Color for selected text
                                unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                indicatorColor = Color.Companion.Transparent // Or your choice for indicator
                            )
                        )
                    }
                }
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = Screen.Portfolio.route, // Your initial screen
                modifier = Modifier.Companion.padding(innerPadding)
            ) {
                composable(Screen.Expenses.route) { ExpensesScreen() }
                composable(Screen.Budget.route) { BudgetScreen() } // Replace with your actual ChartScreen
                composable(Screen.Portfolio.route) { PortfolioScreen() }
                composable(Screen.Market.route) { MarketScreen() }
                composable(Screen.Settings.route) { SettingsScreen(useDynamicTheme.value, onToggleDynamicTheme = { useDynamicTheme.value = it }) }
                // Add other composable routes here if needed
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AppTheme(darkTheme = true) { // Explicitly use dark theme for this preview
        MainScreen()
    }
}