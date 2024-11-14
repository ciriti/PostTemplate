package com.example.posttemplate.data.models

data class PostDto(
    val id: Int,
    val title: String?,
    val content: String?,
    val authorId: Int
)
