package com.example.posttemplate.auth.di

import io.github.ciriti.auth.data.repository.AuthRepository
import io.github.ciriti.auth.data.repository.create
import io.github.ciriti.auth.ui.AuthenticationViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val authModule = module {

    // Repository
    single { AuthRepository.create(sharedPreferences = get()) }

    // ViewModels
    viewModel { AuthenticationViewModel(authRepository = get()) }
}
