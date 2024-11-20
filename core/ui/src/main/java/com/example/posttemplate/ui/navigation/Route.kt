package com.example.posttemplate.ui.navigation

sealed class Route(val route: String) {
    object Authentication: Route(route = "authentication_screen")
    object Home : Route("home")
    object Profile : Route("profile") {
        fun passUserId(userId: Int): String {
            return "profile/$userId"
        }
    }
}
