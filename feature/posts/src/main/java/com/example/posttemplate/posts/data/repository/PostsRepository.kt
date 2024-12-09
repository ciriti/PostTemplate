package com.example.posttemplate.posts.data.repository

import com.example.posttemplate.data.local.PostDao
import com.example.posttemplate.data.local.PostEntity
import com.example.posttemplate.data.models.PostDto
import com.example.posttemplate.data.remote.ApiService

interface PostsRepository {
    suspend fun getPosts(): Result<List<PostDto>>
    suspend fun getPostById(id: Int): Result<PostDto>

    companion object
}

fun PostsRepository.Companion.create(apiService: ApiService, postDao: PostDao): PostsRepository =
    PostsRepositoryImpl(apiService, postDao)

private class PostsRepositoryImpl(
    private val apiService: ApiService,
    private val postDao: PostDao
) : PostsRepository {

    override suspend fun getPosts(): Result<List<PostDto>> =
        kotlin.runCatching {
            // Check local cache
            val cachedPosts = postDao.getAllPosts().map { it.toDto() }
            if (cachedPosts.isNotEmpty()) {
                return@runCatching cachedPosts
            }

            // Fetch from remote
            val remotePosts = apiService.getPosts()
            // Cache locally
            postDao.insertPosts(remotePosts.map { it.toEntity() })
            remotePosts
        }

    override suspend fun getPostById(id: Int): Result<PostDto> =
        kotlin.runCatching {
            // Check local cache
            val cachedPost = postDao.getPostById(id)?.toDto()
            if (cachedPost != null) {
                return@runCatching cachedPost
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
