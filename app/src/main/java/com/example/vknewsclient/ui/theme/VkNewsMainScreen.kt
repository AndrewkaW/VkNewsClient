package com.example.vknewsclient.ui.theme

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.vknewsclient.domain.FeedPost


@Composable
fun MainScreen() {
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
    ) {
        Box(modifier = Modifier.padding(it)) {
            val feedPost = remember { mutableStateOf(FeedPost()) }
            VkPost(
                modifier = Modifier.padding(8.dp),
                feedPost = feedPost.value,
                onStatisticItemClickListener = { newItem ->
                    val oldStatistics = feedPost.value.statistics
                    val newStatistics = oldStatistics.toMutableList().apply {
                        replaceAll { oldItem ->
                            if (oldItem.type == newItem.type) {
                                oldItem.copy(count = oldItem.count + 1)

                            } else {
                                oldItem
                            }
                        }
                    }
                    feedPost.value = feedPost.value.copy(statistics = newStatistics)
                }
            )
        }
    }
}