package com.example.posttemplate.auth.data.repository

import android.content.SharedPreferences

interface AuthRepository {
    fun isUserSignedIn(): Boolean
    fun setUserSignedIn(isSignedIn: Boolean)

    companion object
}

fun AuthRepository.Companion.create(sharedPreferences: SharedPreferences): AuthRepository =
    AuthRepositoryImpl(sharedPreferences)

private class AuthRepositoryImpl(
    private val sharedPreferences: SharedPreferences
) : AuthRepository {

    override fun isUserSignedIn(): Boolean = sharedPreferences.getBoolean("is_signed_in", false)

    override fun setUserSignedIn(isSignedIn: Boolean) =
        sharedPreferences.edit().putBoolean("is_signed_in", isSignedIn).apply()
}
