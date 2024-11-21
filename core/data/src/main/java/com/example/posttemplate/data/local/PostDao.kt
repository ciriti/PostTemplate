package com.example.posttemplate.data.local

import java.util.concurrent.ConcurrentHashMap

interface PostDao {
    suspend fun getAllPosts(): List<PostEntity>
    suspend fun getPostById(id: Int): PostEntity?
    suspend fun insertPosts(posts: List<PostEntity>)

    companion object
}

fun PostDao.Companion.create(postTable: ConcurrentHashMap<Int, PostEntity>): PostDao = MockPostDao(postTable)

private class MockPostDao(
    private val postTable: ConcurrentHashMap<Int, PostEntity>
) : PostDao {

    override suspend fun getAllPosts(): List<PostEntity> {
        return postTable.values.toList()
    }

    override suspend fun getPostById(id: Int): PostEntity? {
        return postTable[id]
    }

    override suspend fun insertPosts(posts: List<PostEntity>) {
        posts.forEach { post ->
            postTable[post.id] = post
        }
    }
}
