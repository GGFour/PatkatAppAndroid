
package com.example.mdp_frontend.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mdp_frontend.R
import com.example.mdp_frontend.model.MessageBubbleItem

@Composable
fun MessageBubble(message: MessageBubbleItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Image(
            bitmap = message.senderImage,
            contentDescription = "Profile picture of ${message.senderName}",
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Column {
            Text(
                text = message.senderName,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = message.messageContent,
                fontSize = 16.sp
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = message.sentAt,
            fontSize = 12.sp,
            textAlign = TextAlign.End
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMessageBubble() {
    val message = MessageBubbleItem(
        senderName = "Hajri Ji",
        senderImage = ImageBitmap.imageResource(R.drawable.demo),
        messageContent = "Hello world!",
        sentAt = "1 hour ago"
    )

    MessageBubble(message)
}
