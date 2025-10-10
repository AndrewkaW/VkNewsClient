package com.example.vknewsclient.presentation.comments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.vknewsclient.domain.FeedPost

class CommentsViewModel(feedPost: FeedPost) : ViewModel() {

    private val _commentsScreenState =
        MutableLiveData<CommentsScreenState>(CommentsScreenState.Initial)
    val commentsScreenState: LiveData<CommentsScreenState> = _commentsScreenState


    init {
        loadComments(feedPost)
    }

    fun loadComments(feedPost: FeedPost) {
        val initialCommentsList = feedPost.comments
        _commentsScreenState.value =
            CommentsScreenState.Comments(feedPost = feedPost, comments = initialCommentsList)
    }
}