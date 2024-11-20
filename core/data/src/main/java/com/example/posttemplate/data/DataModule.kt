package com.example.posttemplate.data

import android.content.Context
import com.example.posttemplate.data.local.AppDatabase
import com.example.posttemplate.data.local.PostDao
import com.example.posttemplate.data.local.UserDao
import com.example.posttemplate.data.local.create
import com.example.posttemplate.data.remote.ApiService
import com.example.posttemplate.data.remote.NetworkClient
import com.example.posttemplate.data.repository.AuthRepository
import com.example.posttemplate.data.repository.PostRepository
import com.example.posttemplate.data.repository.UserRepository
import com.example.posttemplate.data.repository.create
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module


val dataModule = module {

    single {
        androidContext().getSharedPreferences("auth_preferences", Context.MODE_PRIVATE)
    }

    single<AppDatabase> { AppDatabase.create() }

    single<PostDao> { get<AppDatabase>().postDao() }
    single<UserDao> { get<AppDatabase>().userDao() }

    // Api
    single<ApiService> { NetworkClient.apiService }


    // Repositories
    single { AuthRepository.create(sharedPreferences = get()) }
    single { PostRepository.create(get(), get()) }
    single { UserRepository.create(get(), get()) }

}
