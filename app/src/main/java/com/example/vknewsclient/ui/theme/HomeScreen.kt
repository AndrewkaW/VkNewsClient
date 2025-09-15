package com.example.vknewsclient.ui.theme

import androidx.activity.compose.BackHandler
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
import com.example.vknewsclient.domain.FeedPost
import com.example.vknewsclient.ui.theme.HomeScreenState.Comments
import com.example.vknewsclient.ui.theme.HomeScreenState.Posts

@Composable
fun HomeScreen(modifier: Modifier, viewModel: MainViewModel) {

    val screenState = viewModel.screenState.observeAsState(HomeScreenState.Initial)

    val currentState = screenState.value

    when (currentState) {
        is Posts -> FeedPosts(
            posts = currentState.posts,
            modifier = modifier,
            viewModel = viewModel
        )

        is Comments -> {
            CommentsScreen(
                feedPost = currentState.feedPost,
                comments = currentState.comments,
                onBackPressed = {
                    viewModel.closeComments()
                }
            )
            BackHandler {
                viewModel.closeComments()
            }
        }

        is HomeScreenState.Initial -> {}

    }

}

@Composable
fun FeedPosts(posts: List<FeedPost>, modifier: Modifier, viewModel: MainViewModel) {
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
                    onCommentClickListener = { viewModel.showComments(post) },
                )
            }
        }
    }
}