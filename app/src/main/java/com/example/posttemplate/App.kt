package io.github.ciriti

import android.app.Application
import io.github.ciriti.data.di.dataModule
import io.github.ciriti.di.appModule
import io.github.ciriti.auth.di.authModule
import io.github.ciriti.posts.di.postsModule
import io.github.ciriti.profile.di.profileModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(dataModule)
            modules(authModule)
            modules(profileModule)
            modules(postsModule)
            modules(appModule)
        }
    }
}
