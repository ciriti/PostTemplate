package io.github.ciriti.profile.ui.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import io.github.ciriti.profile.ui.ProfileIntent
import io.github.ciriti.profile.ui.ProfileScreen
import io.github.ciriti.profile.ui.ProfileViewModel
import io.github.ciriti.ui.navigation.Route

fun NavGraphBuilder.profileRoute(profileViewModel: ProfileViewModel) {
    composable(
        route = Route.Profile.route + "/{userId}",
        arguments = listOf(navArgument("userId") { type = NavType.IntType })
    ) { backStackEntry ->
        val userId = backStackEntry.arguments?.getInt("userId") ?: return@composable
        LaunchedEffect(Unit) {
            profileViewModel.handleIntent(ProfileIntent.LoadProfile(userId))
        }
        ProfileScreen(
            state = profileViewModel.state.collectAsState().value,
            onBack = { /* Handle navigation back */ }
        )
    }
}
