package com.example.posttemplate.ui.components

import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModel
import com.example.posttemplate.data.repository.AuthRepository
import com.example.posttemplate.ui.navigation.Route

data class DrawerItem(
    val label: String,
    val route: String,
    val icon: ImageVector? = null
)

class DrawerViewModel(val authRepository: AuthRepository) : ViewModel() {
    val drawerItems = listOf(
        DrawerItem(label = "Home", route = Route.Home.route),
        DrawerItem(label = "Profile", route = Route.Profile.route)
    )

    fun logOut() {
        authRepository.setUserSignedIn(false)
    }

    fun isTopBarVisible(route: String?): Boolean {
        return route != Route.Authentication.route
    }
}
