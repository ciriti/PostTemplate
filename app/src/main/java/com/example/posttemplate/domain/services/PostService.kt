package com.example.posttemplate.domain.services

import arrow.core.Either
import arrow.core.getOrHandle
import com.example.posttemplate.data.repository.PostRepository
import com.example.posttemplate.data.repository.UserRepository
import com.example.posttemplate.domain.extensions.toDomain
import com.example.posttemplate.domain.models.Post
import com.example.posttemplate.util.check

interface PostService {
    suspend fun getPosts(): Either<Throwable, List<Post>>
    suspend fun getPostById(id: Int): Either<Throwable, Post>

    companion object {
        fun create(postRepository: PostRepository, userRepository: UserRepository): PostService =
            PostServiceImpl(postRepository, userRepository)
    }
}

private class PostServiceImpl(
    private val postRepository: PostRepository,
    private val userRepository: UserRepository
) : PostService {

    override suspend fun getPosts(): Either<Throwable, List<Post>> = check {
        val postsDto = postRepository.getPosts().getOrHandle { throw it }
        postsDto.map { postDto ->
            postDto.toDomain(postDto.userId)
        }
    }

    override suspend fun getPostById(id: Int): Either<Throwable, Post> = check {
        val postDto = postRepository.getPostById(id).getOrHandle { throw it }
        postDto.toDomain(postDto.userId)
    }

}
