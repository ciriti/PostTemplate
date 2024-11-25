package io.github.ciriti.posts.data.repository

import arrow.core.Either
import io.github.ciriti.data.local.PostDao
import io.github.ciriti.data.local.PostEntity
import io.github.ciriti.data.models.PostDto
import io.github.ciriti.data.remote.ApiService

interface PostsRepository {
    suspend fun getPosts(): Either<Throwable, List<PostDto>>
    suspend fun getPostById(id: Int): Either<Throwable, PostDto>

    companion object
}

fun PostsRepository.Companion.create(apiService: ApiService, postDao: PostDao): PostsRepository =
    PostsRepositoryImpl(apiService, postDao)

private class PostsRepositoryImpl(
    private val apiService: ApiService,
    private val postDao: PostDao
) : PostsRepository {

    override suspend fun getPosts(): Either<Throwable, List<PostDto>> =
        io.github.ciriti.util.check {
            // Check local cache
            val cachedPosts = postDao.getAllPosts().map { it.toDto() }
            if (cachedPosts.isNotEmpty()) {
                return@check cachedPosts
            }

            // Fetch from remote
            val remotePosts = apiService.getPosts()
            // Cache locally
            postDao.insertPosts(remotePosts.map { it.toEntity() })
            remotePosts
        }

    override suspend fun getPostById(id: Int): Either<Throwable, PostDto> =
        io.github.ciriti.util.check {
            // Check local cache
            val cachedPost = postDao.getPostById(id)?.toDto()
            if (cachedPost != null) {
                return@check cachedPost
            }

            // Fetch from remote
            val remotePost = apiService.getPostById(id)
            // Cache locally
            postDao.insertPosts(listOf(remotePost.toEntity()))
            remotePost
        }
}

private fun PostEntity.toDto(): PostDto = PostDto(id, title, content, authorId)

private fun PostDto.toEntity(): PostEntity = PostEntity(id, title ?: "", body ?: "", userId)
