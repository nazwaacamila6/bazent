package com.example.bazent.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bazent.ui.screen.LoginScreen
import com.example.bazent.ui.screen.RegisterScreen
import com.example.bazent.ui.screen.HomeScreen
import com.example.bazent.ui.screen.DetailEventScreen
import com.example.bazent.ui.screen.ProfileScreen
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.getValue
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.filled.Add
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.bazent.ui.theme.PrimaryBlue
import com.example.bazent.ui.theme.SoftBlue
import com.example.bazent.ui.theme.TextGray
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import com.example.bazent.ui.screen.CreateEventScreen

@Composable
fun NavGraph() {

    val navController = rememberNavController()

    val currentRoute =
        navController.currentBackStackEntryAsState().value?.destination?.route

    Scaffold(

        bottomBar = {

            // navbar cuma muncul di halaman tertentu
            if (
                currentRoute == "home" ||
                currentRoute == "profile" ||
                currentRoute == "create"
            ) {

                NavigationBar {

                    // HOME
                    NavigationBarItem(
                        selected = currentRoute == "home",

                        onClick = {
                            navController.navigate("home") {
                                launchSingleTop = true
                            }
                        },

                        icon = {
                            Icon(
                                imageVector = Icons.Default.Home,
                                contentDescription = "Home"
                            )
                        },

                        label = {
                            Text("Home")
                        },

                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = PrimaryBlue,
                            selectedTextColor = PrimaryBlue,
                            indicatorColor = SoftBlue,
                            unselectedIconColor = TextGray,
                            unselectedTextColor = TextGray
                        )
                    )

                    // CREATE
                    NavigationBarItem(
                        selected = currentRoute == "create",

                        onClick = {
                            navController.navigate("create") {
                                launchSingleTop = true
                            }
                        },

                        icon = {

                            Box(
                                modifier = Modifier
                                    .size(52.dp)
                                    .background(
                                        color = PrimaryBlue,
                                        shape = CircleShape
                                    ),

                                contentAlignment = Alignment.Center
                            ) {

                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = "Create",
                                    tint = Color.White
                                )
                            }
                        },

                        label = { }
                    )

                    // PROFILE
                    NavigationBarItem(
                        selected = currentRoute == "profile",

                        onClick = {
                            navController.navigate("profile") {
                                launchSingleTop = true
                            }
                        },

                        icon = {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "Profile"
                            )
                        },

                        label = {
                            Text("Profile")
                        },

                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = PrimaryBlue,
                            selectedTextColor = PrimaryBlue,
                            indicatorColor = SoftBlue,
                            unselectedIconColor = TextGray,
                            unselectedTextColor = TextGray
                        )
                    )
                }
            }
        }

    ) { paddingValues ->

        NavHost(
            navController = navController,
            startDestination = "login",
            modifier = Modifier.padding(paddingValues)
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
            composable("profile") {
                ProfileScreen(navController)
            }
            composable("create") {
                CreateEventScreen(navController)
            }
        }
    }
}