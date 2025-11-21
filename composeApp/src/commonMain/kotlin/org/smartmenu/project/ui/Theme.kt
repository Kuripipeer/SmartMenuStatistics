package org.smartmenu.project.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun SmartMenuTheme(
    content: @Composable () -> Unit
) {
    // You can define your theme colors, typography, and shapes here
    MaterialTheme(
        content = content
    )
}