package com.example.posttemplate.ui.screens.auth

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AuthenticationScreen(
    loadingState: Boolean,
    onButtonClicked: () -> Unit,
    navigateToHome: () -> Unit
) {
    Scaffold(
        modifier = Modifier
            .statusBarsPadding()
            .navigationBarsPadding(),
        content = {
            AuthenticationContent(
                loadingState = loadingState,
                onButtonClicked = {
                    onButtonClicked()
                    navigateToHome()
                }
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewAuthenticationScreen() {
    AuthenticationScreen(
        loadingState = false,
        onButtonClicked = {},
        navigateToHome = {}
    )
}
