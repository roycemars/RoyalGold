package com.roycemars.royalgold.view.theme

// In app/src/main/java/com/roycemars/royalgold/ui/theme/Shape.kt

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

val AppShapes = Shapes( // Changed from 'Shapes' to 'AppShapes' or any other variable name
    small = RoundedCornerShape(4.dp),
    medium = RoundedCornerShape(8.dp),
    large = RoundedCornerShape(16.dp)
)