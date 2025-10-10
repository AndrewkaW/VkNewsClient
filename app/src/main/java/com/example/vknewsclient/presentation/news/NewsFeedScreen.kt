package com.example.vknewsclient.presentation.news

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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

    when (currentState) {
        is NewsFeedScreenState.Posts -> {
            val lazyListState = rememberLazyListState()

            LaunchedEffect(currentState.isRefreshing) {
                if (!currentState.isRefreshing) {
                    lazyListState.animateScrollToItem(0)
                }
            }

            PullToRefreshBox(
                modifier = modifier,
                isRefreshing = currentState.isRefreshing,
                onRefresh = {
                    viewModel.reloadNews()
                },
            ) {

                FeedPosts(
                    posts = currentState.posts,
                    viewModel = viewModel,
                    onCommentsClickListener = onCommentsClickListener,
                    nextDateIsLoading = currentState.loadingNextPosts,
                    lazyListState = lazyListState
                )
            }
        }

        NewsFeedScreenState.Initial -> {}

        NewsFeedScreenState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = DarkBlue)
            }
        }
    }
}

@Composable
fun FeedPosts(
    posts: List<FeedPost>,
    viewModel: NewFeedViewModel,
    onCommentsClickListener: (FeedPost) -> Unit,
    nextDateIsLoading: Boolean,
    lazyListState: LazyListState
) {
    LazyColumn(
        contentPadding = PaddingValues(
            top = 16.dp,
            start = 8.dp,
            end = 8.dp,
            bottom = 8.dp
        ),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        state = lazyListState
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
                    onCommentClickListener = { onCommentsClickListener(post) },
                )
            }
        }

        item {
            if (nextDateIsLoading && posts.isNotEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator(color = DarkBlue)
                }
            } else if (posts.isNotEmpty()) {
                SideEffect { viewModel.loadNextNews() }
            }
        }
    }
}