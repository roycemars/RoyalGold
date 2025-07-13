package com.roycemars.royalgold.ui.theme // Adjust package name if different

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ColorScheme // Ensure this is imported
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color // Keep this for your custom color definitions
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// Assuming these colors are defined in your com.roycemars.royalgold.view.theme.Color.kt
// If not, define them here or ensure Color.kt is in the correct package.
// Example:
// val OrangePrimary = Color(0xFFFFA500)
// val OnDarkPrimary = Color(0xFFFFFFFF)
// ... and so on for all custom colors used below

// Define your Dark Color Scheme using your custom colors
// For any parameter not specified, darkColorScheme provides a default Material 3 dark value.
private val AppDarkColorScheme = darkColorScheme(
    primary = Purple,
    onPrimary = Color.White,// WhiteForOnPurple, // As per your original
    primaryContainer = DarkPurple,
    onPrimaryContainer = Color.White,

    secondary = Gold, // BlueAccent,
    onSecondary = Color.Black,
    secondaryContainer = DarkGold,
    onSecondaryContainer = Color.White,

    tertiary = PinkAccent,
    onTertiary = Color.Black,
    tertiaryContainer = Color(0xFF6A003A),
    onTertiaryContainer = Color.White,

    error = Color(0xFFFFB4AB),
    onError = Color(0xFF690005),
    errorContainer = Color(0xFF93000A),
    onErrorContainer = Color(0xFFFFDAD6),

    background = DarkBackground,
    onBackground = OnDarkPrimary,

    surface = DarkSurface,
    onSurface = OnDarkPrimary,

    surfaceVariant = DarkCenterCircle,
    onSurfaceVariant = OnDarkSecondary,

    outline = OnDarkSurfaceVariant,
    inverseOnSurface = DarkBackground,
    inverseSurface = OnDarkPrimary
    // inversePrimary, surfaceTint, scrim will use Material 3 defaults
    // if not specified here.
)

// Define your Light Color Scheme
// For any parameter not specified, lightColorScheme provides a default Material 3 light value.
private val AppLightColorScheme = lightColorScheme(
    primary = Purple,
    onPrimary = WhiteForOnPurple, // As per your original
    primaryContainer = Purple,
    onPrimaryContainer = WhiteForOnPurple,

    // You can continue to define your specific light theme colors here.
    // For example, if you want a specific light background:
    // background = Color(0xFFFFFBFE), // As per your original
    // surface = Color(0xFFFFFBFE),   // As per your original

    // Or, if you want to use Material 3 defaults for some light theme colors,
    // simply omit them. For example, if you omit 'background', 'surface',
    // 'onBackground', 'onSurface', 'secondary', 'onSecondary' etc.,
    // they will take sensible default light values from Material 3.

    // Let's assume you want to define a few more explicitly from your original
    secondary = BlueAccent, // Example if BlueAccent works for light theme
    onSecondary = Color.Black, // Or Color.White depending on BlueAccent's brightness
    // tertiary = PinkAccent,
    // onTertiary = Color.Black,

    background = Color(0xFFFFFBFE), // Your original light background
    onBackground = Color(0xFF1C1B1F), // Your original onBackground
    surface = Color(0xFFFFFBFE), // Your original light surface
    onSurface = Color(0xFF1C1B1F), // Your original onSurface

    // Other colors like error, outline, etc., will use Material 3 light defaults
    // if not specified.
)

@Composable
fun RoyalGoldTheme( // Or whatever your Theme name is
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Set to false if you ONLY want your custom theme
    content: @Composable () -> Unit
) {
    val colorScheme: ColorScheme = when { // Explicitly type colorScheme for clarity
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> AppDarkColorScheme // Use your custom dark scheme
        else -> AppLightColorScheme     // Use your custom light scheme
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            // Match status bar to background or primary, as per your design
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
            // Optional: for navigation bar
            // window.navigationBarColor = colorScheme.background.toArgb()
            // WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,        // Pass the chosen ColorScheme
        typography = Typography,          // Make sure you have Typography.kt defined
        shapes = AppShapes,               // Make sure you have Shapes.kt defined (e.g., AppShapes in Shapes.kt)
        content = content
    )
}