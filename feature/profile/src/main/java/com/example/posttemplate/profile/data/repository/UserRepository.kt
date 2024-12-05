package io.github.ciriti.profile.data.repository

import arrow.core.Either
import io.github.ciriti.data.local.AddressEntity
import io.github.ciriti.data.local.CompanyEntity
import io.github.ciriti.data.local.UserDao
import io.github.ciriti.data.local.UserEntity
import io.github.ciriti.data.models.AddressDto
import io.github.ciriti.data.models.CompanyDto
import io.github.ciriti.data.models.UserDto
import io.github.ciriti.data.remote.ApiService
import io.github.ciriti.util.check

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

fun UserEntity.toDto(): UserDto {
    return UserDto(
        id = this.id,
        name = this.name.ifEmpty { null },
        email = this.email.ifEmpty { null },
        address = this.address?.toDto(),
        phone = this.phone.ifEmpty { null },
        website = this.website.ifEmpty { null },
        company = this.company?.toDto()
    )
}

fun AddressEntity.toDto(): AddressDto {
    return AddressDto(
        street = this.street.ifEmpty { null },
        suite = this.suite.ifEmpty { null },
        city = this.city.ifEmpty { null },
        zipcode = this.zipcode.ifEmpty { null }
    )
}

fun CompanyEntity.toDto(): CompanyDto {
    return CompanyDto(
        name = this.name.ifEmpty { null },
        catchPhrase = this.catchPhrase.ifEmpty { null },
        bs = this.bs.ifEmpty { null }
    )
}

fun UserDto.toEntity(): UserEntity {
    return UserEntity(
        id = this.id,
        name = this.name ?: "Unknown",
        email = this.email ?: "Unknown",
        address = this.address?.toEntity(),
        phone = this.phone ?: "Unknown",
        website = this.website ?: "Unknown",
        company = this.company?.toEntity()
    )
}

fun AddressDto.toEntity(): AddressEntity {
    return AddressEntity(
        street = this.street ?: "Unknown",
        suite = this.suite ?: "Unknown",
        city = this.city ?: "Unknown",
        zipcode = this.zipcode ?: "Unknown"
    )
}

fun CompanyDto.toEntity(): CompanyEntity {
    return CompanyEntity(
        name = this.name ?: "Unknown",
        catchPhrase = this.catchPhrase ?: "Unknown",
        bs = this.bs ?: "Unknown"
    )
}
