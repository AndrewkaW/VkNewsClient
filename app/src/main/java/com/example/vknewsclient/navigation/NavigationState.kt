package com.example.vknewsclient.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

class NavigationState(
    val navHostController: NavHostController
) {
    fun navigateTo(route: String) {
        navHostController.navigate(route = route) {
            popUpTo(navHostController.graph.findStartDestination().id) {
                saveState = true
            }
            restoreState = true
            launchSingleTop = true
        }
    }

    fun navigationToComments() {
        navHostController.navigate(Screen.Comments.route)
    }

}

@Composable
fun rememberNavBottomBarState(navHostController: NavHostController = rememberNavController()): NavigationState {
    return remember {
        NavigationState(navHostController)
    }
}