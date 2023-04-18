package com.example.mdp_frontend.ui.screen.subscreen

import android.content.Context
import android.location.Geocoder
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.mdp_frontend.domain.use_case.listing.getListings
import com.example.mdp_frontend.ui.components.Listings
import com.example.mdp_frontend.model.ListingCardItem
import com.example.mdp_frontend.model.TopBarItem
import com.example.mdp_frontend.ui.components.TopBar

@Composable
fun Category_specificListing(
    modifier: Modifier,
    onNavUp: () -> Unit,
    onListingCardClick: (String) -> Unit,
    context: Context
) {
    val listings = remember { mutableStateOf<List<ListingCardItem>>(emptyList()) }
    val isLoading = remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        getListings(context) { listingItems ->
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

            Listings(
                listings = listings.value,
                onListingCardClick = { listingId ->
                    onListingCardClick(listingId)
                }
            )
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

