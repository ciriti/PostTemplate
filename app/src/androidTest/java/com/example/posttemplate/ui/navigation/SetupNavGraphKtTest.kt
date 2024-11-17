package com.example.posttemplate.ui.navigation


import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.rememberNavController
import com.example.posttemplate.ui.theme.AppTheme
import org.junit.Rule
import org.junit.Test

class MyComposeTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun myTest() {
        // Start the app
        composeTestRule.setContent {
            AppTheme {
                val navController = rememberNavController()
                SetupNavGraph(
                    startDestination = Route.Authentication.route,
                    navController = navController
                )
            }

        }

        composeTestRule.onNodeWithText("Sign in with Google").performClick()

        composeTestRule.onNodeWithText("Welcome").assertIsDisplayed()
    }
}
