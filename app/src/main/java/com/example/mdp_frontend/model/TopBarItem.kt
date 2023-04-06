package com.example.mdp_frontend.model

data class TopBarItem(
    val title: String,
    val drawNavUp: Boolean = false,
    val drawClose: Boolean = false,
    val onNavUpPressed: () -> Unit,
    val onClosePressed: () -> Unit = { /* go back to home screen */ }
)
