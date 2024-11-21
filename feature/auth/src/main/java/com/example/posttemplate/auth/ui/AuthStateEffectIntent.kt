package com.example.posttemplate.ui.screen.auth

data class AuthenticationState(
    val isLoading: Boolean = false
)

sealed class AuthenticationEffect {
    object NavigateToHome : AuthenticationEffect()
}

sealed class AuthenticationIntent {
    object Authenticate : AuthenticationIntent()
}
