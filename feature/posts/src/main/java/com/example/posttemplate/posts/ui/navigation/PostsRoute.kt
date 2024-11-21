package com.example.posttemplate.posts.ui.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.posttemplate.posts.ui.HomeIntent
import com.example.posttemplate.posts.ui.HomeScreen
import com.example.posttemplate.posts.ui.HomeViewModel
import com.example.posttemplate.ui.navigation.Route

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
