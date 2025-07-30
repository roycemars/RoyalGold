package com.roycemars.royalgold.core.ui.theme
import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.roycemars.royalgold.core.ui.theme.core.DefaultGradientColors
import com.roycemars.royalgold.core.ui.theme.core.DefaultTickerColors
import com.roycemars.royalgold.core.ui.theme.core.GradientColors
import com.roycemars.royalgold.core.ui.theme.core.TickerColors
import com.roycemars.royalgold.core.ui.theme.mars.MarsDarkScheme
import com.roycemars.royalgold.core.ui.theme.mars.MarsLightScheme
import com.roycemars.royalgold.core.ui.theme.mars.MartianTypography
import com.roycemars.royalgold.core.ui.theme.matrix.MatrixDarkScheme
import com.roycemars.royalgold.core.ui.theme.matrix.MatrixGradientColors
import com.roycemars.royalgold.core.ui.theme.matrix.MatrixLightScheme
import com.roycemars.royalgold.core.ui.theme.matrix.MatrixTypography
import com.roycemars.royalgold.core.ui.theme.space.SpaceDarkScheme
import com.roycemars.royalgold.core.ui.theme.space.SpaceGradientColors
import com.roycemars.royalgold.core.ui.theme.space.SpaceLightScheme
import com.roycemars.royalgold.core.ui.theme.space.SpaceTypography

private val DefaultLightScheme = lightColorScheme(
    primary = primaryLight,
    onPrimary = onPrimaryLight,
    primaryContainer = primaryContainerLight,
    onPrimaryContainer = onPrimaryContainerLight,
    secondary = secondaryLight,
    onSecondary = onSecondaryLight,
    secondaryContainer = secondaryContainerLight,
    onSecondaryContainer = onSecondaryContainerLight,
    tertiary = tertiaryLight,
    onTertiary = onTertiaryLight,
    tertiaryContainer = tertiaryContainerLight,
    onTertiaryContainer = onTertiaryContainerLight,
    error = errorLight,
    onError = onErrorLight,
    errorContainer = errorContainerLight,
    onErrorContainer = onErrorContainerLight,
    background = backgroundLight,
    onBackground = onBackgroundLight,
    surface = surfaceLight,
    onSurface = onSurfaceLight,
    surfaceVariant = surfaceVariantLight,
    onSurfaceVariant = onSurfaceVariantLight,
    outline = outlineLight,
    outlineVariant = outlineVariantLight,
    scrim = scrimLight,
    inverseSurface = inverseSurfaceLight,
    inverseOnSurface = inverseOnSurfaceLight,
    inversePrimary = inversePrimaryLight,
    surfaceDim = surfaceDimLight,
    surfaceBright = surfaceBrightLight,
    surfaceContainerLowest = surfaceContainerLowestLight,
    surfaceContainerLow = surfaceContainerLowLight,
    surfaceContainer = surfaceContainerLight,
    surfaceContainerHigh = surfaceContainerHighLight,
    surfaceContainerHighest = surfaceContainerHighestLight,
)

private val DefaultDarkScheme = darkColorScheme(
    primary = primaryDark,
    onPrimary = onPrimaryDark,
    primaryContainer = primaryContainerDark,
    onPrimaryContainer = onPrimaryContainerDark,
    secondary = secondaryDark,
    onSecondary = onSecondaryDark,
    secondaryContainer = secondaryContainerDark,
    onSecondaryContainer = onSecondaryContainerDark,
    tertiary = tertiaryDark,
    onTertiary = onTertiaryDark,
    tertiaryContainer = tertiaryContainerDark,
    onTertiaryContainer = onTertiaryContainerDark,
    error = errorDark,
    onError = onErrorDark,
    errorContainer = errorContainerDark,
    onErrorContainer = onErrorContainerDark,
    background = backgroundDark,
    onBackground = onBackgroundDark,
    surface = surfaceDark,
    onSurface = onSurfaceDark,
    surfaceVariant = surfaceVariantDark,
    onSurfaceVariant = onSurfaceVariantDark,
    outline = outlineDark,
    outlineVariant = outlineVariantDark,
    scrim = scrimDark,
    inverseSurface = inverseSurfaceDark,
    inverseOnSurface = inverseOnSurfaceDark,
    inversePrimary = inversePrimaryDark,
    surfaceDim = surfaceDimDark,
    surfaceBright = surfaceBrightDark,
    surfaceContainerLowest = surfaceContainerLowestDark,
    surfaceContainerLow = surfaceContainerLowDark,
    surfaceContainer = surfaceContainerDark,
    surfaceContainerHigh = surfaceContainerHighDark,
    surfaceContainerHighest = surfaceContainerHighestDark,
)

