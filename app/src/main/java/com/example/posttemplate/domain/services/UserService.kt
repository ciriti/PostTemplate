package com.example.posttemplate.domain.services

import arrow.core.Either
import arrow.core.getOrHandle
import com.example.posttemplate.data.repository.UserRepository
import com.example.posttemplate.domain.extensions.toDomain
import com.example.posttemplate.domain.models.User
import com.example.posttemplate.util.check

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

    override suspend fun getUserById(id: Int): Either<Throwable, User> = check {
        val userDto = userRepository.getUserById(id).getOrHandle { throw it }
        userDto.toDomain()
    }
}
