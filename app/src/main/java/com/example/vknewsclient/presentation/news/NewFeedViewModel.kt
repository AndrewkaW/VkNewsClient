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
        loadNews()
    }

    fun loadNews() {
        val currentState = _screenState.value
        // Если это уже список постов, то включаем индикатор обновления
        if (currentState is NewsFeedScreenState.Posts) {
            _screenState.value = currentState.copy(isRefreshing = true)
        }

        viewModelScope.launch {
            // Загружаем посты (при pull-to-refresh это будут самые свежие)
            val freshPosts = repository.loadNewsPosts()
            // Обновляем состояние с новым списком и выключаем индикатор
            _screenState.value = NewsFeedScreenState.Posts(posts = freshPosts, isRefreshing = false)
        }
    }

    fun loadNextNews() {
        val currentState = _screenState.value
        // Проверяем, что текущее состояние - это Posts и что уже не идет загрузка
        if (currentState !is NewsFeedScreenState.Posts || currentState.loadingNextPosts) {
            return
        }

        // 1. Устанавливаем флаг, что началась загрузка следующей страницы
        _screenState.value = currentState.copy(loadingNextPosts = true)

        viewModelScope.launch {
            // 2. Загружаем СЛЕДУЮЩУЮ порцию новостей
            // ПРИМЕЧАНИЕ: Ваш repository.loadNewsPosts() должен уметь загружать следующую страницу
            val newPosts = repository.loadNewsPosts()

            // 3. Создаем новый объединенный список
            val existingPosts = currentState.posts
            val mergedPosts = existingPosts.toMutableList().apply {
                addAll(newPosts)
            }.toList()

            // 4. Обновляем состояние с объединенным списком и выключаем индикаторы
            _screenState.value = NewsFeedScreenState.Posts(
                posts = mergedPosts,
                loadingNextPosts = false,
                isRefreshing = false // Также выключаем pull-to-refresh на всякий случай
            )
        }
    }

    fun changeLike(feedPost: FeedPost) {
        repository.changeLikesStatus(feedPost)
        _screenState.value = NewsFeedScreenState.Posts(posts = repository.feedPosts)
    }


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
