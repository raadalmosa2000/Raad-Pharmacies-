package com.example
import com.example.ui.screens.OnboardingScreen

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Medication
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LocalHospital
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material.icons.outlined.Medication
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.ui.screens.*
import com.example.ui.theme.PrimaryGreen

sealed class Screen(val route: String, val title: String, val selectedIcon: ImageVector, val unselectedIcon: ImageVector) {
    object Home : Screen("home", "الرئيسية", Icons.Filled.Home, Icons.Outlined.Home)
    object OnDuty : Screen("onduty", "المناوبة", Icons.Filled.LocalHospital, Icons.Outlined.LocalHospital)
    object Map : Screen("map", "الخريطة", Icons.Filled.Map, Icons.Outlined.Map)
    object Medicine : Screen("medicine", "الأدوية", Icons.Filled.Medication, Icons.Outlined.Medication)
    object Account : Screen("account", "حسابي", Icons.Filled.Person, Icons.Outlined.Person)
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    
    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = navController)
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "onboarding",
            modifier = Modifier.padding(innerPadding),
            enterTransition = { androidx.compose.animation.fadeIn(animationSpec = androidx.compose.animation.core.tween(300)) },
            exitTransition = { androidx.compose.animation.fadeOut(animationSpec = androidx.compose.animation.core.tween(300)) },
            popEnterTransition = { androidx.compose.animation.fadeIn(animationSpec = androidx.compose.animation.core.tween(300)) },
            popExitTransition = { androidx.compose.animation.fadeOut(animationSpec = androidx.compose.animation.core.tween(300)) }
        ) {
            composable("onboarding") { OnboardingScreen(navController) }
            composable(Screen.Home.route) { HomeScreen(navController) }
            composable(Screen.OnDuty.route) { OnDutyScreen(navController) }
            composable(Screen.Map.route) { MapScreen(navController) }
            composable(Screen.Medicine.route) { MedicineSearchScreen(navController) }
            composable(Screen.Account.route) { AccountScreen(navController) }
            
            // Sub screens
            composable("favorites") { FavoritesScreen(navController) }
            composable("pharmacy_detail/{id}") { PharmacyDetailScreen(navController) }
            composable("unavailable_medicines") { UnavailableMedicinesScreen(navController) }
            composable("region_selection") { RegionSelectionScreen(navController) }
            composable("register_pharmacy") { RegisterPharmacyScreen(navController) }
            composable("admin_dashboard") { AdminDashboardScreen(navController) }
            composable("prescription_scanner") { PrescriptionScannerScreen(navController) }
            composable("saved_medicines") { SavedMedicinesScreen(navController) }
            composable("search_history") { SearchHistoryScreen(navController) }
            composable("reminders") { RemindersScreen(navController) }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf(
        Screen.Home,
        Screen.OnDuty,
        Screen.Map,
        Screen.Medicine,
        Screen.Account
    )
    
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    
    val bottomBarDestination = items.any { it.route == currentDestination?.route }
    if (bottomBarDestination) {
        NavigationBar(
            containerColor = androidx.compose.ui.graphics.Color.White,
            tonalElevation = 8.dp
        ) {
            items.forEach { screen ->
                val selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true
                NavigationBarItem(
                    icon = {
                        Icon(
                            imageVector = if (selected) screen.selectedIcon else screen.unselectedIcon,
                            contentDescription = screen.title
                        )
                    },
                    label = { 
                        Text(
                            text = screen.title,
                            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
                        ) 
                    },
                    selected = selected,
                    onClick = {
                        navController.navigate(screen.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = PrimaryGreen,
                        selectedTextColor = PrimaryGreen,
                        indicatorColor = com.example.ui.theme.LightGreen,
                        unselectedIconColor = com.example.ui.theme.LightText,
                        unselectedTextColor = com.example.ui.theme.LightText
                    )
                )
            }
        }
    }
}
