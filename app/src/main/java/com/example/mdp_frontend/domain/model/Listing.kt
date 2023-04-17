package com.example.mdp_frontend.domain.model

import android.net.Uri
import com.google.firebase.Timestamp

enum class ListingState {
    Active,
    Noticed,
    WIP,
    Finished,
    Abandoned,
}

data class Listing(
    val id: String = "",
    val pictureUrl: String = "",
    val pictureUri: String? = null,
    val title: String = "",
    val description: String = "",
    val priceStr: String = "",
    val price: Double = 0.0,
    val latitude: Float? = null,
    val longitude: Float? = null,
    val category: String? = null,
    val publisher: User? = null,
    val assignee: User? = null,
    val rating: Int = 0,
    val publishedDate: Timestamp? = null,
    val state: ListingState = ListingState.Active,
)