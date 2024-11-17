package com.example.posttemplate.ui.navigation

import org.junit.Assert.*

import androidx.compose.runtime.Composable
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.compose.rememberNavController
import androidx.navigation.testing.TestNavHostController
import com.example.posttemplate.ui.navigation.SetupNavGraph
import org.junit.Rule
import org.junit.Test

class SetupNavGraphTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testStartDestinationIsAuthentication() {
        composeTestRule.setContent {
            val navController = TestNavHostController(composeTestRule)
            navController.setGraph(SetupNavGraph()) // Replace with your nav graph resource
            SetupNavGraph(
                startDestination = Route.Authentication.route,
                navController = navController
            )
        }

        composeTestRule.runOnIdle {
            assertThat(navController.currentDestination?.route).isEqualTo(Route.Authentication.route)
        }
    }

    @Test
    fun testNavigateToHome() {
        composeTestRule.setContent {
            val navController = TestNavHostController(composeTestRule.activity)
            navController.setGraph(R.navigation.nav_graph) // Replace with your nav graph resource
            SetupNavGraph(
                startDestination = Route.Authentication.route,
                navController = navController
            )
        }

        composeTestRule.runOnIdle {
            navController.navigate(Route.Home.route)
        }

        composeTestRule.runOnIdle {
            assertThat(navController.currentDestination?.route).isEqualTo(Route.Home.route)
        }
    }
}
