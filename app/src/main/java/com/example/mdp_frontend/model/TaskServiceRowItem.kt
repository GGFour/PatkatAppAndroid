package com.example.mdp_frontend.model

import com.example.mdp_frontend.domain.model.ListingState

data class TaskServiceRowItem(
    val name: String,
    val status: ListingState,
    val price: Int,
)