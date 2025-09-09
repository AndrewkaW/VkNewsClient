package com.example.vknewsclient.ui.theme

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.vknewsclient.MainViewModel
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color

@Composable
fun MainScreen(viewModel: MainViewModel) {
    val selectedNavItem by viewModel.selectedNavItem.observeAsState(NavigationItem.Home)
    Scaffold(
        bottomBar = {
            NavigationBar {
                val item = listOf(
                    NavigationItem.Home,
                    NavigationItem.Favorite,
                    NavigationItem.Profile
                )
                item.forEach { item ->
                    NavigationBarItem(
                        selected = selectedNavItem == item,
                        onClick = { viewModel.selectNavItem(item) },
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
        when(selectedNavItem) {
            NavigationItem.Favorite -> Text(text = "Favorite", color = Color.Black)
            NavigationItem.Home -> HomeScreen(modifier = Modifier.padding(it), viewModel = viewModel)
            NavigationItem.Profile -> Text(text = "Profile", color = Color.Black)
        }
    }
}

