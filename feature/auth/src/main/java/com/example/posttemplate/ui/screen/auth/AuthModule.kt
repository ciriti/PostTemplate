package com.example.posttemplate.ui.screen.auth

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val authModule = module {
    // ViewModels
    viewModel { AuthenticationViewModel(authRepository = get()) }
}
