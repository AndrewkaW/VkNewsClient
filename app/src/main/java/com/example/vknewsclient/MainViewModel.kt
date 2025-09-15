package com.example.vknewsclient

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.vknewsclient.domain.FeedPost
import com.example.vknewsclient.domain.PostComment
import com.example.vknewsclient.domain.StatisticItem
import com.example.vknewsclient.ui.theme.HomeScreenState

class MainViewModel : ViewModel() {

    private val initialPostList = mutableListOf<FeedPost>().apply {
        repeat(20) {
            add(
                FeedPost(id = it, communityName = "Dev $it")
            )
        }
    }

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

    private val initialState = HomeScreenState.Posts(initialPostList)

    private val _screenState = MutableLiveData<HomeScreenState>(initialState)
    val screenState: LiveData<HomeScreenState> = _screenState

    private var savedState: HomeScreenState? = initialState

    fun showComments(feedPost: FeedPost) {
        savedState = _screenState.value
        _screenState.value =
            HomeScreenState.Comments(feedPost = feedPost, comments = initialCommentsList)
    }

    @SuppressLint("NullSafeMutableLiveData")
    fun closeComments() {
        _screenState.value = savedState
    }

    fun updateCount(feedPost: FeedPost, item: StatisticItem) {
        val currentState = screenState.value
        if (currentState !is HomeScreenState.Posts) return

        val oldStatistics = feedPost.statistics
        val newStatistics = oldStatistics.toMutableList().apply {
            replaceAll { oldItem ->
                if (oldItem.type == item.type) {
                    oldItem.copy(count = oldItem.count + 1)

                } else {
                    oldItem
                }
            }
        }

        val oldList = currentState.posts.toMutableList()
        val newPosts = oldList.apply {
            replaceAll {
                if (it.id == feedPost.id) {
                    feedPost.copy(statistics = newStatistics)
                } else {
                    it
                }
            }
        }
        _screenState.value = HomeScreenState.Posts(posts = newPosts)
    }

    fun deletePost(feedPost: FeedPost) {
        val currentState = _screenState.value
        if (currentState !is HomeScreenState.Posts) return
        val updatedList = currentState.posts.toMutableList()
        val deletedPost = updatedList.find { it.id == feedPost.id }
        updatedList.remove(deletedPost)
        _screenState.value = HomeScreenState.Posts(posts = updatedList)
    }
}