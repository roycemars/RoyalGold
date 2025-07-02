package com.roycemars.royalgold.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.DonutSmall
import androidx.compose.material.icons.filled.Feed
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Camera
import androidx.compose.material.icons.outlined.DonutSmall
import androidx.compose.material.icons.outlined.Feed
import androidx.compose.material.icons.outlined.MonetizationOn
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(
    val route: String,
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
) {
    data object Scan : Screen(
        route = "tracker",
        title = "Tracker",
        selectedIcon = Icons.Filled.Camera,
        unselectedIcon = Icons.Outlined.Camera
    )
    data object Budget : Screen(
        route = "budget",
        title = "Budget",
        selectedIcon = Icons.Filled.DonutSmall,
        unselectedIcon = Icons.Outlined.DonutSmall
    )
    data object Wallet : Screen(
        route = "wallet",
        title = "Wallet",
        selectedIcon = Icons.Filled.MonetizationOn,
        unselectedIcon = Icons.Outlined.MonetizationOn
    )
    data object News : Screen(
        route = "news",
        title = "News",
        selectedIcon = Icons.Filled.Feed,
        unselectedIcon = Icons.Outlined.Feed
    )
    data object Settings : Screen(
        route = "settings",
        title = "Settings",
        selectedIcon = Icons.Filled.Settings,
        unselectedIcon = Icons.Outlined.Settings
    )
}

val bottomNavItems = listOf(
    Screen.Wallet,
    Screen.Budget,
    Screen.Scan,
    Screen.News,
    Screen.Settings
)