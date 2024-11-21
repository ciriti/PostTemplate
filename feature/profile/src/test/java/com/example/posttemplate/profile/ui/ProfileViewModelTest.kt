package com.example.posttemplate.profile.ui

import app.cash.turbine.turbineScope
import arrow.core.Either
import com.example.posttemplate.profile.domain.model.User
import com.example.posttemplate.profile.domain.service.UserService
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
class ProfileViewModelTest {

    private val mockUserService: UserService = mockk()
    private lateinit var viewModel: ProfileViewModel
    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = ProfileViewModel(service = mockUserService)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `load user updates state and emits effect on success`() = testScope.runTest {
        turbineScope {
            // Arrange
            val mockUser = User(
                id = 1,
                fullName = "John Doe",
                email = "johndoe@example.com",
                address = null,
                phone = "123-456-7890",
                website = "www.johndoe.com",
                company = null
            )
            val expectedState =
                ProfileState(isLoading = false, user = mockUser, errorMessage = null)
            coEvery { mockUserService.getUserById(1) } returns Either.Right(mockUser)

            val states = viewModel.state.testIn(backgroundScope)
            val effects = viewModel.effect.testIn(backgroundScope)

            // Act
            viewModel.handleIntent(ProfileIntent.LoadProfile(1))

            // Assert
            assertEquals(false, states.awaitItem().isLoading)
            assertEquals(true, states.awaitItem().isLoading)
            assertEquals(expectedState, states.awaitItem())

            coVerify { mockUserService.getUserById(1) }

            states.cancel()
            effects.cancel()
        }
    }

    @Test
    fun `load user updates state and emits effect on error`() = testScope.runTest {
        turbineScope {
            // Arrange
            val errorMessage = "User not found"
            coEvery { mockUserService.getUserById(1) } returns Either.Left(Exception(errorMessage))
            val states = viewModel.state.testIn(backgroundScope)
            val effects = viewModel.effect.testIn(backgroundScope)

            // Act
            viewModel.handleIntent(ProfileIntent.LoadProfile(1))

            // Assert
            assertEquals(
                ProfileState(isLoading = false, user = null, errorMessage = null),
                states.awaitItem()
            )
            assertEquals(
                ProfileState(isLoading = true, user = null, errorMessage = null),
                states.awaitItem()
            )
            assertEquals(
                ProfileState(isLoading = false, user = null, errorMessage = errorMessage),
                states.awaitItem()
            )
            assertEquals(
                ProfileEffect.ShowError(errorMessage),
                effects.awaitItem()
            )

            coVerify { mockUserService.getUserById(1) }

            states.cancel()
            effects.cancel()
        }
    }
}
