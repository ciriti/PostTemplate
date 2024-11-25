package io.github.ciriti

import io.github.ciriti.auth.di.authModule
import io.github.ciriti.data.di.dataModule
import io.github.ciriti.di.appModule
import io.github.ciriti.posts.di.postsModule
import io.github.ciriti.profile.di.profileModule
import org.junit.Test
import org.koin.android.test.verify.androidVerify
import org.koin.test.KoinTest

class CheckModulesTest : KoinTest {

    @Test
    fun checkAllModules() {
        dataModule.androidVerify()
        authModule.androidVerify()
        profileModule.androidVerify()
        postsModule.androidVerify()
//        appModule.androidVerify()
    }
}
