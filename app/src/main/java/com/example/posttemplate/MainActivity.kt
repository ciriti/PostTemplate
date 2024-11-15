package com.example.posttemplate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.example.posttemplate.data.repository.AuthRepository
import com.example.posttemplate.ui.navigation.Route
import com.example.posttemplate.ui.navigation.SetupNavGraph
import com.example.posttemplate.ui.theme.AppTheme
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
            if (auth.isUserSignedIn()) Route.Home.route else Route.Authentication.route

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
