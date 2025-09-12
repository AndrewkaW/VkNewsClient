package com.example.vknewsclient

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.vknewsclient.domain.PostComment

class CommentsViewModel() : ViewModel() {

    private val initialCommentsList = mutableListOf<PostComment>().apply {
        repeat(30) {
            add(
                PostComment(
                    id = it,
                    authorName = "Author $it",
                )
            )
        }
    }

    private val _commentsLiveData = MutableLiveData<List<PostComment>>(initialCommentsList)
    val commentsLiveDate: LiveData<List<PostComment>> = _commentsLiveData

}