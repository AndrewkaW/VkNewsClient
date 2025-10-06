package com.example.vknewsclient.data.repository

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

    suspend fun loadNewsPosts(): List<FeedPost> {
        val result =
            ApiFactory.apiService.loadNews(newsToken.toString(), "it technology")
        val newsList = mapper.mapNewsItemsToFeedPosts(result)
        _feedPosts.addAll(newsList)
        return newsList
    }

    suspend fun addLike(feedPost: FeedPost) {
        val oldStatistics = feedPost.statistics.toMutableList()
        val newStatistics = oldStatistics.apply {
            replaceAll { item ->
                if (item.type == StatisticType.LIKES) {
                    item.copy(count = item.count + 1)
                } else {
                    item
                }
            }
        }
        val newPost = feedPost.copy(statistics = newStatistics, isFavorite = true)
        val postIndex = _feedPosts.indexOf(feedPost)
        _feedPosts[postIndex] = newPost
    }
}