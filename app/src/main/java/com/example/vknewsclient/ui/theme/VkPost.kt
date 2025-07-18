package com.example.vknewsclient.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.vknewsclient.R


@Composable
fun VkPost(
    userName: String,
    userAvatarId: Int,
    time: String,
    postText: String,
    postImageId: Int,
    views: Int,
    likes: Int,
    reposts: Int,
    comments: Int
) {

    Card {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape),
                    painter = painterResource(userAvatarId),
                    contentDescription = "user avatar"
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column(
                    modifier = Modifier
                        .weight(1f)
                ) {
                    Text(
                        text = userName,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = time,
                        color = MaterialTheme.colorScheme.onSecondary
                    )
                }
                Icon(
                    tint = MaterialTheme.colorScheme.onSecondary,
                    painter = painterResource(R.drawable.ic_more_vert),
                    contentDescription = "more button"
                )

            }
            Text(text = postText)
            Image(
                modifier = Modifier
                    .fillMaxWidth(),
                painter = painterResource(postImageId),
                contentDescription = "post image",
                contentScale = ContentScale.Crop
            )
            Row { }
        }
    }
}

@Preview
@Composable
fun PreviewVkPostLite() {
    VkNewsClientTheme (darkTheme = false) {
        VkPost(
            userName = "Уволено",
            userAvatarId = R.drawable.post_comunity_thumbnail,
            time = "14:00",
            postText = "кобаныч, когда узнал, что если сотрудникам не платить они начинают умерать от голода",
            postImageId = R.drawable.post_content_image,
            views = 206,
            likes = 491,
            reposts = 206,
            comments = 11
        )
    }
}

@Preview
@Composable
fun PreviewVkPostDark() {
    VkNewsClientTheme (darkTheme = true) {
        VkPost(
            userName = "Уволено",
            userAvatarId = R.drawable.post_comunity_thumbnail,
            time = "14:00",
            postText = "кобаныч, когда узнал, что если сотрудникам не платить они начинают умерать от голода",
            postImageId = R.drawable.post_content_image,
            views = 206,
            likes = 491,
            reposts = 206,
            comments = 11
        )
    }

}