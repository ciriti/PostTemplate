package com.example.posttemplate.data.models

data class UserDto(
    val id: Int,
    val name: String?,
    val email: String?,
    val address: AddressDto?,
    val phone: String?,
    val website: String?,
    val company: CompanyDto?
)

data class AddressDto(
    val street: String?,
    val suite: String?,
    val city: String?,
    val zipcode: String?
)

data class CompanyDto(
    val name: String?,
    val catchPhrase: String?,
    val bs: String?
)
