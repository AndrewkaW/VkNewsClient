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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.vknewsclient.domain.PostComment

@Composable
fun Comment(modifier: Modifier = Modifier, postComment: PostComment) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = 4.dp,
                vertical = 2.dp
            )
    ) {
        Row(
            modifier.padding(4.dp)
        ) {
            Image(
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape),
                painter = painterResource(postComment.authorAvatarId),
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(4.dp))
            Column {
                Text(text = postComment.authorName)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = postComment.commentText)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = postComment.publicDate)
            }
        }
    }
}

@Preview
@Composable
fun Test() {
    Comment(
        postComment = PostComment(
            id = 123,
            commentText = "fsgfsdggfgdsgfsdfgdsfgsdfgdsgergfrvdvwerfwdf  egfevdv errgev reg evfd dfq sefedrerqgqerg qevfe eger dqsfg eqgqe cve ferg qefv qefq qefeqf qef def qefq ereqf def wegfeq eq"
        )
    )
}