package com.example.posttemplate.profile.domain.extensions

import com.example.posttemplate.data.local.AddressEntity
import com.example.posttemplate.data.local.CompanyEntity
import com.example.posttemplate.data.local.UserEntity
import com.example.posttemplate.data.models.AddressDto
import com.example.posttemplate.data.models.CompanyDto
import com.example.posttemplate.data.models.UserDto
import com.example.posttemplate.profile.domain.model.Address
import com.example.posttemplate.profile.domain.model.Company
import com.example.posttemplate.profile.domain.model.User
import com.example.posttemplate.util.fail

fun UserDto.toDomain(): User {
    return User(
        id = this.id,
        fullName = this.name ?: "Name is missing".fail(),
        email = this.email ?: "Email is missing".fail(),
        address = this.address?.toDomain(),
        phone = this.phone ?: "N/A",
        website = this.website ?: "N/A",
        company = this.company?.toDomain()
    )
}

fun AddressDto.toDomain(): Address {
    return Address(
        street = this.street ?: "N/A",
        suite = this.suite ?: "N/A",
        city = this.city ?: "N/A",
        zipcode = this.zipcode ?: "N/A"
    )
}

fun CompanyDto.toDomain(): Company {
    return Company(
        name = this.name ?: "N/A",
        catchPhrase = this.catchPhrase ?: "N/A",
        bs = this.bs ?: "N/A"
    )
}

fun User.toEntity(): UserEntity {
    return UserEntity(
        id = this.id,
        name = this.fullName,
        email = this.email,
        address = this.address?.toEntity(),
        phone = this.phone,
        website = this.website,
        company = this.company?.toEntity()
    )
}

fun Address.toEntity(): AddressEntity {
    return AddressEntity(
        street = this.street,
        suite = this.suite,
        city = this.city,
        zipcode = this.zipcode
    )
}

fun Company.toEntity(): CompanyEntity {
    return CompanyEntity(
        name = this.name,
        catchPhrase = this.catchPhrase,
        bs = this.bs
    )
}
