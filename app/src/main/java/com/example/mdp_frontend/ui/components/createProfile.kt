package com.example.mdp_frontend.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.colorspace.WhitePoint
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mdp_frontend.ui.theme.md_theme_dark_tertiaryContainer
import com.example.mdp_frontend.ui.theme.md_theme_light_tertiary
import com.example.mdp_frontend.ui.theme.md_theme_light_tertiaryContainer

//This is the create profile block in home screen

@Composable
fun CreateProfileBlock(onCreateProfileClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Start working!",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp
        )
        Text(
            text = "Create a profile and start browsing!",
            color = md_theme_light_tertiaryContainer,
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {onCreateProfileClick() },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Create")
        }
    }
}