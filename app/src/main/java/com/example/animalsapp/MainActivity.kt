package com.example.animalsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.animalsapp.ui.navigation.Screen
import com.example.animalsapp.ui.navigation.bottomNavItems
import com.example.animalsapp.ui.screens.*
import com.example.animalsapp.ui.theme.AnimalsAppTheme
import com.example.animalsapp.ui.theme.DarkBackground
import com.example.animalsapp.ui.theme.DarkSurface
import com.example.animalsapp.ui.theme.TextSecondary
import com.example.animalsapp.viewmodel.AnimalsViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AnimalsAppTheme {
                AnimalsApp()
            }
        }
    }
}

@Composable
fun AnimalsApp() {
    val navController = rememberNavController()
    val viewModel: AnimalsViewModel = viewModel()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    // Only show bottom bar on main screens
    val showBottomBar = currentDestination?.route in listOf(
        Screen.AnimalList.route, Screen.EnvironmentList.route
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = DarkBackground,
        bottomBar = {
            if (showBottomBar) {
                NavigationBar(containerColor = DarkSurface) {
                    bottomNavItems.forEach { item ->
                        val selected = currentDestination?.hierarchy?.any { it.route == item.route } == true
                        NavigationBarItem(
                            selected = selected,
                            onClick = {
                                navController.navigate(item.route) {
                                    popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = {
                                Icon(item.icon, contentDescription = item.label)
                            },
                            label = {
                                Text(
                                    text = item.label,
                                    fontSize = 11.sp,
                                    fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = DarkBackground,
                                selectedTextColor = androidx.compose.ui.graphics.Color(0xFFD4E84A),
                                indicatorColor = androidx.compose.ui.graphics.Color(0xFFD4E84A),
                                unselectedIconColor = TextSecondary,
                                unselectedTextColor = TextSecondary
                            )
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.AnimalList.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.AnimalList.route) {
                AnimalListScreen(
                    viewModel = viewModel,
                    onAnimalClick = { id ->
                        navController.navigate(Screen.AnimalDetail.createRoute(id))
                    }
                )
            }
            composable(Screen.EnvironmentList.route) {
                EnvironmentListScreen(
                    viewModel = viewModel,
                    onEnvironmentClick = { id ->
                        navController.navigate(Screen.EnvironmentDetail.createRoute(id))
                    }
                )
            }
            composable(Screen.AnimalDetail.route) { backStackEntry ->
                val animalId = backStackEntry.arguments?.getString("animalId") ?: return@composable
                AnimalDetailScreen(
                    animalId = animalId,
                    viewModel = viewModel,
                    onBack = { navController.popBackStack() }
                )
            }
            composable(Screen.EnvironmentDetail.route) { backStackEntry ->
                val environmentId = backStackEntry.arguments?.getString("environmentId") ?: return@composable
                EnvironmentDetailScreen(
                    environmentId = environmentId,
                    viewModel = viewModel,
                    onBack = { navController.popBackStack() },
                    onAnimalClick = { id ->
                        navController.navigate(Screen.AnimalDetail.createRoute(id))
                    }
                )
            }
        }
    }
}
