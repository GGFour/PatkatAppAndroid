package com.example.mdp_frontend.ui.all_categories

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mdp_frontend.domain.model.Response
import com.example.mdp_frontend.domain.repository.Categories
import com.example.mdp_frontend.model.TopBarItem
import com.example.mdp_frontend.ui.components.CategoryBox
import com.example.mdp_frontend.ui.components.TopBar
import com.example.mdp_frontend.ui.theme.md_theme_dark_background
import com.example.mdp_frontend.ui.theme.md_theme_light_background

@Composable
fun AllCategories(
    onNavUp: () -> Unit,
    onCategoryBoxChecked: (String) -> Unit,
    viewModel: AllCategoriesViewModel = hiltViewModel(),
) {
    Column(modifier = Modifier.
    background(if (isSystemInDarkTheme()) md_theme_dark_background else md_theme_light_background))
    {
        TopBar(model = TopBarItem(
            title = "All Categories",
            onNavUpPressed = onNavUp
        ))

        when (viewModel.categoriesResponse) {
            is Response.Failure -> {}
            is Response.Success<Categories> -> {
                LazyVerticalGrid(
                    GridCells.Fixed(2)
                ) {
                    items((viewModel.categoriesResponse as Response.Success<Categories>).data) { item ->
                        Box(
                            modifier = Modifier
                                .padding(16.dp)
                                .size(150.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CategoryBox(
                                category = item,
                                onClick = onCategoryBoxChecked
                            )
                        }

                    }
                }
            }
            is Response.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}
