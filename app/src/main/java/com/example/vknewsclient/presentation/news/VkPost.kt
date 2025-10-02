package com.example.vknewsclient.presentation.news

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.unit.dp
import com.example.vknewsclient.R
import com.example.vknewsclient.domain.FeedPost
import com.example.vknewsclient.domain.StatisticItem
import com.example.vknewsclient.domain.StatisticType


@Composable
fun VkPost(
    modifier: Modifier = Modifier,
    feedPost: FeedPost = FeedPost(),
    onLikeClickListener: (StatisticItem) -> Unit,
    onShareClickListener: (StatisticItem) -> Unit,
    onViewsClickListener: (StatisticItem) -> Unit,
    onCommentClickListener: (StatisticItem) -> Unit,

    ) {

    Card(modifier = modifier) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(8.dp),

            ) {
            PostHeader(
                userAvatarId = feedPost.userAvatarId,
                userName = feedPost.communityName,
                time = feedPost.publicationData
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text(text = feedPost.postText)

            Spacer(modifier = Modifier.height(8.dp))

            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp),
                painter = painterResource(feedPost.postImageId),
                contentDescription = "post image",
                contentScale = ContentScale.FillWidth
            )

            Spacer(modifier = Modifier.height(8.dp))

            Statistics(
                statistics = feedPost.statistics,
                onLikeClickListener = onLikeClickListener,
                onViewsClickListener = onViewsClickListener,
                onShareClickListener = onShareClickListener,
                onCommentClickListener = onCommentClickListener,
            )
        }
    }
}

@Composable
private fun PostHeader(userAvatarId: Int, userName: String, time: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
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
            Text(
                text = time,
                color = MaterialTheme.colorScheme.onSecondary
            )
        }
        Icon(
            tint = MaterialTheme.colorScheme.onSecondary,
            painter = painterResource(R.drawable.ic_more_vert),
            contentDescription = "more button"
        )

    }
}

@Composable
private fun Statistics(
    statistics: List<StatisticItem>,
    onLikeClickListener: (StatisticItem) -> Unit,
    onShareClickListener: (StatisticItem) -> Unit,
    onViewsClickListener: (StatisticItem) -> Unit,
    onCommentClickListener: (StatisticItem) -> Unit,
) {

    Row(
        modifier = Modifier
            .height(40.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        val viewsItem = statistics.getItemByType(StatisticType.VIEWS)
        Row(modifier = Modifier.weight(1f)) {
            IconWithText(
                iconId = R.drawable.ic_views_count,
                text = viewsItem.count.toString(),
                onIconClickListener = { onViewsClickListener(viewsItem) }
            )
        }
        val reposts = statistics.getItemByType(StatisticType.SHARES)
        val comments = statistics.getItemByType(StatisticType.COMMENTS)
        val likes = statistics.getItemByType(StatisticType.LIKES)
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.weight(1f)) {
            IconWithText(
                iconId = R.drawable.ic_share,
                text = reposts.count.toString(),
                onIconClickListener = { onShareClickListener(reposts) })
            IconWithText(
                iconId = R.drawable.ic_comment,
                text = comments.count.toString(),
                onIconClickListener = { onCommentClickListener(comments) })
            IconWithText(
                iconId = R.drawable.ic_like,
                text = likes.count.toString(),
                onIconClickListener = { onLikeClickListener(likes) })
        }
    }
}

private fun List<StatisticItem>.getItemByType(type: StatisticType): StatisticItem {
    return this.find { it.type == type } ?: throw IllegalStateException(
        "Invalid StatisticType"
    )
}

@Composable
private fun IconWithText(iconId: Int, text: String, onIconClickListener: () -> Unit) {
    Row(
        modifier = Modifier.clickable(onClick = { onIconClickListener() }),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(iconId),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSecondary
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = text,
            color = MaterialTheme.colorScheme.onSecondary,
        )
    }

}