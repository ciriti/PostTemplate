package com.example.posttemplate.posts.utils

import com.example.posttemplate.data.local.PostEntity
import com.example.posttemplate.data.models.PostDto
import com.example.posttemplate.posts.domain.model.Post
import com.example.posttemplate.util.fail


fun PostDto.toDomain(authorId: Int): Post {
    return Post(
        id = this.id,
        title = this.title ?: "Title is missing".fail(),
        body = this.body ?: "",
        authorId = authorId
    )
}

fun PostEntity.toDomain(authorId: Int): Post {
    return Post(
        id = this.id,
        title = this.title,
        body = this.content,
        authorId = authorId
    )
}

fun Post.toEntity(authorId: Int): PostEntity {
    return PostEntity(
        id = this.id,
        title = this.title,
        content = this.body,
        authorId = authorId
    )
}
