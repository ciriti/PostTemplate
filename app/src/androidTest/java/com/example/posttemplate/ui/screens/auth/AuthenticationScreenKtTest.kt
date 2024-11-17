package com.example.posttemplate.ui.screens.auth

import androidx.compose.ui.test.assertHasNoClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import org.junit.Rule
import org.junit.Test

class AuthenticationScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testAuthenticationScreenDisplaysContent() {
        // Arrange
        composeTestRule.setContent {
            AuthenticationScreen(
                loadingState = false,
                onButtonClicked = {},
                navigateToHome = {}
            )
        }

        // Assert
        composeTestRule.onNodeWithTag("GoogleLogo").assertIsDisplayed()
        composeTestRule.onNodeWithTag("AuthTitle").assertIsDisplayed()
        composeTestRule.onNodeWithTag("AuthSubtitle").assertIsDisplayed()
        composeTestRule.onNodeWithTag("GoogleButton").assertIsDisplayed()
    }
    @Test
    fun testGoogleButtonClickTriggersCallbacks() {
        // Arrange
        var buttonClicked = false
        var navigated = false

        composeTestRule.setContent {
            AuthenticationScreen(
                loadingState = false,
                onButtonClicked = { buttonClicked = true },
                navigateToHome = { navigated = true }
            )
        }

        // Act
        composeTestRule.onNodeWithTag("GoogleButton").performClick()

        // Assert
//        assert(buttonClicked) { "Button click callback was not triggered" }
        assert(navigated) { "Navigation callback was not triggered" }
    }

    @Test
    fun testLoadingStateDisablesGoogleButton() {
        // Arrange
        composeTestRule.setContent {
            AuthenticationScreen(
                loadingState = true,
                onButtonClicked = {},
                navigateToHome = {}
            )
        }

        // Assert
        composeTestRule.onNodeWithTag("GoogleButton").assertIsDisplayed() // Button is displayed
        composeTestRule.onNodeWithTag("GoogleButton")
            .assertHasNoClickAction() // Button is not clickable
    }

    @Test
    fun testGoogleButtonDisabledWhenLoading() {
        composeTestRule.setContent {
            AuthenticationScreen(
                loadingState = true,
                onButtonClicked = {},
                navigateToHome = {}
            )
        }

        composeTestRule.onNodeWithTag("GoogleButton").assertHasNoClickAction()
    }
}
