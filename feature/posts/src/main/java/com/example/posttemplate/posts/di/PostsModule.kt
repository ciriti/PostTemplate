package io.github.ciriti.posts.di

import io.github.ciriti.posts.data.repository.PostsRepository
import io.github.ciriti.posts.data.repository.create
import io.github.ciriti.posts.domain.service.PostsService
import io.github.ciriti.posts.ui.HomeViewModel
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
