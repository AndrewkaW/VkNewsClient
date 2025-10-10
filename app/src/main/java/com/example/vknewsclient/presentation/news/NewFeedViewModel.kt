package com.example.vknewsclient.presentation.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vknewsclient.data.repository.NewsFeedRepository
import com.example.vknewsclient.domain.FeedPost
import com.example.vknewsclient.domain.StatisticItem
import kotlinx.coroutines.launch

class NewFeedViewModel() : ViewModel() {

    private val initialState = NewsFeedScreenState.Initial

    private val _screenState = MutableLiveData<NewsFeedScreenState>(initialState)
    val screenState: LiveData<NewsFeedScreenState> = _screenState

    private val repository = NewsFeedRepository()

    init {
        _screenState.value = NewsFeedScreenState.Loading
        loadNews()
    }

    fun loadNews() {
        val currentState = _screenState.value
        if (currentState is NewsFeedScreenState.Posts) {
            _screenState.value = currentState.copy(isRefreshing = true)
        }

        viewModelScope.launch {
            repository.loadNewsPosts()
            val freshPosts = repository.feedPosts
            _screenState.value = NewsFeedScreenState.Posts(posts = freshPosts, isRefreshing = false)
        }
    }

    fun loadNextNews() {
        val currentState = _screenState.value
        if (currentState !is NewsFeedScreenState.Posts || currentState.loadingNextPosts) {
            return
        }

        _screenState.value = currentState.copy(loadingNextPosts = true)

        viewModelScope.launch {

            repository.loadNewsPosts()
            val newPosts = repository.feedPosts

            _screenState.value = NewsFeedScreenState.Posts(
                posts = newPosts,
                loadingNextPosts = false,
                isRefreshing = false
            )
        }
    }

    fun reloadNews() {
        viewModelScope.launch {
            _screenState.value = if (_screenState.value is NewsFeedScreenState.Posts) {
                NewsFeedScreenState.Posts(posts = repository.feedPosts, isRefreshing = true)
            } else {
                return@launch
            }

            repository.reloadNewsPosts()
            _screenState.value = NewsFeedScreenState.Posts(
                posts = repository.feedPosts,
                loadingNextPosts = false,
                isRefreshing = false
            )
        }
    }

    fun changeLike(feedPost: FeedPost) {
        repository.changeLikesStatus(feedPost)
        _screenState.value = NewsFeedScreenState.Posts(posts = repository.feedPosts)
    }

    fun deletePost(feedPost: FeedPost) {
        val currentState = _screenState.value
        if (currentState !is NewsFeedScreenState.Posts) return
        val updatedList = repository.deletePost(feedPost)
        _screenState.value = NewsFeedScreenState.Posts(posts = updatedList)
    }
}
