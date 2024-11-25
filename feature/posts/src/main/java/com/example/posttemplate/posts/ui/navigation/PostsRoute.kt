package io.github.ciriti.posts.ui.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import io.github.ciriti.posts.ui.HomeIntent
import io.github.ciriti.posts.ui.HomeScreen
import io.github.ciriti.posts.ui.HomeViewModel
import io.github.ciriti.ui.navigation.Route

fun NavGraphBuilder.postsRoute(navController: NavHostController, homeViewModel: HomeViewModel) {
    composable(route = Route.Posts.route) {
        LaunchedEffect(Unit) {
            homeViewModel.handleIntent(HomeIntent.LoadPosts)
        }
        HomeScreen(
            state = homeViewModel.state.collectAsState().value,
            onRetry = { homeViewModel.handleIntent(HomeIntent.LoadPosts) },
            onNavigateToDetails = { postId ->
                navController.navigate(Route.Profile.passUserId(postId))
            }
        )
    }
}
