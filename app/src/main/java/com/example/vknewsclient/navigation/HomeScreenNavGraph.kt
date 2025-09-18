package com.example.vknewsclient.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.vknewsclient.domain.FeedPost
import com.example.vknewsclient.navigation.Screen.Companion.KEY_FEED_POST
import com.google.gson.Gson


fun NavGraphBuilder.homeScreenNavGraph(
    newsFeetScreenContent: @Composable () -> Unit,
    commentsScreenContent: @Composable (FeedPost) -> Unit,
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
            route = Screen.Comments.route,
            arguments = listOf(

                navArgument(KEY_FEED_POST) {
                    type = NavType.StringType
                }
            )
        ) {
            val feedPostJson = it.arguments?.getString(KEY_FEED_POST) ?: ""
            val feedPost = Gson().fromJson(feedPostJson, FeedPost::class.java)
            commentsScreenContent(feedPost)
        }
    }
}