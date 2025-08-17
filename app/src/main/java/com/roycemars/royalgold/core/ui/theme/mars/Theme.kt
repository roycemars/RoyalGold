package com.roycemars.royalgold.core.ui.theme.mars
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme

val MarsLightScheme = lightColorScheme(
    primary = MarsRed,                 // Key actions, FAB, selected tab icon
    onPrimary = PureWhite,             // Text/icons on MarsRed elements
    primaryContainer = MarsRed,        // Could be used for less prominent MarsRed elements
    onPrimaryContainer = PureWhite,

    secondary = MarsRed,               // Can also be MarsRed if you want a strong accent
    onSecondary = PureWhite,
    secondaryContainer = Silver,       // Example for buttons or cards if not using MarsRed
    onSecondaryContainer = PureBlack,

    tertiary = MarsRed,                // Yet another accent if needed
    onTertiary = PureWhite,
    tertiaryContainer = Silver,
    onTertiaryContainer = PureBlack,

    error = MarsRed,                   // Typically a distinct error color, but using MarsRed for simplicity
    onError = PureWhite,
    errorContainer = MarsRed,
    onErrorContainer = PureWhite,

    background = PureWhite,            // Screen background
    onBackground = PureBlack,          // Text on screen background

    surface = PureWhite,               // Surface of components like Cards, Sheets, Menus
    onSurface = PureBlack,             // Text on these surfaces
    surfaceVariant = Silver,           // Slightly different surface color (e.g., outlines, dividers)
    onSurfaceVariant = PureBlack,      // Text on surfaceVariant

    outline = LightGray,               // Borders, dividers
    inverseOnSurface = PureWhite,      // For elements on a dark surface in light theme (rare)
    inverseSurface = PureBlack,        // For elements that need to be dark in light theme (rare)
    inversePrimary = PureWhite,        // For text/icons on an inverse primary surface

    // Custom colors not directly part of Material 3 scheme, but useful
    // These would typically be accessed via custom extension properties on MaterialTheme.colorScheme
    // For simplicity here, we assume components like TopAppBar/BottomAppBar will pick them up if defined,
    // or you'll apply them manually. Material 3 TopAppBar/BottomAppBar use `containerColor` and `contentColor`.
    // We will ensure our `surface` and `onSurface` or specific component colors handle this.

    // For TopAppBar specifically in Material 3, you'd set its `containerColor`
    // For BottomAppBar, similarly.
)


val MarsDarkScheme = darkColorScheme(
    primary = MarsRed,
    onPrimary = PureWhite,
    primaryContainer = MarsRed,
    onPrimaryContainer = PureWhite,

    secondary = MarsRed,
    onSecondary = PureWhite,
    secondaryContainer = DarkGray,
    onSecondaryContainer = PureWhite,

    tertiary = MarsRed,
    onTertiary = PureWhite,
    tertiaryContainer = DarkGray,
    onTertiaryContainer = PureWhite,

    error = MarsRed,
    onError = PureWhite,
    errorContainer = MarsRed,
    onErrorContainer = PureWhite,

    background = DarkBackground,       // Screen background (PureBlack as requested)
    onBackground = PureWhite,          // Text on screen background

    surface = DarkSurface,             // Cards, Sheets, Menus (e.g., NearBlack)
    onSurface = PureWhite,             // Text on these surfaces
    surfaceVariant = SlightlyLighterDarkGray, // Slightly different surface color
    onSurfaceVariant = LightGray,      // Text on surfaceVariant (lighter gray for contrast)

    outline = DarkGray,                // Borders, dividers
    inverseOnSurface = PureBlack,
    inverseSurface = PureWhite,
    inversePrimary = PureBlack,
)

