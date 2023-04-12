package com.example.mdp_frontend.ui.screen.subscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mdp_frontend.R
import com.example.mdp_frontend.model.CategoryBoxItem
import com.example.mdp_frontend.model.TopBarItem
import com.example.mdp_frontend.ui.components.CategoryBox
import com.example.mdp_frontend.ui.components.TopBar
import com.example.mdp_frontend.ui.theme.md_theme_light_background
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@Composable
fun AllCategories(
    onNavUp: () -> Unit,
    onCategoryBoxChecked: () -> Unit,
) {
    val db = Firebase.firestore
    var categoryBoxItems by remember { mutableStateOf(listOf<CategoryBoxItem>()) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit){
        db.collection("categories")
            .get()
            .addOnSuccessListener { documents ->
                val items = mutableListOf<CategoryBoxItem>()
                for (document in documents) {
                    val categoryName = document.getString("name") ?: ""
                    val categoryBoxItem = CategoryBoxItem(name = categoryName, imageResId = R.drawable.demo)
                    items.add(categoryBoxItem)
                }
                categoryBoxItems = items
                isLoading = false
            }
    }



    Column(modifier = Modifier.
    background(md_theme_light_background))
    {
        TopBar(model = TopBarItem(
            title = "All Categories",
            onNavUpPressed = onNavUp
        ))


        //sample list of items, it will be edited when we have our items from database
        /*
        val items = listOf(
            CategoryBoxItem(name = "Category 1", imageResId = R.drawable.demo),
            CategoryBoxItem(name = "Category 2", imageResId = R.drawable.demo),
            CategoryBoxItem(name = "Category 3", imageResId = R.drawable.demo),
            CategoryBoxItem(name = "Category 4", imageResId = R.drawable.demo)
        )

         */
        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else
        {

            LazyVerticalGrid(
                GridCells.Fixed(2)
            ) {
                items(categoryBoxItems) { item ->
                    Box(
                        modifier = Modifier
                            .padding(16.dp)
                            .size(150.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CategoryBox(
                            categoryBoxItem = item,
                            onClick = onCategoryBoxChecked
                        )
                    }

                }
            }
        }
    }
}

/*
@Composable
@Preview
fun AllPreview() {
    AllCategories()
}


 */

