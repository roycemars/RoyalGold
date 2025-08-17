package com.roycemars.royalgold.core.ui.theme
import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.roycemars.royalgold.core.ui.theme.core.DefaultTickerColors
import com.roycemars.royalgold.core.ui.theme.mars.MarsDarkScheme
import com.roycemars.royalgold.core.ui.theme.mars.MarsLightScheme
import com.roycemars.royalgold.core.ui.theme.mars.MartianTypography
import com.roycemars.royalgold.core.ui.theme.matrix.MatrixDarkScheme
import com.roycemars.royalgold.core.ui.theme.matrix.MatrixLightScheme
import com.roycemars.royalgold.core.ui.theme.matrix.MatrixTypography
import com.roycemars.royalgold.core.ui.theme.royal.RoyalDarkScheme
import com.roycemars.royalgold.core.ui.theme.royal.RoyalLightScheme
import com.roycemars.royalgold.core.ui.theme.space.SpaceDarkScheme
import com.roycemars.royalgold.core.ui.theme.space.SpaceLightScheme
import com.roycemars.royalgold.core.ui.theme.space.SpaceTypography

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
    ROYAL("Royal Gold"),
    MARS("Mars Future"),
    MATRIX("Matrix Green"),
    SPACE("Space Deep"),
    SYSTEM_DYNAMIC("System Dynamic")
}

val appThemes: Map<AppThemeIdentifier, ThemeDefinition> = mapOf(
    AppThemeIdentifier.ROYAL to ThemeDefinition(
        lightColorScheme = RoyalLightScheme,
        darkColorScheme = RoyalDarkScheme,
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

fun tickerColors() =
    DefaultTickerColors


@Composable
fun RoyalGoldTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false, // Set to true if you want to use Monet on API 31+
    selectedThemeIdentifier: AppThemeIdentifier = AppThemeIdentifier.SYSTEM_DYNAMIC,
    content: @Composable () -> Unit
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
        shapes = AppShapes,
        content = content
    )
}

