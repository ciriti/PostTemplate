package com.example.posttemplate.ui.screens.auth

import androidx.lifecycle.viewModelScope
import com.example.posttemplate.data.repository.AuthRepository
import com.example.posttemplate.ui.components.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthenticationViewModel(private val authRepository: AuthRepository) :
    BaseViewModel<AuthenticationState, AuthenticationEffect, AuthenticationIntent>() {

    private val _state = MutableStateFlow(AuthenticationState())
    override val state: StateFlow<AuthenticationState> = _state

    private val _effect = MutableSharedFlow<AuthenticationEffect>()
    override val effect: SharedFlow<AuthenticationEffect> = _effect

    override fun handleIntent(intent: AuthenticationIntent) {
        when (intent) {
            is AuthenticationIntent.Authenticate -> authenticate()
        }
    }

    private fun authenticate() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            delay(2000) // Simulate a 2-second authentication process

            authRepository.setUserSignedIn(true)

            _state.value = _state.value.copy(isLoading = false)
            _effect.emit(AuthenticationEffect.NavigateToHome)
        }
    }

}
