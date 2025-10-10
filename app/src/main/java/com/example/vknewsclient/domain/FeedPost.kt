package com.example.vknewsclient.domain

import kotlin.random.Random

data class FeedPost(
    val id: String,
    val communityName: String,
    val communityImageUrl: String?,
    val publicationData: String,
    val postText: String,
    val postImageUrl: String?,
    val statistics: List<StatisticItem>,
    val isFavorite: Boolean = Random.nextBoolean()
)
