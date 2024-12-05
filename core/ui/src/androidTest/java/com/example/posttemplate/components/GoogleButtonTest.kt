package io.github.ciriti.components

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import io.github.ciriti.ui.R
import io.github.ciriti.ui.components.GoogleButton
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class GoogleButtonTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun buttonDisplaysPrimaryTextWhenNotLoading() {
        // Arrange
        val primaryText = "Sign in with Google"

        // Act
        composeTestRule.setContent {
            GoogleButton(
                loadingState = false,
                primaryText = primaryText,
                onClick = {}
            )
        }

        // Assert
        composeTestRule.onNodeWithText(primaryText).assertIsDisplayed()
    }

    @Test
    fun buttonDisplaysSecondaryTextWhenLoading() {
        // Arrange
        val secondaryText = "Please wait..."

        // Act
        composeTestRule.setContent {
            GoogleButton(
                loadingState = true,
                secondaryText = secondaryText,
                onClick = {}
            )
        }

        // Assert
        composeTestRule.onNodeWithText(secondaryText).assertIsDisplayed()
    }

    @Test
    fun buttonIsClickableWhenNotLoading() {
        // Arrange
        var wasClicked = false

        // Act
        composeTestRule.setContent {
            GoogleButton(
                loadingState = false,
                onClick = { wasClicked = true }
            )
        }

        composeTestRule.onNodeWithContentDescription("GoogleButton").performClick()

        // Assert
        assertTrue("Button was not clickable when not loading.", wasClicked)
    }

    @Test
    fun buttonIsNotClickableWhenLoading() {
        // Arrange
        var wasClicked = false

        // Act
        composeTestRule.setContent {
            GoogleButton(
                loadingState = true,
                onClick = { wasClicked = true }
            )
        }

        composeTestRule.onNodeWithContentDescription("GoogleButton").performClick()

        // Assert
        assertFalse("Button was clickable when loading.", wasClicked)
    }

    @Test
    fun iconIsDisplayed() {
        // Act
        composeTestRule.setContent {
            GoogleButton(
                loadingState = false,
                icon = R.drawable.google_logo,
                onClick = {}
            )
        }

        // Assert
        composeTestRule.onNodeWithContentDescription("Google Logo").assertIsDisplayed()
    }

    @Test
    fun progressIndicatorIsDisplayedWhenLoading() {
        // Act
        composeTestRule.setContent {
            GoogleButton(
                loadingState = true,
                onClick = {}
            )
        }

        // Assert
        composeTestRule.onNodeWithContentDescription("CircularProgressIndicator")
            .assertIsDisplayed()
    }

    @Test
    fun progressIndicatorIsNotDisplayedWhenNotLoading() {
        // Act
        composeTestRule.setContent {
            GoogleButton(
                loadingState = false,
                onClick = {}
            )
        }

        // Assert
        composeTestRule.onNodeWithContentDescription("CircularProgressIndicator")
            .assertDoesNotExist()
    }
}
