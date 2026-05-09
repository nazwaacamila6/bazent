package com.example.bazent.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bazent.ui.screen.LoginScreen
import com.example.bazent.ui.screen.RegisterScreen
import com.example.bazent.ui.screen.HomeScreen
import com.example.bazent.ui.screen.DetailEventScreen

@Composable
fun NavGraph() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "Login"
    ) {

        composable("register") {
            RegisterScreen(navController)
        }

        composable("login") {
            LoginScreen(navController)
        }
        composable("home") {
            HomeScreen(navController)
        }
        composable("detail_event") {
            DetailEventScreen(navController)
        }
    }
}