package com.example.posttemplate

import com.example.posttemplate.auth.di.authModule
import com.example.posttemplate.data.di.dataModule
import com.example.posttemplate.di.appModule
import com.example.posttemplate.posts.di.postsModule
import com.example.posttemplate.profile.di.profileModule
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
