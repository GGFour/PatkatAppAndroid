package com.example.mdp_frontend.model

import androidx.compose.ui.graphics.ImageBitmap

data class MessageBubbleItem(
    val senderName: String,
    val senderImage: ImageBitmap,
    val messageContent: String,
    val sentAt: String
)
