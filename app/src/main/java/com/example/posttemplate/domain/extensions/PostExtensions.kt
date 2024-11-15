package com.example.posttemplate.domain.extensions

import com.example.posttemplate.data.local.PostEntity
import com.example.posttemplate.data.models.PostDto
import com.example.posttemplate.domain.models.Post
import com.example.posttemplate.utils.fail


fun PostDto.toDomain(authorId: Int): Post {
    return Post(
        id = this.id,
        title = this.title ?: "Title is missing".fail(),
        body = this.body ?: "",
        authorId = authorId
    )
}

// Convert PostEntity to Post domain model
fun PostEntity.toDomain(authorId: Int): Post {
    return Post(
        id = this.id,
        title = this.title,
        body = this.content,
        authorId = authorId
    )
}

// Convert Post domain model to PostEntity
fun Post.toEntity(authorId: Int): PostEntity {
    return PostEntity(
        id = this.id,
        title = this.title,
        content = this.body,
        authorId = authorId
    )
}
