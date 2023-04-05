package com.example.mdp_frontend.model

import androidx.compose.ui.graphics.ImageBitmap

data class RequestNotificationBoxItem(
    val taskerPicture: ImageBitmap,
    val taskerName: String,
    val taskerRating: Float,
    val requestTime: String,
    //val serviceTitle: String,
    val serviceName: String
)