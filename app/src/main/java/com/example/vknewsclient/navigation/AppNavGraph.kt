package com.example.vknewsclient.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun AppNavGraph(
    navHostController : NavHostController,
    homeScreenContent: @Composable () -> Unit,
    favoriteScreenContent: @Composable () -> Unit,
    profileScreenContent: @Composable () -> Unit,

    ) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.NewsFeet.route,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None }
    ) {
        composable(
            route = Screen.Favorite.route,
        ) {
            favoriteScreenContent()
        }
        composable(
            route = Screen.NewsFeet.route,
        ) {
            homeScreenContent()
        }
        composable(
            route = Screen.Profile.route,
        ) {
            profileScreenContent()
        }
    }
}