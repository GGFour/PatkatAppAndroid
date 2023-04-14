package com.example.mdp_frontend.model

import com.google.firebase.Timestamp

data class ListingCardItem (
    val title: String,
    val category: String,
    val location: String,
    val date: Timestamp? = null
)
