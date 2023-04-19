package com.example.mdp_frontend.ui.screen.subscreen

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mdp_frontend.domain.model.Response
import com.example.mdp_frontend.model.TopBarItem
import com.example.mdp_frontend.ui.components.Listings
import com.example.mdp_frontend.ui.components.TopBar
import com.example.mdp_frontend.viewmodel.CategorySpecificListingsViewModel

@Composable
fun Category_specificListing(
    modifier: Modifier,
    onNavUp: () -> Unit,
    onListingCardClick: (String) -> Unit,
    context: Context,
    viewModel: CategorySpecificListingsViewModel = hiltViewModel()
) {

    Column {
        TopBar(
            model = TopBarItem(
                title = "Category Listings",
                onNavUpPressed = onNavUp
            )
        )

        when (viewModel.getListingsResponse) {
            is Response.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            is Response.Success -> {
                Listings(
                    listings = viewModel.listings,
                    onListingCardClick = { listingId ->
                        onListingCardClick(listingId)
                    }
                )
            }
            is Response.Failure -> {
                Text("Failed to get the listings :<")
            }
        }
    }
}


