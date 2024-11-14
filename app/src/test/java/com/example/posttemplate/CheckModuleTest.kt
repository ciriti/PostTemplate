package com.example.posttemplate

import com.example.posttemplate.di.appModule
import org.junit.Test
import org.koin.android.test.verify.androidVerify
import org.koin.test.KoinTest

class CheckModulesTest : KoinTest {

    @Test
    fun checkAllModules(){
        appModule.androidVerify()
    }
}
