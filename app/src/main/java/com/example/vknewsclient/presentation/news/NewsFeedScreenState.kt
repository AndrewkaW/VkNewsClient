package com.example.vknewsclient.presentation.news

import com.example.vknewsclient.domain.FeedPost

sealed class NewsFeedScreenState {
    object Initial : NewsFeedScreenState()
    object Loading : NewsFeedScreenState()
    data class Posts(
        val posts: List<FeedPost>, val loadingNextPosts: Boolean = false,
        val isRefreshing: Boolean = false
    ) :
        NewsFeedScreenState()
}