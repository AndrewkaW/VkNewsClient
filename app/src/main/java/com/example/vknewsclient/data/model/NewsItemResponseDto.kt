package com.example.vknewsclient.data.model

data class NewsItemResponseDto(
    val results: List<NewsItemDto>,
    val nextPage: String?
)
