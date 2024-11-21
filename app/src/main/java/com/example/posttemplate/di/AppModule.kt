package com.example.posttemplate.di

import com.example.posttemplate.ui.screen.auth.AuthenticationViewModel
import com.example.posttemplate.domain.services.UserService
import com.example.posttemplate.ui.navigation.DrawerViewModel
import com.example.posttemplate.posts.ui.HomeViewModel
import com.example.posttemplate.ui.screens.profile.ProfileViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    // Services
    single { com.example.posttemplate.posts.domain.service.PostService.create(get(), get()) }
    single { UserService.create(get()) }

    // ViewModels
    viewModel { HomeViewModel(service = get()) }
    viewModel { ProfileViewModel(service = get()) }
    viewModel { AuthenticationViewModel(authRepository = get()) }
    viewModel { DrawerViewModel(authRepository = get()) }
}
