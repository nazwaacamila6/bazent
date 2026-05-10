package com.example.bazent.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.bazent.ui.screen.*
import com.example.bazent.ui.theme.PrimaryBlue
import com.example.bazent.ui.theme.SoftBlue
import com.example.bazent.ui.theme.TextGray

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // MENGGUNAKAN BOX AGAR NAVBAR MELAYANG TANPA KOTAK BACKGROUND
    Box(modifier = Modifier.fillMaxSize()) {

        // 1. KONTEN LAYAR
        NavHost(
            navController = navController,
            startDestination = "login",
            modifier = Modifier.fillMaxSize()
        ) {
            composable("register") { RegisterScreen(navController) }
            composable("login") { LoginScreen(navController) }
            composable("home") { HomeScreen(navController) }
            composable("detail_event") { DetailEventScreen(navController) }
            composable("profile") { ProfileScreen(navController) }
            composable("create") { CreateEventScreen(navController) }
        }

        // 2. NAVBAR OVAL BENERAN (MELAYANG)
        if (currentRoute == "home" || currentRoute == "profile" || currentRoute == "create" || currentRoute == "detail_event") {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 18.dp)
            ) {

                NavigationBar(
                    modifier = Modifier
                        .fillMaxWidth(0.86f)
                        .height(78.dp)
                        .shadow(
                            elevation = 18.dp,
                            shape = CircleShape
                        )
                        .clip(CircleShape),

                    containerColor = Color.White,
                    tonalElevation = 0.dp
                ) {

                    // HOME
                    NavigationBarItem(
                        selected = currentRoute == "home",
                        onClick = {
                            navController.navigate("home")
                        },
                        icon = {
                            Icon(
                                Icons.Default.Home,
                                contentDescription = null,
                                modifier = Modifier.offset(y = 6.dp)
                            )
                        },
                        label = {
                            Text(
                                "Home",
                                fontSize = 11.sp,
                                modifier = Modifier.offset(y = 4.dp)
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = PrimaryBlue,
                            selectedTextColor = PrimaryBlue,
                            indicatorColor = SoftBlue.copy(alpha = 0.3f),
                            unselectedIconColor = TextGray
                        )
                    )

                    // RUANG KOSONG TENGAH
                    Spacer(modifier = Modifier.width(70.dp))

                    // PROFILE
                    NavigationBarItem(
                        selected = currentRoute == "profile",
                        onClick = {
                            navController.navigate("profile")
                        },
                        icon = {
                            Icon(
                                Icons.Default.Person,
                                contentDescription = null,
                                modifier = Modifier.offset(y = 6.dp)
                            )
                        },
                        label = {
                            Text(
                                "Profile",
                                fontSize = 11.sp,
                                modifier = Modifier.offset(y = 4.dp)
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = PrimaryBlue,
                            selectedTextColor = PrimaryBlue,
                            indicatorColor = SoftBlue.copy(alpha = 0.3f),
                            unselectedIconColor = TextGray
                        )
                    )
                }

                // TOMBOL PLUS FLOATING
                Box(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .offset(y = (-18).dp)
                        .size(58.dp)
                        .shadow(12.dp, CircleShape)
                        .clip(CircleShape)
                        .background(PrimaryBlue),
                    contentAlignment = Alignment.Center
                ) {
                    IconButton(
                        onClick = {
                            navController.navigate("create") {
                                launchSingleTop = true
                            }
                        }
                    ) {
                        Icon(
                            Icons.Default.Add,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                }
            }
        }
    }
}