package com.example.vknewsclient.presentation.comments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.vknewsclient.domain.FeedPost

@Suppress("UNCHECKED_CAST")
class CommentsViewModelFactory(private val feedPost: FeedPost): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CommentsViewModel(feedPost = feedPost) as T
    }
}