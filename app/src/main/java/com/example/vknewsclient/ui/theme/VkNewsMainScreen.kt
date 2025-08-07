package com.example.vknewsclient.ui.theme

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import kotlinx.coroutines.launch

@Composable
fun MainScreen() {
    val snackBarState = SnackbarHostState()
    val floatingActionButtonState = remember {
        mutableStateOf(true)
    }
    val scope = rememberCoroutineScope()
    Scaffold(
        bottomBar = {
            NavigationBar {
                val selectedItemPosition = remember { mutableIntStateOf(0) }
                val item = listOf(
                    NavigationItem.Home,
                    NavigationItem.Favorite,
                    NavigationItem.Profile
                )
                item.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = selectedItemPosition.intValue == index,
                        onClick = { selectedItemPosition.intValue = index },
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
        snackbarHost = {
            SnackbarHost(
                hostState = snackBarState
            )
        },
        floatingActionButton = {
            if (floatingActionButtonState.value){
                FloatingActionButton(
                    onClick = {
                        scope.launch {
                            val action = snackBarState.showSnackbar(
                                message = "This is snackbar",
                                actionLabel = "Close",
                                duration = SnackbarDuration.Long
                            )
                            if (action == SnackbarResult.ActionPerformed) {
                                floatingActionButtonState.value = false
                            }
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Favorite,
                        contentDescription = null
                    )
                }
            }

        }
    ) {
        Box(modifier = Modifier.padding(it))
    }
}