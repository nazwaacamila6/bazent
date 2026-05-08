package com.example.bazent.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bazent.ui.screen.LoginScreen
import com.example.bazent.ui.screen.RegisterScreen

@Composable
fun NavGraph() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "register"
    ) {

        composable("register") {
            RegisterScreen(navController)
        }

        composable("login") {
            LoginScreen(navController)
        }
    }
}