package com.example.posttemplate.profile.domain.model

data class User(
    val id: Int,
    val fullName: String,
    val email: String,
    val address: Address? = null,
    val phone: String,
    val website: String,
    val company: Company? = null
)

data class Address(
    val street: String,
    val suite: String,
    val city: String,
    val zipcode: String
)

data class Company(
    val name: String,
    val catchPhrase: String,
    val bs: String
)
