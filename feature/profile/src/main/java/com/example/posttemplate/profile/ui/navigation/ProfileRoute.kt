package com.example.posttemplate.profile.ui.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.posttemplate.profile.ui.ProfileIntent
import com.example.posttemplate.profile.ui.ProfileScreen
import com.example.posttemplate.profile.ui.ProfileViewModel
import com.example.posttemplate.ui.navigation.Route

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
