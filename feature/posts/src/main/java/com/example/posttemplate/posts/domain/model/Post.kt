package io.github.ciriti.posts.domain.model

data class Post(
    val id: Int,
    val title: String,
    val body: String,
    val authorId: Int
)
