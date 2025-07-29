package com.roycemars.royalgold.core.ui.composables

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import com.roycemars.royalgold.core.ui.theme.backgroundGradientDark
import com.roycemars.royalgold.core.ui.theme.backgroundGradientLight

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun BoxWithGradientBackground(
    modifier: Modifier = Modifier,
    contentAlignment: Alignment = Alignment.TopStart,
    content: @Composable BoxScope.() -> Unit) {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val gradientBrush = Brush.linearGradient(
            colors = listOf(backgroundGradientLight, backgroundGradientDark),
            start = Offset.Zero,
            end = Offset(constraints.maxWidth * 0.2f, constraints.maxHeight * 0.1f),
        )
        Box(
            modifier = modifier.background(brush = gradientBrush),
            contentAlignment = contentAlignment
        ) {
            content()
        }
    }
}