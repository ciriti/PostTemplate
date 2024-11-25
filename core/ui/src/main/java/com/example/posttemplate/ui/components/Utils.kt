package io.github.ciriti.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration

@Composable
fun isLargeScreen(): Boolean {
    val configuration = LocalConfiguration.current
    return configuration.screenWidthDp >= 600
}
