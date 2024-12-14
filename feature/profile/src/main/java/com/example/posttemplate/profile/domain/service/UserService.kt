package com.example.posttemplate.profile.domain.service

import com.example.posttemplate.profile.data.repository.UserRepository
import com.example.posttemplate.profile.domain.extensions.toDomain
import com.example.posttemplate.profile.domain.model.User

interface UserService {
    suspend fun getUserById(id: Int): Result<User>

    companion object {
        fun create(userRepository: UserRepository): UserService =
            UserServiceImpl(userRepository)
    }
}

private class UserServiceImpl(
    private val userRepository: UserRepository
) : UserService {

    override suspend fun getUserById(id: Int): Result<User> =
        kotlin.runCatching {
            val userDto = userRepository.getUserById(id).getOrElse { throw it }
            userDto.toDomain()
        }
}
