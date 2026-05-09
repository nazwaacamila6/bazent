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
        if (currentRoute == "home" || currentRoute == "profile" || currentRoute == "create") {
            NavigationBar(
                modifier = Modifier
                    .align(Alignment.BottomCenter) // Memposisikan di bawah tengah
                    .padding(horizontal = 24.dp)   // Jarak samping
                    .padding(bottom = 32.dp)       // Jarak bawah (Biar gak error lagi)
                    .height(90.dp)
                    .shadow(elevation = 16.dp, shape = CircleShape) // Bayangan OVAL
                    .clip(CircleShape) // FISIK OVAL
                    .border(1.dp, Color.White.copy(alpha = 0.5f), CircleShape),
                containerColor = Color.White,
                tonalElevation = 0.dp
            ) {
                // --- ITEM HOME ---
                NavigationBarItem(
                    selected = currentRoute == "home",
                    onClick = {
                        navController.navigate("home") {
                            popUpTo("home") { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = { Icon(Icons.Default.Home, contentDescription = null) },
                    label = { Text("Home", fontSize = 12.sp) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = PrimaryBlue,
                        indicatorColor = SoftBlue.copy(alpha = 0.5f),
                        unselectedIconColor = TextGray
                    )
                )

                // --- ITEM CREATE ---
                NavigationBarItem(
                    selected = currentRoute == "create",
                    onClick = {
                        navController.navigate("create") {
                            popUpTo("home") { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = {
                        Box(
                            modifier = Modifier.size(48.dp).background(PrimaryBlue, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.Add, contentDescription = null, tint = Color.White)
                        }
                    },
                    label = { Text(" ") },
                    colors = NavigationBarItemDefaults.colors(indicatorColor = Color.Transparent)
                )

                // --- ITEM PROFILE ---
                NavigationBarItem(
                    selected = currentRoute == "profile",
                    onClick = {
                        navController.navigate("profile") {
                            popUpTo("home") { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = { Icon(Icons.Default.Person, contentDescription = null) },
                    label = { Text("Profile", fontSize = 12.sp) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = PrimaryBlue,
                        indicatorColor = SoftBlue.copy(alpha = 0.5f),
                        unselectedIconColor = TextGray
                    )
                )
            }
        }
    }
}