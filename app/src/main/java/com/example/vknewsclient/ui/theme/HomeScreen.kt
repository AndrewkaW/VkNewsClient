package com.example.vknewsclient.ui.theme

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.vknewsclient.MainViewModel

@Composable
fun HomeScreen(modifier : Modifier, viewModel: MainViewModel){
    val posts = viewModel.postsLiveData.observeAsState(listOf())
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(
            top = 16.dp,
            start = 8.dp,
            end = 8.dp,
            bottom = 8.dp
        ),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(items = posts.value, key = { it.id }) { post ->
            val dismissBoxState = rememberSwipeToDismissBoxState(
                confirmValueChange = { value ->
                    val isDismissed = value in setOf(
                        SwipeToDismissBoxValue.StartToEnd,
                        SwipeToDismissBoxValue.EndToStart
                    )
                    if (isDismissed) {
                        viewModel.deletePost(post)
                    }
                    return@rememberSwipeToDismissBoxState isDismissed
                }
            )

            SwipeToDismissBox(
                modifier = Modifier.animateItem(),
                state = dismissBoxState,
                enableDismissFromStartToEnd = false,
                enableDismissFromEndToStart = true,
                backgroundContent = { }
            ) {
                VkPost(
                    feedPost = post,
                    onLikeClickListener = { viewModel.updateCount(feedPost = post, item = it) },
                    onShareClickListener = { viewModel.updateCount(feedPost = post, item = it) },
                    onViewsClickListener = { viewModel.updateCount(feedPost = post, item = it) },
                    onCommentClickListener = { viewModel.updateCount(feedPost = post, item = it) },
                )
            }
        }
    }
}