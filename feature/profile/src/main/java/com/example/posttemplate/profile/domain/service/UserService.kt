package com.example.posttemplate.profile.domain.service

import arrow.core.Either
import arrow.core.getOrHandle
import com.example.posttemplate.profile.data.repository.UserRepository
import com.example.posttemplate.profile.domain.extensions.toDomain
import com.example.posttemplate.profile.domain.model.User

interface UserService {
    suspend fun getUserById(id: Int): Either<Throwable, User>

    companion object {
        fun create(userRepository: UserRepository): UserService =
            UserServiceImpl(userRepository)
    }
}

private class UserServiceImpl(
    private val userRepository: UserRepository
) : UserService {

    override suspend fun getUserById(id: Int): Either<Throwable, User> =
        com.example.posttemplate.util.check {
            val userDto = userRepository.getUserById(id).getOrHandle { throw it }
            userDto.toDomain()
        }
}
