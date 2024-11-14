package com.example.posttemplate.domain.extensions

import com.example.posttemplate.data.local.PostEntity
import com.example.posttemplate.data.models.PostDto
import com.example.posttemplate.domain.models.Post
import com.example.posttemplate.domain.models.User
import com.example.posttemplate.utils.fail


fun PostDto.toDomain(user: User): Post {
    return Post(
        id = this.id,
        title = this.title ?: fail("Title is missing"),
        body = this.content ?: fail("Content is missing"),
        author = user.fullName
    )
}

// Convert PostEntity to Post domain model
fun PostEntity.toDomain(user: User): Post {
    return Post(
        id = this.id,
        title = this.title,
        body = this.content,
        author = user.fullName
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
