package com.example.mdp_frontend.ui.components


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.mdp_frontend.model.TopBarItem


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(model: TopBarItem) {
    TopAppBar(
        title = { Text(
            text = model.title,
            style = MaterialTheme.typography.displayLarge
        ) },
        navigationIcon = {
            if (model.drawNavUp) {
                IconButton(onClick = model.onNavUpPressed) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                }
            }
        },
        actions = {
            if (model.drawClose) {
                IconButton(onClick = model.onClosePressed) {
                    Icon(Icons.Filled.Close, contentDescription = "Close")
                }
            }
        }
    )
}
@Composable
@Preview
fun TopBarPreview(){
    val topBarItem = TopBarItem(
        title = "My Preview",
        drawNavUp = true,
        drawClose = true,
        onNavUpPressed = { /* handle back press */ },
        onClosePressed = { /* handle close press */ }
    )
    TopBar(topBarItem)
}
