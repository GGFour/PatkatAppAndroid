package com.example.mdp_frontend.model

data class ListingUiState(
    val title: String = "",
    val description: String = "",
    val priceStr: String = "",
    val price: Int = 0,
    val latitude: Float? = null,
    val longitude: Float? = null,
    val category: String? = null,
    val Provider: UserModel? = null,
)
