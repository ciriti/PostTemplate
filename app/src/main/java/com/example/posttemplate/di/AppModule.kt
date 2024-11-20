package com.example.posttemplate.di

import android.content.Context
import com.example.posttemplate.ui.screen.auth.AuthenticationViewModel
import com.example.posttemplate.domain.services.PostService
import com.example.posttemplate.domain.services.UserService
import com.example.posttemplate.ui.navigation.DrawerViewModel
import com.example.posttemplate.ui.screens.home.HomeViewModel
import com.example.posttemplate.ui.screens.profile.ProfileViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    // Services
    single { PostService.create(get(), get()) }
    single { UserService.create(get()) }

    // ViewModels
    viewModel { HomeViewModel(service = get()) }
    viewModel { ProfileViewModel(service = get()) }
    viewModel { AuthenticationViewModel(authRepository = get()) }
    viewModel { DrawerViewModel(authRepository = get()) }
}
