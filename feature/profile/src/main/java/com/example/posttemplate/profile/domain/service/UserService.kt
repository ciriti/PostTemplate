package io.github.ciriti.profile.domain.service

import arrow.core.Either
import arrow.core.getOrHandle
import io.github.ciriti.profile.data.repository.UserRepository
import io.github.ciriti.profile.domain.extensions.toDomain
import io.github.ciriti.profile.domain.model.User

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
        io.github.ciriti.util.check {
            val userDto = userRepository.getUserById(id).getOrHandle { throw it }
            userDto.toDomain()
        }
}
