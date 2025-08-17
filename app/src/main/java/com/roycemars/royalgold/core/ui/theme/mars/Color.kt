package com.roycemars.royalgold.core.ui.theme.mars
import androidx.compose.ui.graphics.Color

// Key Colors
val MarsRed = Color(0xFFFF0030)
val PureWhite = Color(0xFFFFFFFF)
val PureBlack = Color(0xFF000000)

// Grey Shades
val Silver = Color(0xFFEEEEEE)
val LightGray = Color(0xFFCCCCCC)
val Gray = Color(0xFFAAAAAA)
val DarkGray = Color(0xFF555555)
val NearBlack = Color(0xFF1C1B1F) // A common dark surface color for Material 3 dark themes
val SlightlyOffBlack = Color(0xFF222222) // Alternative for top bar or surfaces in dark mode
val SlightlyLighterDarkGray = Color(0xFF3A3A3A) // For subtle variations in dark mode

// Semantic Colors (will be used in the ColorScheme)

// Light Theme Specific
val LightTopBarBackground = PureBlack
val LightTopBarContent = PureWhite
val LightBottomBarBackground = PureBlack
val LightBottomBarSelectedIcon = MarsRed
val LightBottomBarUnselectedIcon = Gray

// Dark Theme Specific
val DarkTopBarBackground = SlightlyOffBlack // Or DarkGray if you prefer a lighter dark
val DarkTopBarContent = PureWhite
val DarkBottomBarBackground = SlightlyOffBlack // Consistent with top bar
val DarkBottomBarSelectedIcon = MarsRed
val DarkBottomBarUnselectedIcon = Gray
val DarkSurface = NearBlack // Material 3 recommended dark surface
val DarkBackground = PureBlack






