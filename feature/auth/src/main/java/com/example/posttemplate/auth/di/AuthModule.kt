package com.example.posttemplate.auth.di

import com.example.posttemplate.auth.ui.AuthenticationViewModel
import com.example.posttemplate.data.repository.AuthRepository
import com.example.posttemplate.data.repository.create
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val authModule = module {

    // Repository
    single { AuthRepository.create(sharedPreferences = get()) }

    // ViewModels
    viewModel { AuthenticationViewModel(authRepository = get()) }
}
