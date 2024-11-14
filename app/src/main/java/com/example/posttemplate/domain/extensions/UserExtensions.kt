package com.example.posttemplate.domain.extensions

import com.example.posttemplate.data.local.UserEntity
import com.example.posttemplate.data.models.UserDto
import com.example.posttemplate.domain.models.User
import com.example.posttemplate.utils.fail

fun UserDto.toDomain(): User {
    return User(
        id = this.id,
        fullName = this.name ?: fail("Name is missing"),
        email = this.email ?: fail("Email is missing")
    )
}

fun UserEntity.toDomain(): User {
    return User(
        id = this.id,
        fullName = this.name,
        email = this.email
    )
}

fun User.toEntity(): UserEntity {
    return UserEntity(
        id = this.id,
        name = this.fullName,
        email = this.email
    )
}
