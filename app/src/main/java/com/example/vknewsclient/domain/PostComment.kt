package com.example.vknewsclient.domain

import com.example.vknewsclient.R


data class PostComment(
    val id: Int,
    val authorName: String = "Author",
    val authorAvatarId: Int = R.drawable.comment_author_avatar,
    val commentText: String = "Long comment text",
    val publicDate: String = "14:00"
)
