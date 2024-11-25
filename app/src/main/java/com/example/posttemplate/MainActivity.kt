package io.github.ciriti

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import io.github.ciriti.auth.data.repository.AuthRepository
import io.github.ciriti.ui.navigation.Route
import io.github.ciriti.ui.navigation.SetupNavGraph
import io.github.ciriti.ui.theme.AppTheme
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {

    private val auth by inject<AuthRepository>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().setKeepOnScreenCondition {
            false
        }
        enableEdgeToEdge()
        val startDestination =
            if (auth.isUserSignedIn()) Route.Posts.route else Route.Authentication.route

        setContent {
            AppTheme {
                val navController = rememberNavController()
                SetupNavGraph(
                    startDestination = startDestination,
                    navController = navController
                )
            }
        }
    }
}
