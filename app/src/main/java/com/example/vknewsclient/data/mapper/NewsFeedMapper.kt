package com.example.vknewsclient.data.mapper

import com.example.vknewsclient.data.model.NewsItemResponseDto
import com.example.vknewsclient.domain.FeedPost
import com.example.vknewsclient.domain.StatisticItem
import com.example.vknewsclient.domain.StatisticType
import java.text.SimpleDateFormat
import java.util.Locale

class NewsFeedMapper {
    fun mapNewsItemsToFeedPosts(responseDto: NewsItemResponseDto): List<FeedPost> {
        val result = mutableListOf<FeedPost>()
        for (item in responseDto.results) {
            result.add(
                FeedPost(
                    id = item.id,
                    communityName = item.sourceName ?: "",
                    communityImageUrl = item.sourceIconUrl,
                    publicationData = dateFormat(item.publicationDate),
                    postText = item.description ?: "",
                    postImageUrl = item.contentImageUrl,
                    statistics = listOf(
                        StatisticItem(StatisticType.LIKES, (0..3000).random()),
                        StatisticItem(StatisticType.VIEWS, (0..2000).random()),
                        StatisticItem(StatisticType.SHARES, (0..8000).random()),
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

    private fun dateFormat(dateDto: String): String {
        return try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            val outputFormat = SimpleDateFormat("d MMMM yyyy, HH:mm", Locale.getDefault())
            val date = inputFormat.parse(dateDto)
            outputFormat.format(date)
        } catch (e: Exception) {
            dateDto // возвращаем исходную строку при ошибке
        }
    }
}