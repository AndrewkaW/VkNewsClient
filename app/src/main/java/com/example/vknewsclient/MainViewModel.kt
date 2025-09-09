package com.example.vknewsclient

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.vknewsclient.domain.FeedPost
import com.example.vknewsclient.domain.StatisticItem
import com.example.vknewsclient.ui.theme.NavigationItem


class MainViewModel : ViewModel() {

    private val initialPostList = mutableListOf<FeedPost>().apply {
        repeat(20) {
            add(
                FeedPost(id = it, communityName = "Dev $it")
            )
        }
    }

    private val _selectedNavItem = MutableLiveData<NavigationItem>(NavigationItem.Home)
    val selectedNavItem : LiveData<NavigationItem> = _selectedNavItem

    fun selectNavItem (item: NavigationItem) {
        _selectedNavItem.value = item
    }

    private val _postsLiveData = MutableLiveData<List<FeedPost>>(initialPostList)
    val postsLiveData: LiveData<List<FeedPost>> = _postsLiveData

    fun updateCount(feedPost: FeedPost, item: StatisticItem) {
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

        val oldList = _postsLiveData.value?.toMutableList() ?: mutableListOf()
        _postsLiveData.value = oldList.apply {
            replaceAll {
                if (it.id == feedPost.id) {
                    feedPost.copy(statistics = newStatistics)
                } else {
                    it
                }
            }
        }
    }

    fun deletePost(feedPost: FeedPost) {
        val updatedList = _postsLiveData.value?.toMutableList() ?: mutableListOf()
        val deletedPost = updatedList.find { it.id == feedPost.id }
        updatedList.remove(deletedPost)
        _postsLiveData.value = updatedList
    }
}