package io.github.ciriti.ui.components

import androidx.compose.material3.DrawerState
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.runtime.Composable

@Composable
fun AdaptiveNavigationDrawer(
    isLargeScreen: Boolean,
    drawerState: DrawerState,
    drawerContent: @Composable () -> Unit,
    content: @Composable () -> Unit
) {
    if (isLargeScreen) {
        PermanentNavigationDrawer(
            drawerContent = { drawerContent() }
        ) {
            content()
        }
    } else {
        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = { drawerContent() }
        ) {
            content()
        }
    }
}
