package com.example.posttemplate.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration

@Composable
fun isLargeScreen(): Boolean {
    val configuration = LocalConfiguration.current
    return configuration.screenWidthDp >= 600 // Threshold for "large screens"
}
