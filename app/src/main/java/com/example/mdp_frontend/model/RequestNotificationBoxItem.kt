package com.example.mdp_frontend.model

import android.graphics.Bitmap
import androidx.compose.ui.graphics.ImageBitmap

data class RequestNotificationBoxItem(
    val listingId: String,
    //val taskerPicture: Bitmap,
    val taskerName: String,
    val taskerRating: Float,
    val requestTime: String,
    val serviceName: String
)