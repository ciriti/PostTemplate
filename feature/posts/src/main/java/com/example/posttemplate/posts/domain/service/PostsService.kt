package io.github.ciriti.posts.domain.service

import arrow.core.Either
import arrow.core.getOrHandle
import io.github.ciriti.posts.data.repository.PostsRepository
import io.github.ciriti.posts.domain.extensions.toDomain
import io.github.ciriti.posts.domain.model.Post
import io.github.ciriti.util.check

interface PostsService {
    suspend fun getPosts(): Either<Throwable, List<Post>>
    suspend fun getPostById(id: Int): Either<Throwable, Post>

    companion object {
        fun create(postRepository: PostsRepository): PostsService =
            PostsServiceImpl(postRepository)
    }
}

private class PostsServiceImpl(
    private val postRepository: PostsRepository
) : PostsService {

    override suspend fun getPosts(): Either<Throwable, List<Post>> =
        check {
            val postsDto = postRepository.getPosts().getOrHandle { throw it }
            postsDto.map { postDto ->
                postDto.toDomain(postDto.userId)
            }
        }

    override suspend fun getPostById(id: Int): Either<Throwable, Post> =
        check {
            val postDto = postRepository.getPostById(id).getOrHandle { throw it }
            postDto.toDomain(postDto.userId)
        }
}
