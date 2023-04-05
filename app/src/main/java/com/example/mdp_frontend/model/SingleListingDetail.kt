package com.example.mdp_frontend.model

data class SingleListingDetail(
    val id: String,
    val pictureUrl: String,
    val title: String,
    val description: String,
    val publisherPictureUrl: String,
    val publisherName: String,
    val rating: Int,
    val price: Double
)