package io.github.ciriti.di

import io.github.ciriti.ui.navigation.DrawerViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { DrawerViewModel(authRepository = get()) }
}
