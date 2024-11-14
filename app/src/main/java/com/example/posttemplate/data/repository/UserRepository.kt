package com.example.posttemplate.data.repository

import arrow.core.Either
import com.example.posttemplate.data.local.UserDao
import com.example.posttemplate.data.local.UserEntity
import com.example.posttemplate.data.models.UserDto
import com.example.posttemplate.data.remote.ApiService
import com.example.posttemplate.utils.check

interface UserRepository {
    suspend fun getUserById(id: Int): Either<Throwable, UserDto>

    companion object {
        fun create(apiService: ApiService, userDao: UserDao): UserRepository =
            UserRepositoryImpl(apiService, userDao)
    }
}

private class UserRepositoryImpl(
    private val apiService: ApiService,
    private val userDao: UserDao
) : UserRepository {

    override suspend fun getUserById(id: Int): Either<Throwable, UserDto> = check {
        // Check local cache
        val cachedUser = userDao.getUserById(id)?.toDto()
        if (cachedUser != null) {
            return@check cachedUser
        }

        // Fetch from remote
        val remoteUser = apiService.getUserById(id)
        // Cache locally
        userDao.insertUser(remoteUser.toEntity())
        remoteUser
    }
}

// Extension Functions for Entity <-> DTO Conversions
private fun UserEntity.toDto(): UserDto = UserDto(id, name, email)

private fun UserDto.toEntity(): UserEntity = UserEntity(id, name ?: "", email ?: "")
