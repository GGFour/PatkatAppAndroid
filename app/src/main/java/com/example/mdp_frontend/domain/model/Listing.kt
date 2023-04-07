package com.example.mdp_frontend.domain.model

data class Listing(
    val id: String = "",
    val pictureUrl: String = "",
    val title: String = "",
    val description: String = "",
    val priceStr: String = "",
    val price: Double = 0.0,
    val latitude: Float? = null,
    val longitude: Float? = null,
    val category: String? = null,
    val publisher: User? = null,
    val rating: Int = 0,
)