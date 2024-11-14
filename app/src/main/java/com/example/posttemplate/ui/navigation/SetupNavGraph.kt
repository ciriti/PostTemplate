package com.example.posttemplate.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.posttemplate.ui.screens.home.*
import com.example.posttemplate.ui.screens.profile.*
import org.koin.compose.koinInject

@Composable
fun SetupNavGraph(startDestination: String, navController: NavHostController) {
    NavHost(
        startDestination = startDestination,
        navController = navController
    ) {
        homeRoute(navController)
        profileRoute()
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
