package com.example.posttemplate.data.remote

import com.example.posttemplate.data.models.PostDto
import com.example.posttemplate.data.models.UserDto
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
