package com.example.vknewsclient.domain

import com.example.vknewsclient.R

data class FeedPost(
    val communityName: String = "/dev/null",
    val userAvatarId: Int = R.drawable.post_comunity_thumbnail,
    val publicationData: String = "14:00",
    val postText: String = "кобаныч, когда узнал, что если сотрудникам не платить они начинают умерать от голода",
    val postImageId: Int = R.drawable.post_content_image,
    val statistics : List<StatisticItem> = listOf(
        StatisticItem(type = StatisticType.VIEWS, count = 996),
        StatisticItem(type = StatisticType.SHARES, count = 7),
        StatisticItem(type = StatisticType.LIKES, count = 8),
        StatisticItem(type = StatisticType.COMMENTS, count = 27),
    )
)
