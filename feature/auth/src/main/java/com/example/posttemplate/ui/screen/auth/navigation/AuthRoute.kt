package com.example.posttemplate.ui.screen.auth.navigation

import androidx.compose.runtime.collectAsState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.posttemplate.ui.navigation.Route
import com.example.posttemplate.ui.screen.auth.AuthenticationIntent
import com.example.posttemplate.ui.screen.auth.AuthenticationScreen
import com.example.posttemplate.ui.screen.auth.AuthenticationViewModel

fun NavGraphBuilder.authenticationRoute(
    navController: NavHostController,
    authViewModel: AuthenticationViewModel
) {
    composable(route = Route.Authentication.route) {
        AuthenticationScreen(
            loadingState = authViewModel.state.collectAsState().value.isLoading,
            onButtonClicked = {
                authViewModel.handleIntent(AuthenticationIntent.Authenticate)
            },
            navigateToHome = {
                navController.navigate(Route.Posts.route) {
                    popUpTo(Route.Authentication.route) { inclusive = true }
                }
            }
        )
    }
}
