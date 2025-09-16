package com.example.vknewsclient.ui.theme

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.vknewsclient.domain.FeedPost
import com.example.vknewsclient.navigation.AppNavGraph
import com.example.vknewsclient.navigation.rememberNavBottomBarState

@Composable
fun MainScreen() {
    val navigationState = rememberNavBottomBarState()
    val postToComments: MutableState<FeedPost?> = remember {
        mutableStateOf(null)
    }
    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navigationState.navHostController.currentBackStackEntryAsState()
                val currentRout = navBackStackEntry?.destination?.route
                val item = listOf(
                    NavigationItem.Home,
                    NavigationItem.Favorite,
                    NavigationItem.Profile
                )
                item.forEach { item ->
                    NavigationBarItem(
                        selected = currentRout == item.screen.route,
                        onClick = { navigationState.navigateTo(item.screen.route) },
                        icon = {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = null
                            )
                        },
                        label = {
                            Text(text = stringResource(item.titleResId))
                        }
                    )
                }
            }
        },
    ) {
        AppNavGraph(
            navHostController = navigationState.navHostController,
            homeScreenContent = {
                if (postToComments.value == null) {
                    HomeScreen(
                        modifier = Modifier.padding(it),
                        onCommentsClickListener = { postToComments.value = it }
                    )
                } else {
                    CommentsScreen(feedPost = postToComments.value!!) { postToComments.value = null }
                }

            },
            favoriteScreenContent = { TextCounter("Favorite") },
            profileScreenContent = { TextCounter("Profile") }

        )
    }
}

@Composable
fun TextCounter(name: String) {
    var count: Int by rememberSaveable { mutableIntStateOf(0) }

    Text(
        modifier = Modifier
            .clickable { count++ }
            .padding(16.dp),
        text = "$name Count: $count",
        color = Color.Black
    )
}

