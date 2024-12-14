package com.example.posttemplate.profile.ui

import androidx.lifecycle.viewModelScope
import com.example.posttemplate.profile.domain.service.UserService
import com.example.posttemplate.ui.components.BaseViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val service: UserService
) : BaseViewModel<ProfileState, ProfileEffect, ProfileIntent>() {

    private val _state = MutableStateFlow(ProfileState())
    override val state: StateFlow<ProfileState> = _state

    private val _effect = MutableSharedFlow<ProfileEffect>()
    override val effect: SharedFlow<ProfileEffect> = _effect

    override fun handleIntent(intent: ProfileIntent) {
        when (intent) {
            is ProfileIntent.LoadProfile -> loadUser(intent.userId)
        }
    }

    private fun loadUser(userId: Int) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            service.getUserById(userId).fold(
                onFailure = { throwable ->
                    _state.value = _state.value.copy(isLoading = false, errorMessage = throwable.message)
                    _effect.emit(ProfileEffect.ShowError(throwable.message ?: "Unknown error"))
                },
                onSuccess = { user ->
                    _state.value = _state.value.copy(isLoading = false, user = user, errorMessage = null)
                }
            )
        }
    }
}
