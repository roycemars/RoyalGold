package com.roycemars.royalgold.core.ui.composables

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.roycemars.royalgold.core.ui.theme.AppThemeIdentifier

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun BoxWithGradientBackground(
    appThemeIdentifier: AppThemeIdentifier = AppThemeIdentifier.ROYAL,
    modifier: Modifier = Modifier,
    contentAlignment: Alignment = Alignment.TopStart,
    content: @Composable BoxScope.() -> Unit) {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(
            contentAlignment = contentAlignment
        ) {
            content()
        }
    }
}