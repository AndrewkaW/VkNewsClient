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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.vknewsclient.NewFeedViewModel
import com.example.vknewsclient.domain.FeedPost
import com.example.vknewsclient.ui.theme.NewsFeedScreenState.Posts

@Composable
fun HomeScreen(modifier: Modifier, onCommentsClickListener: (FeedPost) -> Unit) {

    val viewModel: NewFeedViewModel = viewModel()

    val screenState = viewModel.screenState.observeAsState(NewsFeedScreenState.Initial)

    val currentState = screenState.value

    when (currentState) {
        is Posts -> FeedPosts(
            posts = currentState.posts,
            modifier = modifier,
            viewModel = viewModel,
            onCommentsClickListener = onCommentsClickListener
        )

        is NewsFeedScreenState.Initial -> {}
    }
}

@Composable
fun FeedPosts(
    posts: List<FeedPost>,
    modifier: Modifier,
    viewModel: NewFeedViewModel,
    onCommentsClickListener: (FeedPost) -> Unit
) {
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
        items(items = posts, key = { it.id }) { post ->
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
                    onCommentClickListener = { onCommentsClickListener(post) },
                )
            }
        }
    }
}