@Immutable
data class ColorFamily(
    val color: Color,
    val onColor: Color,
    val colorContainer: Color,
    val onColorContainer: Color
)

val unspecified_scheme = ColorFamily(
    Color.Unspecified, Color.Unspecified, Color.Unspecified, Color.Unspecified
)

data class ThemeDefinition(
    val lightColorScheme: ColorScheme,
    val darkColorScheme: ColorScheme,
    val typography: Typography
)

enum class AppThemeIdentifier(val displayName: String) {
    ROYAL("Royal Gold"), // Or "Ocean" if that's your primary
    MARS("Martian Flame"),
    MATRIX("Matrix Green"),
    SPACE("Space Dark"),
    SYSTEM_DYNAMIC("System Dynamic")
}

val appThemes: Map<AppThemeIdentifier, ThemeDefinition> = mapOf(
    AppThemeIdentifier.ROYAL to ThemeDefinition(
        lightColorScheme = DefaultLightScheme,
        darkColorScheme = DefaultDarkScheme,
        typography = DefaultTypography
    ),
    AppThemeIdentifier.MARS to ThemeDefinition(
        lightColorScheme = MarsLightScheme,
        darkColorScheme = MarsDarkScheme,
        typography = MartianTypography
    ),
    AppThemeIdentifier.MATRIX to ThemeDefinition(
        lightColorScheme = MatrixLightScheme,
        darkColorScheme = MatrixDarkScheme,
        typography = MatrixTypography
    ),
    AppThemeIdentifier.SPACE to ThemeDefinition(
        lightColorScheme = SpaceLightScheme,
        darkColorScheme = SpaceDarkScheme,
        typography = SpaceTypography
    ),
    // SYSTEM_DYNAMIC will be handled separately as it doesn't have a predefined typography
)

fun gradientColors(selectedThemeIdentifier: AppThemeIdentifier = AppThemeIdentifier.ROYAL) = when(selectedThemeIdentifier) {
    AppThemeIdentifier.MATRIX -> MatrixGradientColors
    AppThemeIdentifier.SPACE -> SpaceGradientColors
    else -> DefaultGradientColors
}

fun tickerColors() =
    DefaultTickerColors


    @Composable
fun RoyalGoldTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    selectedThemeIdentifier: AppThemeIdentifier = AppThemeIdentifier.ROYAL,
    // Dynamic color is available on Android 12+
    content: @Composable() () -> Unit
) {
    val context = LocalContext.current
    val currentThemeDef = appThemes[selectedThemeIdentifier]

    val colorScheme: ColorScheme
    val typography = currentThemeDef?.typography ?: DefaultTypography

    if ((selectedThemeIdentifier == AppThemeIdentifier.SYSTEM_DYNAMIC)
        && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        colorScheme = if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
    } else if (currentThemeDef != null) {
        colorScheme = if (darkTheme) currentThemeDef.darkColorScheme else currentThemeDef.lightColorScheme
    } else {
        // Fallback to default if something goes wrong (e.g., SYSTEM_DYNAMIC on older OS, or invalid identifier)
        val ROYALTheme = appThemes[AppThemeIdentifier.ROYAL]!! // Assuming DEFAULT always exists
        colorScheme = if (darkTheme) ROYALTheme.darkColorScheme else ROYALTheme.lightColorScheme
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

  MaterialTheme(
    colorScheme = colorScheme,
    typography = typography,
    content = content
  )
}

