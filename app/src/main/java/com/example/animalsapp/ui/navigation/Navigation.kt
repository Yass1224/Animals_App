package com.example.animalsapp.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Park
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String) {
    object AnimalList : Screen("animals")
    object EnvironmentList : Screen("environments")
    object AnimalDetail : Screen("animals/{animalId}") {
        fun createRoute(animalId: String) = "animals/$animalId"
    }
    object EnvironmentDetail : Screen("environments/{environmentId}") {
        fun createRoute(environmentId: String) = "environments/$environmentId"
    }
}

data class BottomNavItem(
    val label: String,
    val icon: ImageVector,
    val route: String
)

val bottomNavItems = listOf(
    BottomNavItem("Inicio", Icons.Filled.Home, Screen.AnimalList.route),
    BottomNavItem("Ambientes", Icons.Filled.Park, Screen.EnvironmentList.route)
)
