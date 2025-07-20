package com.roycemars.royalgold.feature.settings // Adjust package name

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SettingsScreen(
        useDynamicTheme: Boolean,
        onToggleDynamicTheme: (Boolean) -> Unit
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Use Dynamic Theme", modifier = Modifier.weight(1f))
                Switch(
                    checked = useDynamicTheme,
                    onCheckedChange = { onToggleDynamicTheme(it) }
                )
            }
        }
    }