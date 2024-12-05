package io.github.ciriti.auth.ui

data class AuthenticationState(
    val isLoading: Boolean = false
)

sealed class AuthenticationEffect {
    object NavigateToHome : AuthenticationEffect()
}

sealed class AuthenticationIntent {
    object Authenticate : AuthenticationIntent()
}
