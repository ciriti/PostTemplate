package io.github.ciriti.data.remote

import io.github.ciriti.data.models.PostDto
import io.github.ciriti.data.models.UserDto
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("/posts")
    suspend fun getPosts(): List<PostDto>

    @GET("/posts/{id}")
    suspend fun getPostById(@Path("id") id: Int): PostDto

    @GET("/users/{id}")
    suspend fun getUserById(@Path("id") id: Int): UserDto
}
