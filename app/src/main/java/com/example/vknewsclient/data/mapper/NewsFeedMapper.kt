package com.example.vknewsclient.data.mapper

import com.example.vknewsclient.data.model.NewsItemResponseDto
import com.example.vknewsclient.domain.FeedPost
import com.example.vknewsclient.domain.StatisticItem
import com.example.vknewsclient.domain.StatisticType

class NewsFeedMapper {
    fun mapNewsItemsToFeedPosts(responseDto: NewsItemResponseDto): List<FeedPost> {
        val result = mutableListOf<FeedPost>()
        for (item in responseDto.results) {
            result.add(
                FeedPost(
                    id = item.id,
                    communityName = item.sourceName ?: "",
                    communityImageUrl = item.sourceIconUrl,
                    publicationData = item.publicationDate,
                    postText = item.description,
                    postImageUrl = item.contentImageUrl,
                    statistics = listOf(
                        StatisticItem(StatisticType.LIKES, (0..1000).random()),
                        StatisticItem(StatisticType.VIEWS, (0..1000).random()),
                        StatisticItem(StatisticType.SHARES, (0..1000).random()),
                        StatisticItem(
                            StatisticType.COMMENTS,
                            if (item.keywords != null) item.keywords.count() else 0
                        )
                    )
                )
            )
        }
        return result
    }
}