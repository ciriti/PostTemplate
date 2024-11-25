package io.github.ciriti.profile.di

import io.github.ciriti.profile.data.repository.UserRepository
import io.github.ciriti.profile.domain.service.UserService
import io.github.ciriti.profile.ui.ProfileViewModel
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
