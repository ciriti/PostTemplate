package io.github.ciriti.data.local

data class UserEntity(
    val id: Int,
    val name: String,
    val email: String,
    val address: AddressEntity?,
    val phone: String,
    val website: String,
    val company: CompanyEntity?
)

data class AddressEntity(
    val street: String,
    val suite: String,
    val city: String,
    val zipcode: String
)

data class CompanyEntity(
    val name: String,
    val catchPhrase: String,
    val bs: String
)
