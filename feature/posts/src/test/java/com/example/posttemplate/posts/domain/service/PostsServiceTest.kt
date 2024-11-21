package com.example.posttemplate.posts.domain.service

import arrow.core.Either
import com.example.posttemplate.data.models.PostDto
import com.example.posttemplate.posts.data.repository.PostsRepository
import com.example.posttemplate.posts.domain.model.Post
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class PostsServiceTest {

    private val mockPostRepository: PostsRepository = mockk()

    private val postService: PostsService =
        PostsService.create(mockPostRepository)

    private val postId = 1
    private val userId = 1
    private val postDto = PostDto(
        id = postId,
        title = "Test Post",
        body = "This is a test post",
        userId = userId
    )
    private val post = Post(
        id = postId,
        title = "Test Post",
        body = "This is a test post",
        authorId = userId
    )

    @Test
    fun `getPosts should return a list of posts`() = runBlocking {
        // Arrange
        coEvery { mockPostRepository.getPosts() } returns Either.Right(listOf(postDto))

        // Act
        val result = postService.getPosts()

        // Assert
        assertEquals(Either.Right(listOf(post)), result)
        coVerify { mockPostRepository.getPosts() }
    }

    @Test
    fun `getPosts should handle errors from PostRepository`() = runBlocking {
        // Arrange
        val exception = RuntimeException("Error fetching posts")
        coEvery { mockPostRepository.getPosts() } returns Either.Left(exception)

        // Act
        val result = postService.getPosts()

        // Assert
        assert(result.isLeft())
        assertEquals(exception, (result as Either.Left).value.cause)
    }

    @Test
    fun `getPostById should return a post`() = runBlocking {
        // Arrange
        coEvery { mockPostRepository.getPostById(postId) } returns Either.Right(postDto)

        // Act
        val result = postService.getPostById(postId)

        // Assert
        assertEquals(Either.Right(post), result)
        coVerify { mockPostRepository.getPostById(postId) }
    }

    @Test
    fun `getPostById should handle errors from PostRepository`() = runBlocking {
        // Arrange
        val exception = RuntimeException("Error fetching post by ID")
        coEvery { mockPostRepository.getPostById(postId) } returns Either.Left(exception)

        // Act
        val result = postService.getPostById(postId)

        // Assert
        assert(result.isLeft())
        assertEquals(exception, (result as Either.Left).value.cause)
    }
}
