package com.example.posttemplate.data.repository

import arrow.core.Either
import com.example.posttemplate.data.local.AddressEntity
import com.example.posttemplate.data.local.CompanyEntity
import com.example.posttemplate.data.local.UserDao
import com.example.posttemplate.data.local.UserEntity
import com.example.posttemplate.data.models.AddressDto
import com.example.posttemplate.data.models.CompanyDto
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
        name = this.name ?: "Unknown", // Default for name
        email = this.email ?: "Unknown", // Default for email
        address = this.address?.toEntity(),
        phone = this.phone ?: "Unknown", // Default for phone
        website = this.website ?: "Unknown", // Default for website
        company = this.company?.toEntity()
    )
}

fun AddressDto.toEntity(): AddressEntity {
    return AddressEntity(
        street = this.street ?: "Unknown", // Default for street
        suite = this.suite ?: "Unknown", // Default for suite
        city = this.city ?: "Unknown", // Default for city
        zipcode = this.zipcode ?: "Unknown" // Default for zipcode
    )
}

fun CompanyDto.toEntity(): CompanyEntity {
    return CompanyEntity(
        name = this.name ?: "Unknown", // Default for name
        catchPhrase = this.catchPhrase ?: "Unknown", // Default for catchPhrase
        bs = this.bs ?: "Unknown" // Default for bs
    )
}
