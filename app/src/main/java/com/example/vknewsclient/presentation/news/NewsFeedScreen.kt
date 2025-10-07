package com.example.vknewsclient.presentation.news

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.vknewsclient.domain.FeedPost
import com.example.vknewsclient.ui.theme.DarkBlue


@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun NewsFeedScreen(modifier: Modifier, onCommentsClickListener: (FeedPost) -> Unit) {

    val viewModel: NewFeedViewModel = viewModel()

    val screenState = viewModel.screenState.observeAsState(NewsFeedScreenState.Initial)

    val currentState = screenState.value

    if (currentState is NewsFeedScreenState.Initial) {
        Box(
            modifier = modifier,
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = DarkBlue)
        }
    } else if (currentState is NewsFeedScreenState.Posts) {
        PullToRefreshBox(
            modifier = modifier,
            isRefreshing = currentState.isRefreshing,
            onRefresh = { viewModel.loadNews() }
        ) {
            FeedPosts(
                posts = currentState.posts,
                viewModel = viewModel,
                onCommentsClickListener = onCommentsClickListener,
                nextDateIsLoading = currentState.loadingNextPosts,
            )
        }
    }
}

@Composable
fun FeedPosts(
    posts: List<FeedPost>,
    viewModel: NewFeedViewModel,
    onCommentsClickListener: (FeedPost) -> Unit,
    nextDateIsLoading: Boolean
) {
    LazyColumn(
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
                    onLikeClickListener = {
                        viewModel.changeLike(post)
                    },
                    onShareClickListener = { viewModel.updateCount(feedPost = post, item = it) },
                    onViewsClickListener = { viewModel.updateCount(feedPost = post, item = it) },
                    onCommentClickListener = { onCommentsClickListener(post) },
                )
            }
        }

        item {
            if (nextDateIsLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator(color = DarkBlue)
                }
            } else {
                SideEffect { viewModel.loadNextNews() }
            }
        }
    }
}