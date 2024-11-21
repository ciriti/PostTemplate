package com.example.posttemplate.data.di

import android.content.Context
import com.example.posttemplate.data.local.AppDatabase
import com.example.posttemplate.data.local.PostDao
import com.example.posttemplate.data.local.UserDao
import com.example.posttemplate.data.local.create
import com.example.posttemplate.data.remote.ApiService
import com.example.posttemplate.data.remote.NetworkClient
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
