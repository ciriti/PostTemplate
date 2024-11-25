package io.github.ciriti.data.di

import android.content.Context
import io.github.ciriti.data.local.AppDatabase
import io.github.ciriti.data.local.PostDao
import io.github.ciriti.data.local.UserDao
import io.github.ciriti.data.local.create
import io.github.ciriti.data.remote.ApiService
import io.github.ciriti.data.remote.NetworkClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataModule = module {

    single {
        androidContext().getSharedPreferences("auth_preferences", Context.MODE_PRIVATE)
    }

    single<AppDatabase> { AppDatabase.create() }

    single<ApiService> { NetworkClient.apiService }

    single<PostDao> { get<AppDatabase>().postDao() }
    single<UserDao> { get<AppDatabase>().userDao() }
}
