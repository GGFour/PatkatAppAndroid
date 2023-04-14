package com.example.mdp_frontend.ui.screen.subscreen

import android.content.Context
import android.location.Geocoder
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mdp_frontend.domain.model.Listing
import com.example.mdp_frontend.model.ListingCardItem
import com.example.mdp_frontend.model.TopBarItem
import com.example.mdp_frontend.ui.components.ListingCard
import com.example.mdp_frontend.ui.components.TopBar
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@Composable
fun Category_specificListing(
    onNavUp: () -> Unit,
    onListingCardClick: () -> Unit,
    context: Context
) {

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

    val listings = remember { mutableStateOf<List<ListingCardItem>>(emptyList()) }
    val isLoading = remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
         Firebase.firestore.collection("listings").get()
            .addOnSuccessListener { snapshot ->
                val listingItems = snapshot.documents.mapNotNull { document ->
                    document.toObject(Listing::class.java)?.let { listing ->
                        val address = if (listing.latitude != null && listing.longitude != null) {
                            getAddressFromLocation(listing.latitude, listing.longitude, context)
                        } else {
                            "Error: Invalid location"
                        }
                        ListingCardItem(
                            title = listing.title,
                            category = listing.category ?: "",
                            location = address,
                            date = ""
                        )
                    }
                }
                listings.value = listingItems
                isLoading.value = false

            }
    }

    if (isLoading.value) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {

        Column {
            TopBar(
                model = TopBarItem(
                    title = "Category Listings",
                    onNavUpPressed = onNavUp
                )
            )

            LazyColumn(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(listings.value) { listing ->
                    ListingCard(
                        listing,
                        onClick = onListingCardClick
                    )
                }
            }
        }
    }
}

fun getAddressFromLocation(latitude: Float?, longitude: Float?, context: Context): String {
    val geocoder = Geocoder(context)
    val lat = latitude?.toDouble() ?: 0.0
    val lng = longitude?.toDouble() ?: 0.0
    val addresses = geocoder.getFromLocation(lat, lng, 1)
    return addresses?.firstOrNull()?.getAddressLine(0) ?: ""
}



/*
@Composable
@Preview
fun ChatPreview() {
    Category_specificListing()
}



 */