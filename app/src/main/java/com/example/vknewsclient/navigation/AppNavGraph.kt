package com.example.vknewsclient.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun AppNavGraph(
    navHostController: NavHostController,
    newsFeetScreenContent: @Composable () -> Unit,
    favoriteScreenContent: @Composable () -> Unit,
    profileScreenContent: @Composable () -> Unit,
    commentsScreenContent: @Composable () -> Unit,
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.Home.route,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None }
    ) {
        homeScreenNavGraph(
            newsFeetScreenContent = newsFeetScreenContent,
            commentsScreenContent = commentsScreenContent
        )
        composable(
            route = Screen.Favorite.route,
        ) {
            favoriteScreenContent()
        }

        composable(
            route = Screen.Profile.route,
        ) {
            profileScreenContent()
        }
    }
}