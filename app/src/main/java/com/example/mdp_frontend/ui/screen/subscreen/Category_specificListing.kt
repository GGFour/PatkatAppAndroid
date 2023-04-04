package com.example.mdp_frontend.ui.screen.subscreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mdp_frontend.model.ListingCardItem
import com.example.mdp_frontend.model.TopbarItem
import com.example.mdp_frontend.ui.components.ListingCard
import com.example.mdp_frontend.ui.components.TopBar

@Composable
fun Category_specificListing() {

    //sample test, will be change once we have the database set up
    val sampleListings = listOf(
        ListingCardItem(
            title = "Backyard cleaning",
            category = "Household",
            location = "Itäkeskus, Hel",
            date = "2023-04-03"
        ),
        ListingCardItem(
            title = "Grocery shopping",
            category = "Errands",
            location = "Kamppi, Hel",
            date = "2023-04-04"
        ),
        ListingCardItem(
            title = "Dog walking",
            category = "Pet care",
            location = "Kallio, Hel",
            date = "2023-04-05"
        )
    )
    Column {
        TopBar(model = TopbarItem(
            title = "Category Listings",
            onBackPressed = { /* do something */ }
        ))

        LazyColumn(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(sampleListings) { listing ->
                ListingCard(listing)
            }
        }
    }
}

@Composable
@Preview
fun ChatPreview() {
    Category_specificListing()
}

