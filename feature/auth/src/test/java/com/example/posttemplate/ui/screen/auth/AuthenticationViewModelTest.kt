package com.example.posttemplate.ui.screen.auth

import app.cash.turbine.turbineScope
import io.github.ciriti.auth.data.repository.AuthRepository
import io.github.ciriti.auth.ui.AuthenticationEffect
import io.github.ciriti.auth.ui.AuthenticationIntent
import io.github.ciriti.auth.ui.AuthenticationViewModel
import io.mockk.coVerify
import io.mockk.justRun
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
class AuthenticationViewModelTest {

    private val mockAuthRepository: AuthRepository = mockk {
        justRun { setUserSignedIn(any()) }
    }
    private lateinit var viewModel: AuthenticationViewModel
    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel =
            AuthenticationViewModel(authRepository = mockAuthRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `authenticate intent updates state and emits effect`() = testScope.runTest {
        turbineScope {
            // Arrange
            val states = viewModel.state.testIn(backgroundScope)
            val effects = viewModel.effect.testIn(backgroundScope)

            // Act
            viewModel.handleIntent(AuthenticationIntent.Authenticate)

            // Assert
            assertEquals(false, states.awaitItem().isLoading)
            assertEquals(true, states.awaitItem().isLoading) // State during loading
            assertEquals(false, states.awaitItem().isLoading) // State after loading is complete
            assertEquals(AuthenticationEffect.NavigateToHome, effects.awaitItem())

            coVerify { mockAuthRepository.setUserSignedIn(true) }

            states.cancel()
        }
    }
}
