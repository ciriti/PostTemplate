package io.github.ciriti.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import io.github.ciriti.ui.components.AdaptiveNavigationDrawer
import io.github.ciriti.ui.components.DisplayAlertDialog
import io.github.ciriti.ui.components.DrawerContent
import io.github.ciriti.ui.components.TopAppBar
import io.github.ciriti.ui.components.isLargeScreen
import io.github.ciriti.auth.ui.AuthenticationViewModel
import io.github.ciriti.auth.ui.navigation.authenticationRoute
import io.github.ciriti.posts.ui.HomeViewModel
import io.github.ciriti.posts.ui.navigation.postsRoute
import io.github.ciriti.profile.ui.ProfileIntent
import io.github.ciriti.profile.ui.ProfileScreen
import io.github.ciriti.profile.ui.ProfileViewModel
import io.github.ciriti.profile.ui.navigation.profileRoute
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

@Composable
fun SetupNavGraph(
    startDestination: String,
    navController: NavHostController,
    drawerViewModel: DrawerViewModel = koinInject<DrawerViewModel>(),
    authViewModel: AuthenticationViewModel = koinInject<AuthenticationViewModel>(),
    profileViewModel: ProfileViewModel = koinInject<ProfileViewModel>(),
    homeViewModel: HomeViewModel = koinInject<HomeViewModel>(),
) {
    val isLargeScreen = isLargeScreen()
    val currentDestination = navController.currentBackStackEntryAsState().value?.destination?.route
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var signOutDialogOpened by remember { mutableStateOf(false) }

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
                },
                onLogOut = {
                    signOutDialogOpened = true
                    scope.launch { drawerState.close() }
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
            DisplayAlertDialog(
                title = "Sign Out",
                message = "Are you sure you want to sign out?",
                dialogOpened = signOutDialogOpened,
                onDialogClosed = { signOutDialogOpened = false },
                onYesClicked = {
                    drawerViewModel.logOut()
                    navController.navigate(Route.Authentication.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            inclusive = true // Clears all intermediate destinations
                        }
                        launchSingleTop = true // Ensures no duplicate destinations
                    }
                }
            )
            NavHost(
                startDestination = startDestination,
                navController = navController,
                modifier = Modifier.padding(innerPadding)
            ) {
                authenticationRoute(navController, authViewModel)
                postsRoute(navController, homeViewModel)
                profileRoute(profileViewModel)
            }
        }
    }
}
