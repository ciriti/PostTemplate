package com.example.posttemplate.ui.screens.profile

import com.example.posttemplate.domain.models.User

data class ProfileState(
    val isLoading: Boolean = false,
    val user: User? = null,
    val errorMessage: String? = null
)

sealed class ProfileIntent {
    data class LoadProfile(val userId: Int) : ProfileIntent()
}

sealed class ProfileEffect {
    data class ShowError(val message: String) : ProfileEffect()
}
