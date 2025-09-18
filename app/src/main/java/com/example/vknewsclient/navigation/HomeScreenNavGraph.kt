package com.example.vknewsclient.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.vknewsclient.domain.FeedPost
import com.example.vknewsclient.navigation.Screen.Companion.KEY_FEED_POST_ID
import com.example.vknewsclient.navigation.Screen.Companion.KEY_FEED_POST_TEXT


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
                navArgument(KEY_FEED_POST_ID) {
                    type = NavType.IntType
                },
                navArgument(KEY_FEED_POST_TEXT) {
                    type = NavType.StringType
                }
            )
        ) {
            val feedPostId = it.arguments?.getInt(KEY_FEED_POST_ID) ?: 0
            val feedPostString = it.arguments?.getString(KEY_FEED_POST_TEXT) ?: ""
            commentsScreenContent(FeedPost(id = feedPostId, postText = feedPostString))
        }
    }
}