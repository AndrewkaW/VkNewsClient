package com.example.vknewsclient.presentation.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.vknewsclient.domain.FeedPost
import com.example.vknewsclient.domain.StatisticItem

class NewFeedViewModel : ViewModel() {

    private val initialPostList = mutableListOf<FeedPost>().apply {
        repeat(20) {
            add(
                FeedPost(id = it, communityName = "Dev $it")
            )
        }
    }

    private val initialState = NewsFeedScreenState.Posts(initialPostList)

    private val _screenState = MutableLiveData<NewsFeedScreenState>(initialState)
    val screenState: LiveData<NewsFeedScreenState> = _screenState

    fun updateCount(feedPost: FeedPost, item: StatisticItem) {
        val currentState = screenState.value
        if (currentState !is NewsFeedScreenState.Posts) return

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
        _screenState.value = NewsFeedScreenState.Posts(posts = newPosts)
    }

    fun deletePost(feedPost: FeedPost) {
        val currentState = _screenState.value
        if (currentState !is NewsFeedScreenState.Posts) return
        val updatedList = currentState.posts.toMutableList()
        val deletedPost = updatedList.find { it.id == feedPost.id }
        updatedList.remove(deletedPost)
        _screenState.value = NewsFeedScreenState.Posts(posts = updatedList)
    }
}