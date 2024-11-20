package com.example.posttemplate.data.models

data class PostDto(
    val id: Int,
    val title: String?,
    val body: String?,
    val userId: Int
)
