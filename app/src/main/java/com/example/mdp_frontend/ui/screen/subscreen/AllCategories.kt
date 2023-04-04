package com.example.mdp_frontend.ui.screen.subscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mdp_frontend.R
import com.example.mdp_frontend.model.CategoryBoxItem
import com.example.mdp_frontend.model.TopbarItem
import com.example.mdp_frontend.ui.components.CategoryBox
import com.example.mdp_frontend.ui.components.TopBar
import com.example.mdp_frontend.ui.theme.md_theme_light_background

@Composable
fun AllCategories() {
    Column(modifier = Modifier.
    background(md_theme_light_background))
    {
        TopBar(model = TopbarItem(
            title = "My Screen",
            onBackPressed = { /* do something */ }
        ))


        //sample list of items, it will be edited when we have our items from database
        val items = listOf(
            CategoryBoxItem(name = "Category 1", imageResId = R.drawable.demo),
            CategoryBoxItem(name = "Category 2", imageResId = R.drawable.demo),
            CategoryBoxItem(name = "Category 3", imageResId = R.drawable.demo),
            CategoryBoxItem(name = "Category 4", imageResId = R.drawable.demo)
        )

        LazyVerticalGrid(
            GridCells.Fixed(2)
        ) {
            items(items) { item ->
                Box(
                    modifier = Modifier
                        .padding(16.dp)
                        .size(150.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CategoryBox(categoryBoxItem = item)
                }
            }
        }
    }
}

@Composable
@Preview
fun AllPreview() {
    AllCategories()
}

