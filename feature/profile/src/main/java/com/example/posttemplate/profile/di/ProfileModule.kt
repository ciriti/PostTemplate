package com.example.posttemplate.profile.di

import com.example.posttemplate.profile.data.repository.UserRepository
import com.example.posttemplate.profile.domain.service.UserService
import com.example.posttemplate.profile.ui.ProfileViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val profileModule = module {

    // Repository
    single { UserRepository.create(apiService = get(), userDao = get()) }

    // Services
    single { UserService.create(userRepository = get()) }

    // ViewModels
    viewModel { ProfileViewModel(service = get()) }
}
