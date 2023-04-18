package com.example.mdp_frontend.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.example.mdp_frontend.model.ListingCardItem

@Composable
fun Listings(
    listings: List<ListingCardItem>,
    onListingCardClick: (String) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(listings) { listing ->
            ListingCard(
                listing,
                onClick = onListingCardClick
            )
        }
    }
}