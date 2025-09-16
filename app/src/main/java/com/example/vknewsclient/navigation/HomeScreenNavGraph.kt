package com.example.vknewsclient.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation


fun NavGraphBuilder.homeScreenNavGraph(
    newsFeetScreenContent: @Composable () -> Unit,
    commentsScreenContent: @Composable () -> Unit,
) {
    navigation(
        startDestination = Screen.NewsFeet.route,
        route = Screen.Home.route
    ) {
        composable(
            route = Screen.NewsFeet.route,
        ) {
            newsFeetScreenContent()
        }

        composable(
            route = Screen.Comments.route
        ) {
            commentsScreenContent()
        }
    }
}