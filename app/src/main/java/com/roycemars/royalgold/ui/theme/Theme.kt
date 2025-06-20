package com.roycemars.royalgold.ui.theme // Adjust package name

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme // Keep light scheme if you want to support both
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// Define your Dark Color Scheme
private val DarkColorScheme = darkColorScheme(
    primary = OrangePrimary,
    onPrimary = Color.Black, // Text/icon color on top of primary color (e.g., on a bright orange button)
    primaryContainer = OrangePrimaryVariant, // A container that uses the primary color, often more subdued
    onPrimaryContainer = Color.White,

    secondary = BlueAccent, // Example: Using another segment color as secondary
    onSecondary = Color.Black,
    secondaryContainer = Color(0xFF004A7F), // A darker container for secondary
    onSecondaryContainer = Color.White,

    tertiary = PinkAccent, // Example: Using another segment color as tertiary
    onTertiary = Color.Black,
    tertiaryContainer = Color(0xFF6A003A),
    onTertiaryContainer = Color.White,

    error = Color(0xFFFFB4AB),
    onError = Color(0xFF690005),
    errorContainer = Color(0xFF93000A),
    onErrorContainer = Color(0xFFFFDAD6),

    background = DarkBackground,
    onBackground = OnDarkPrimary,

    surface = DarkSurface, // Used for Cards, Sheets, Menus
    onSurface = OnDarkPrimary,

    surfaceVariant = DarkCenterCircle, // Can be used for elements like unselected chips, dividers, text field outlines
    onSurfaceVariant = OnDarkSecondary, // Text/icons on top of surfaceVariant

    outline = OnDarkSurfaceVariant, // Borders, dividers
    inverseOnSurface = DarkBackground, // For text/icons on an "inverseSurface" (rarely needed for simple themes)
    inverseSurface = OnDarkPrimary,    // For elements that need to contrast with the default surface (rarely needed)
    // inversePrimary = ..., // (Rarely needed)

    // surfaceTint = primary // Color to tint elevation overlays, typically primary
    // scrim = Color.Black // Color for modal scrims
)

// If you still want a LightColorScheme, define it here too, otherwise you can remove it
private val LightColorScheme = lightColorScheme(
    primary = OrangePrimary,
    onPrimary = Color.White,
    primaryContainer = OrangePrimaryVariant,
    onPrimaryContainer = Color.Black,
    // ... define other light theme colors or use Material Theme Builder defaults
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
)

@Composable
fun RoyalGoldTheme( // Or whatever your Theme name is
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true, // Set to false if you ONLY want your custom theme
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme // Fallback to your defined LightColorScheme or a default one
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb() // Match status bar to background
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography, // Make sure you have Typography defined
        shapes = AppShapes,         // Make sure you have Shapes defined
        content = content
    )
}