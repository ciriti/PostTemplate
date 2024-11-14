package com.example.posttemplate.ui.screens.profile

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.posttemplate.ui.components.LoadingIndicator

@Composable
fun ProfileScreen(
    state: ProfileState,
    onBack: () -> Unit
) {
    when {
        state.isLoading -> LoadingIndicator()
        state.errorMessage != null -> Text("Error: ${state.errorMessage}")
        else -> Text("Hello, ${state.user?.fullName}")
    }
}
