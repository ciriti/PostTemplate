package io.github.ciriti.ui.navigation

import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModel
import io.github.ciriti.auth.data.repository.AuthRepository

data class DrawerItem(
    val label: String,
    val route: String,
    val icon: ImageVector? = null
)

class DrawerViewModel(val authRepository: AuthRepository) : ViewModel() {
    val drawerItems = listOf(
        DrawerItem(label = "Home", route = Route.Posts.route),
        DrawerItem(label = "Profile", route = Route.Profile.route)
    )

    fun logOut() {
        authRepository.setUserSignedIn(false)
    }
}
