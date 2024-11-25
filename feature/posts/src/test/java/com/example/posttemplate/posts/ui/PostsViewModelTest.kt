package io.github.ciriti.posts.ui

import app.cash.turbine.turbineScope
import arrow.core.Either
import io.github.ciriti.posts.domain.model.Post
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PostsViewModelTest {

    private val mockPostService: io.github.ciriti.posts.domain.service.PostsService = mockk()
    private lateinit var viewModel: HomeViewModel
    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = HomeViewModel(service = mockPostService)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `load posts intent updates state and emits no effect on success`() = testScope.runTest {
        turbineScope {
            // Arrange
            val mockPosts = listOf(
                Post(id = 1, title = "Post 1", body = "Body 1", authorId = 1),
                Post(id = 2, title = "Post 2", body = "Body 2", authorId = 2)
            )
            coEvery { mockPostService.getPosts() } returns Either.Right(mockPosts)

            val states = viewModel.state.testIn(backgroundScope)
            val effects = viewModel.effect.testIn(backgroundScope)

            // Act
            viewModel.handleIntent(HomeIntent.LoadPosts)

            // Assert
            assertEquals(false, states.awaitItem().isLoading) // State during loading
            assertEquals(true, states.awaitItem().isLoading) // State during loading
            assertEquals(
                HomeState(
                    isLoading = false,
                    posts = mockPosts,
                    errorMessage = null
                ),
                states.awaitItem()
            )

            coVerify { mockPostService.getPosts() }

            states.cancel()
            effects.cancel()
        }
    }

    @Test
    fun `load posts intent updates state and emits effect on error`() = testScope.runTest {
        turbineScope {
            // Arrange
            val errorMessage = "Failed to load posts"
            coEvery { mockPostService.getPosts() } returns Either.Left(Exception(errorMessage))

            val states = viewModel.state.testIn(backgroundScope)
            val effects = viewModel.effect.testIn(backgroundScope)

            // Act
            viewModel.handleIntent(HomeIntent.LoadPosts)

            // Assert
            assertEquals(
                HomeState(
                    isLoading = false,
                    posts = emptyList(),
                    errorMessage = null
                ),
                states.awaitItem()
            )
            assertEquals(
                HomeState(
                    isLoading = true,
                    posts = emptyList(),
                    errorMessage = null
                ),
                states.awaitItem()
            )
            assertEquals(
                HomeState(
                    isLoading = false,
                    posts = emptyList(),
                    errorMessage = errorMessage
                ),
                states.awaitItem()
            )
            assertEquals(HomeEffect.ShowError(errorMessage), effects.awaitItem())

            coVerify { mockPostService.getPosts() }

            states.cancel()
            effects.cancel()
        }
    }

    @Test
    fun `select post emits navigate to post details effect`() = testScope.runTest {
        turbineScope {
            // Arrange
            val postId = 1
            val effects = viewModel.effect.testIn(backgroundScope)

            // Act
            viewModel.handleIntent(HomeIntent.SelectPost(postId))

            // Assert
            assertEquals(HomeEffect.NavigateToPostDetails(postId), effects.awaitItem())

            effects.cancel()
        }
    }
}
