package com.example.mdp_frontend.ui.create_listing.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ExpandLess
import androidx.compose.material.icons.outlined.ExpandMore
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.mdp_frontend.domain.model.Category
import com.example.mdp_frontend.domain.model.Response
import com.example.mdp_frontend.domain.repository.CategoriesResponse

@Composable
fun PickCategoryScreen(
    category: String?,
    updateCategory: (String?) -> Unit,
    categoriesResponse: CategoriesResponse,
    onNextPressed: () -> Unit,
    onCancelPressed: () -> Unit,
) {
    ListingScreenContainer(onNextPressed = onNextPressed, onCancelPressed = onCancelPressed) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            var expanded by remember { mutableStateOf(false) }
            when (categoriesResponse) {
                is Response.Loading -> {
                    Box(modifier = Modifier.fillMaxSize()) {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }
                }
                is Response.Success -> {
                    TextButton(
                        onClick = { expanded = true},
                    ) {
                        Text(category ?: "Select the category")
                        Icon(
                            imageVector = if (expanded) Icons.Outlined.ExpandLess else Icons.Outlined.ExpandMore,
                            contentDescription = if (expanded) "collapse" else "expand",
                        )
                    }
                    DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                        categoriesResponse.data.forEach { category: Category ->
                            DropdownMenuItem(text = { Text(category.name) }, onClick = {
                                updateCategory(category.name)
                                expanded = false
                            })
                        }
                    }
                }
                is Response.Failure -> {
                    Text("Smth went wrong. . .")
                }
            }

        }
    }
}
