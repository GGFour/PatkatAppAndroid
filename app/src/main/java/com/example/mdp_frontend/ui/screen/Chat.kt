package com.example.mdp_frontend.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mdp_frontend.R
import com.example.mdp_frontend.model.MessageBubbleItem
import com.example.mdp_frontend.ui.components.MessageBubble
import com.example.mdp_frontend.ui.theme.md_theme_light_background

@Composable
fun ChatScreen(modifier: Modifier) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp).
            background(MaterialTheme.colorScheme.background)
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = "Messages",
            style = MaterialTheme.typography.displaySmall,
            //fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // test sample of messages
        val messages = listOf(
            MessageBubbleItem(
                senderName = stringResource(R.string.senderName),
                senderImage = ImageBitmap.imageResource(R.drawable.demo),
                messageContent = stringResource(R.string.messageContent),
                sentAt = stringResource(R.string.sentAt)
            ),
            MessageBubbleItem(
                senderName = stringResource(R.string.senderName),
                senderImage = ImageBitmap.imageResource(R.drawable.demo),
                messageContent = stringResource(R.string.messageContent),
                sentAt = stringResource(R.string.sentAt)
            ),
            MessageBubbleItem(
                senderName = stringResource(R.string.senderName),
                senderImage = ImageBitmap.imageResource(R.drawable.demo),
                messageContent = stringResource(R.string.messageContent),
                sentAt = stringResource(R.string.sentAt)
            )
        )
        LazyColumn {
            items(messages) { message ->
                MessageBubble(message)

                Spacer(modifier = Modifier.height(8.dp))

                Divider()
            }
        }
    }
}

@Composable
@Preview
fun ChatPreview() {
    ChatScreen(modifier = Modifier.
    background(md_theme_light_background))
}

