package com.example.mdp_frontend.model

import androidx.compose.ui.graphics.vector.ImageVector

data class NavTabItem(
    var label: String,
    val route: String,
    var icon: ImageVector,
    val badgeCount: Int = -1,
)
