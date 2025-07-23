package com.example.vknewsclient.ui.theme

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource

@Composable
fun MainScreen(){
    Scaffold(
        bottomBar = {
            NavigationBar {
                val item = listOf(
                    NavigationItem.Home,
                    NavigationItem.Favorite,
                    NavigationItem.Profile
                )
                item.forEach{
                    NavigationBarItem(
                        selected = true,
                        onClick = { },
                        icon = {
                            Icon(
                                imageVector = it.icon,
                                contentDescription = null
                            )
                        },
                        label = {
                            Text(text = stringResource(it.titleResId))
                        }
                    )
                }
            }
        }
    ) {
      Box(modifier = Modifier.padding(it))
    }
}