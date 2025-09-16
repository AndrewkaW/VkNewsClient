package com.example.vknewsclient

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.vknewsclient.domain.FeedPost
import com.example.vknewsclient.domain.PostComment
import com.example.vknewsclient.ui.theme.CommentsScreenState

class CommentsViewModel(feedPost: FeedPost) : ViewModel() {

    private val _commentsScreenState =
        MutableLiveData<CommentsScreenState>(CommentsScreenState.Initial)
    val commentsScreenState: LiveData<CommentsScreenState> = _commentsScreenState


    init {
        loadComments(feedPost)
    }

    fun loadComments(feedPost: FeedPost) {
        val initialCommentsList = mutableListOf<PostComment>().apply {
            repeat(30) {
                add(
                    PostComment(
                        id = it,
                        authorName = "Author $it",
                    )
                )
            }
        }
        _commentsScreenState.value =
            CommentsScreenState.Comments(feedPost = feedPost, comments = initialCommentsList)
    }
}