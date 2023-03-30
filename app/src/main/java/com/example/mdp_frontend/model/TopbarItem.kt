package com.example.mdp_frontend.model

data class TopbarItem(
    val title: String,
    val onBackPressed: () -> Unit,
    val onClosePressed: () -> Unit = { /* go back to home screen */ }
)
