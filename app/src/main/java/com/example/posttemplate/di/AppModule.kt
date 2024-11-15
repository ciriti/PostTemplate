package com.example.posttemplate.di

import com.example.posttemplate.data.local.AppDatabase
import com.example.posttemplate.data.local.PostDao
import com.example.posttemplate.data.local.UserDao
import com.example.posttemplate.data.local.create
import com.example.posttemplate.data.remote.ApiService
import com.example.posttemplate.data.remote.NetworkClient
import com.example.posttemplate.data.repository.PostRepository
import com.example.posttemplate.data.repository.UserRepository
import com.example.posttemplate.domain.services.PostService
import com.example.posttemplate.domain.services.UserService
import com.example.posttemplate.ui.screens.auth.AuthenticationViewModel
import com.example.posttemplate.ui.screens.home.HomeViewModel
import com.example.posttemplate.ui.screens.profile.ProfileViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<AppDatabase> { AppDatabase.create() }

    single<PostDao> { get<AppDatabase>().postDao() }
    single<UserDao> { get<AppDatabase>().userDao() }


    single<ApiService> { NetworkClient.apiService }

    // Repositories
    single { PostRepository.create(get(), get()) }
    single { UserRepository.create(get(), get()) }

    // Services
    single { PostService.create(get(), get()) }
    single { UserService.create(get()) }

    // ViewModels
    viewModel { HomeViewModel(service = get()) }
    viewModel { ProfileViewModel(service = get()) }
    viewModel { AuthenticationViewModel() }
}
