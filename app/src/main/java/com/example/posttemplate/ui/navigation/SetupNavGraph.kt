package com.example.posttemplate.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.example.posttemplate.ui.components.AdaptiveNavigationDrawer
import com.example.posttemplate.ui.components.DrawerContent
import com.example.posttemplate.ui.components.TopAppBar
import com.example.posttemplate.ui.components.isLargeScreen
import com.example.posttemplate.ui.screens.auth.AuthenticationIntent
import com.example.posttemplate.ui.screens.auth.AuthenticationScreen
import com.example.posttemplate.ui.screens.auth.AuthenticationViewModel
import com.example.posttemplate.ui.screens.home.HomeIntent
import com.example.posttemplate.ui.screens.home.HomeScreen
import com.example.posttemplate.ui.screens.home.HomeViewModel
import com.example.posttemplate.ui.screens.profile.ProfileIntent
import com.example.posttemplate.ui.screens.profile.ProfileScreen
import com.example.posttemplate.ui.screens.profile.ProfileViewModel
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

@Composable
fun SetupNavGraph(startDestination: String, navController: NavHostController) {
    val isLargeScreen = isLargeScreen()
    val currentDestination = navController.currentBackStackEntryAsState().value?.destination?.route
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    AdaptiveNavigationDrawer(
        isLargeScreen = isLargeScreen,
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(
                currentDestination = currentDestination,
                onNavigate = { route ->
                    scope.launch { drawerState.close() }
                    navController.navigate(route) {
                        popUpTo(navController.graph.startDestinationId) { inclusive = false }
                        launchSingleTop = true
                    }
                }
            )
        }
    ) {
        Scaffold(
            topBar = {
                if (currentDestination != Route.Authentication.route) {
                    TopAppBar(
                        currentDestination = currentDestination,
                        onNavigateBack = { navController.popBackStack() },
                        onMenuClick = {
                            if (!isLargeScreen) {
                                scope.launch { drawerState.open() }
                            }
                        },
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
