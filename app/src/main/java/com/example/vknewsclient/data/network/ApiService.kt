package com.example.vknewsclient.data.network

import com.example.vknewsclient.data.model.NewsItemResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("latest")
    suspend fun loadNews(
        @Query("apikey") token: String,
        @Query("q") theme: String
    ): NewsItemResponseDto

    @GET("latest")
    suspend fun loadNews(
        @Query("apikey") token: String,
        @Query("q") theme: String,
        @Query("page") nextPage: String,
    ): NewsItemResponseDto
}