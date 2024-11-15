package com.example.posttemplate.ui.screens.auth

import com.example.posttemplate.ui.components.BaseViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

class MockAuthenticationViewModel :
    BaseViewModel<AuthenticationState, AuthenticationEffect, AuthenticationIntent>() {

    private val _state = MutableStateFlow(AuthenticationState(isLoading = true))
    override val state: StateFlow<AuthenticationState> = _state

    private val _effect = MutableSharedFlow<AuthenticationEffect>()
    override val effect: SharedFlow<AuthenticationEffect> = _effect

    override fun handleIntent(intent: AuthenticationIntent) {}
}
