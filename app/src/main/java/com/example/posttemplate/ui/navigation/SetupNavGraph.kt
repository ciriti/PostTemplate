package com.example.posttemplate.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.example.posttemplate.ui.components.TopAppBar
import com.example.posttemplate.ui.screens.auth.AuthenticationIntent
import com.example.posttemplate.ui.screens.auth.AuthenticationScreen
import com.example.posttemplate.ui.screens.auth.AuthenticationViewModel
import com.example.posttemplate.ui.screens.home.HomeIntent
import com.example.posttemplate.ui.screens.home.HomeScreen
import com.example.posttemplate.ui.screens.home.HomeViewModel
import com.example.posttemplate.ui.screens.profile.ProfileIntent
import com.example.posttemplate.ui.screens.profile.ProfileScreen
import com.example.posttemplate.ui.screens.profile.ProfileViewModel
import org.koin.compose.koinInject

@Composable
fun SetupNavGraph(startDestination: String, navController: NavHostController) {
    val currentDestination = navController.currentBackStackEntryAsState().value?.destination?.route

    Scaffold(
        topBar = {
            if (currentDestination != Route.Authentication.route) {
                TopAppBar(
                    currentDestination = currentDestination,
                    onNavigateBack = { navController.popBackStack() },
                    onSearchClick = { /* Handle search click */ }
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            startDestination = startDestination,
            navController = navController,
            modifier = Modifier.padding(innerPadding)
        ) {
            authenticationRoute(navController)
            homeRoute(navController)
            profileRoute()
        }
    }
}

fun NavGraphBuilder.authenticationRoute(navController: NavHostController) {
    composable(route = Route.Authentication.route) {
        val viewModel = koinInject<AuthenticationViewModel>()
        AuthenticationScreen(
            loadingState = viewModel.state.collectAsState().value.isLoading,
            onButtonClicked = {
                viewModel.handleIntent(AuthenticationIntent.Authenticate)
            },
            navigateToHome = {
                navController.navigate(Route.Home.route) {
                    popUpTo(Route.Authentication.route) { inclusive = true }
                }
            }
        )
    }
}

fun NavGraphBuilder.homeRoute(navController: NavHostController) {
    composable(route = Route.Home.route) {
        val viewModel = koinInject<HomeViewModel>()
        LaunchedEffect(Unit) {
            viewModel.handleIntent(HomeIntent.LoadPosts)
        }
        HomeScreen(
            state = viewModel.state.collectAsState().value,
            onRetry = { viewModel.handleIntent(HomeIntent.LoadPosts) },
            onNavigateToDetails = { postId ->
                navController.navigate(Route.Profile.passUserId(postId))
            }
        )
    }
}

fun NavGraphBuilder.profileRoute() {
    composable(
        route = Route.Profile.route + "/{userId}",
        arguments = listOf(navArgument("userId") { type = NavType.IntType })
    ) { backStackEntry ->
        val viewModel = koinInject<ProfileViewModel>()
        val userId = backStackEntry.arguments?.getInt("userId") ?: return@composable
        LaunchedEffect(Unit) {
            viewModel.handleIntent(ProfileIntent.LoadProfile(userId))
        }
        ProfileScreen(
            state = viewModel.state.collectAsState().value,
            onBack = { /* Handle navigation back */ }
        )
    }
}
