package com.example.vknewsclient.presentation.news

import android.annotation.SuppressLint
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.vknewsclient.R
import com.example.vknewsclient.domain.FeedPost
import com.example.vknewsclient.domain.StatisticItem
import com.example.vknewsclient.domain.StatisticType
import com.example.vknewsclient.ui.theme.DarkRed


@Composable
fun VkPost(
    modifier: Modifier = Modifier,
    feedPost: FeedPost,
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
                userImageUrl = feedPost.communityImageUrl,
                userName = feedPost.communityName,
                time = feedPost.publicationData
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text(text = feedPost.postText)

            Spacer(modifier = Modifier.height(8.dp))

            AsyncImage(
                model = feedPost.postImageUrl,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
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
                isFavorite = feedPost.isFavorite
            )
        }
    }
}

@Composable
private fun PostHeader(userImageUrl: String, userName: String, time: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        AsyncImage(
            model = userImageUrl,
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape),
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
    isFavorite: Boolean
) {

    Row(
        modifier = Modifier
            .height(40.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        val views = statistics.getItemByType(StatisticType.VIEWS)
        Row(modifier = Modifier.weight(1f)) {
            IconWithText(
                iconId = R.drawable.ic_views_count,
                text = formatStatistic(views.count),
                onIconClickListener = { onViewsClickListener(views) }
            )
        }
        val reposts = statistics.getItemByType(StatisticType.SHARES)
        val comments = statistics.getItemByType(StatisticType.COMMENTS)
        val likes = statistics.getItemByType(StatisticType.LIKES)
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.weight(1f)) {
            IconWithText(
                iconId = R.drawable.ic_share,
                text = formatStatistic(reposts.count),
                onIconClickListener = { onShareClickListener(reposts) })
            IconWithText(
                iconId = R.drawable.ic_comment,
                text = formatStatistic(comments.count),
                onIconClickListener = { onCommentClickListener(comments) })
            IconWithText(
                iconId = if (isFavorite) R.drawable.ic_like_set else R.drawable.ic_like,
                text = formatStatistic(likes.count),
                onIconClickListener = { onLikeClickListener(likes) },
                tint = if (isFavorite) DarkRed else MaterialTheme.colorScheme.onSecondary,
            )

        }
    }
}

@SuppressLint("DefaultLocale")
private fun formatStatistic(count: Int): String {
    return if (count > 100_000) {
        String.format("%sK", (count / 1000))
    } else if (count > 1000) {
        String.format("%.1fK", (count / 1000f))
    } else {
        count.toString()
    }
}

private fun List<StatisticItem>.getItemByType(type: StatisticType): StatisticItem {
    return this.find { it.type == type } ?: throw IllegalStateException(
        "Invalid StatisticType"
    )
}

@Composable
private fun IconWithText(
    iconId: Int,
    text: String,
    onIconClickListener: () -> Unit,
    tint: Color = MaterialTheme.colorScheme.onSecondary
) {
    Row(
        modifier = Modifier.clickable(onClick = { onIconClickListener() }),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(20.dp),
            painter = painterResource(iconId),
            contentDescription = null,
            tint = tint
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = text,
            color = MaterialTheme.colorScheme.onSecondary,
        )
    }
}