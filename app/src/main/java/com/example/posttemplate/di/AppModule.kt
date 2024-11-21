package com.example.posttemplate.di

import com.example.posttemplate.ui.navigation.DrawerViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { DrawerViewModel(authRepository = get()) }
}
