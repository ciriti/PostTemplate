package com.example.posttemplate

import android.app.Application
import com.example.posttemplate.data.dataModule
import com.example.posttemplate.di.appModule
import com.example.posttemplate.ui.screen.auth.authModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(dataModule)
            modules(authModule)
            modules(appModule)
        }
    }
}
