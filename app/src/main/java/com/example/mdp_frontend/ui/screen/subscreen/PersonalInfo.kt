package com.example.mdp_frontend.ui.screen.subscreen

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.mdp_frontend.model.TopBarItem
import com.example.mdp_frontend.ui.components.TopBar


@Composable
fun PersonalInfo(onNavUp: () -> Unit) {
    Column {
        TopBar(model = TopBarItem(
            title = "Personal Info",
            onNavUpPressed = onNavUp
        )
        )
        Text(text = "PersonalInfo")
    }
}