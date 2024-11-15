package com.example.posttemplate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.example.posttemplate.ui.navigation.Route
import com.example.posttemplate.ui.navigation.SetupNavGraph
import com.example.posttemplate.ui.theme.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().setKeepOnScreenCondition {
            false
        }
        enableEdgeToEdge()
        setContent {
            AppTheme {
                val navController = rememberNavController()
                SetupNavGraph(
                    startDestination = Route.Authentication.route,
                    navController = navController
                )
            }
        }
    }
}
