package com.roycemars.royalgold.feature.settings // Adjust package name

import android.annotation.SuppressLint
import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.roycemars.royalgold.R
import com.roycemars.royalgold.core.ui.MainViewModel
import com.roycemars.royalgold.core.ui.composables.BoxWithGradientBackground
import com.roycemars.royalgold.core.ui.theme.AppThemeIdentifier
import com.roycemars.royalgold.core.ui.theme.RoyalGoldTheme
import com.roycemars.royalgold.core.ui.theme.backgroundGradientDark
import com.roycemars.royalgold.core.ui.theme.backgroundGradientLight

@Composable
fun SettingsScreen(
    viewModel: MainViewModel = hiltViewModel()
) {
    val currentIdentifier by viewModel.currentThemeIdentifier.collectAsState()
    SettingsScreenContent(currentIdentifier, onThemeSelected = { selectedIdentifier ->
        viewModel.updateThemeIdentifier(selectedIdentifier)
    })
}

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun SettingsScreenContent(
    currentIdentifier: AppThemeIdentifier,
    onThemeSelected: (AppThemeIdentifier) -> Unit
) {
    BoxWithGradientBackground(
        appThemeIdentifier = currentIdentifier,
        modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
    ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                ThemeRow(currentIdentifier, onThemeSelected)
            }
    }
}

@Composable
fun ThemeRow(
    currentIdentifier: AppThemeIdentifier,
    onThemeSelected: (AppThemeIdentifier) -> Unit,
    ) {
    val availableThemeIdentifiers = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        AppThemeIdentifier.entries
    } else {
        AppThemeIdentifier.entries.filter { it != AppThemeIdentifier.SYSTEM_DYNAMIC }
    }

    Column(horizontalAlignment = Alignment.Start) {
        Text(
            stringResource(R.string.select_app_theme),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Column(Modifier.selectableGroup()) {
            availableThemeIdentifiers.forEach { identifier ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .selectable(
                            selected = (identifier == currentIdentifier),
                            onClick = { onThemeSelected(identifier) },
                            role = Role.RadioButton
                        )
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = (identifier == currentIdentifier),
                        onClick = null
                    )
                    Text(
                        text = identifier.displayName, // Use the user-friendly name
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun SettingsScreenPreviewInDark() {
    RoyalGoldTheme(darkTheme = true) {
        SettingsScreenContent(AppThemeIdentifier.MATRIX, { })
    }
}

@Preview
@Composable
fun SettingsScreenPreviewInLight() {
    RoyalGoldTheme(darkTheme = false) {
        SettingsScreenContent(AppThemeIdentifier.MATRIX, { })
    }
}