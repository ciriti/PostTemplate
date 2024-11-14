package com.example.posttemplate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.posttemplate.ui.navigation.Route
import com.example.posttemplate.ui.navigation.SetupNavGraph
import com.example.posttemplate.ui.theme.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                val navController = rememberNavController()
                SetupNavGraph(
                    startDestination = Route.Home.route,
                    navController = navController
                )
            }
        }
    }
}