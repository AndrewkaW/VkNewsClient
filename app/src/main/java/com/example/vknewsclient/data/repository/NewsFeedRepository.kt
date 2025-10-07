package com.example.vknewsclient.data.repository

import android.util.Log
import com.example.vknewsclient.BuildConfig
import com.example.vknewsclient.data.mapper.NewsFeedMapper
import com.example.vknewsclient.data.network.ApiFactory
import com.example.vknewsclient.domain.FeedPost
import com.example.vknewsclient.domain.StatisticType

class NewsFeedRepository {
    private val newsToken = BuildConfig.NEWS_TOKEN
    private val mapper = NewsFeedMapper()

    private val _feedPosts = mutableListOf<FeedPost>()
    val feedPosts: List<FeedPost>
        get() = _feedPosts.toList()

    private var nextPage: String? = null

    suspend fun loadNewsPosts(): List<FeedPost> {
        val startPage = nextPage
        if (startPage == null && _feedPosts.isNotEmpty()) {
            return feedPosts
        }
        val result = if (startPage == null) {
            ApiFactory.apiService.loadNews(newsToken, "it technology")
        } else {
            ApiFactory.apiService.loadNews(newsToken, "it technology", startPage)
        }
        Log.d("NewsFeedRepository", "loadNewsPosts: ${result.nextPage}")
        nextPage = result.nextPage
        val newsList = mapper.mapNewsItemsToFeedPosts(result)
        _feedPosts.addAll(newsList)
        return feedPosts
    }

    fun changeLikesStatus(feedPost: FeedPost) {
        val oldStatistics = feedPost.statistics.toMutableList()
        val newStatistics = oldStatistics.apply {
            replaceAll { item ->
                if (item.type == StatisticType.LIKES) {
                    item.copy(
                        count = if (feedPost.isFavorite) {
                            item.count - 1
                        } else {
                            item.count + 1
                        }
                    )
                } else {
                    item
                }
            }
        }
        val newPost = feedPost.copy(statistics = newStatistics, isFavorite = !feedPost.isFavorite)
        val postIndex = _feedPosts.indexOf(feedPost)
        _feedPosts[postIndex] = newPost
    }
}