package com.example.posttemplate.posts.di

import com.example.posttemplate.posts.data.repository.PostsRepository
import com.example.posttemplate.posts.data.repository.create
import com.example.posttemplate.posts.domain.service.PostsService
import com.example.posttemplate.posts.ui.HomeViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val postsModule = module {
    // Repository
    single { PostsRepository.create(apiService = get(), postDao = get()) }

    // Service
    single { PostsService.create(postRepository = get()) }

    // ViewModels
    viewModel { HomeViewModel(service = get()) }
}
