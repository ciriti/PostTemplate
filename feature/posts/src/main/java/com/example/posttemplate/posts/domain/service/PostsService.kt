package com.example.posttemplate.posts.domain.service

import com.example.posttemplate.posts.data.repository.PostsRepository
import com.example.posttemplate.posts.domain.extensions.toDomain
import com.example.posttemplate.posts.domain.model.Post

interface PostsService {
    suspend fun getPosts(): Result<List<Post>>
    suspend fun getPostById(id: Int): Result<Post>

    companion object {
        fun create(postRepository: PostsRepository): PostsService =
            PostsServiceImpl(postRepository)
    }
}

private class PostsServiceImpl(
    private val postRepository: PostsRepository
) : PostsService {

    override suspend fun getPosts(): Result<List<Post>> =
        runCatching {
            val postsDto = postRepository.getPosts().getOrElse { throw it }
            postsDto.map { postDto ->
                postDto.toDomain(postDto.userId)
            }
        }

    override suspend fun getPostById(id: Int): Result<Post> =
        runCatching {
            val postDto = postRepository.getPostById(id).getOrElse { throw it }
            postDto.toDomain(postDto.userId)
        }
}
