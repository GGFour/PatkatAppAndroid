package com.example.mdp_frontend.ui.components


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.mdp_frontend.model.TopbarItem


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(model: TopbarItem) {
    TopAppBar(
        title = { Text(text = model.title) },
        navigationIcon = {
            IconButton(onClick = model.onBackPressed) {
                Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
            }
        },
        actions = {
            IconButton(onClick = model.onClosePressed) {
                Icon(Icons.Filled.Close, contentDescription = "Close")
            }
        }
    )
}
@Composable
@Preview
fun TopbarPreview(){
    val topbarItem = TopbarItem(
        title = "My Preview",
        onBackPressed = { /* handle back press */ },
        onClosePressed = { /* handle close press */ }
    )
    TopBar(topbarItem)
}
