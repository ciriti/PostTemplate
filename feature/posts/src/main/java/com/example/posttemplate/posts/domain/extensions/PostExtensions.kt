package io.github.ciriti.posts.domain.extensions

import io.github.ciriti.data.local.PostEntity
import io.github.ciriti.data.models.PostDto
import io.github.ciriti.posts.domain.model.Post
import io.github.ciriti.util.fail

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
