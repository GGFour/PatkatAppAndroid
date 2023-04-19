package com.example.mdp_frontend.viewmodel

import android.content.Context
import android.location.Geocoder
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mdp_frontend.domain.model.Listing
import com.example.mdp_frontend.domain.model.Response
import com.example.mdp_frontend.domain.repository.Listings
import com.example.mdp_frontend.domain.repository.ListingsResponse
import com.example.mdp_frontend.domain.use_case.listing.ListingUseCases
import com.example.mdp_frontend.model.ListingCardItem
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val listingUseCases: ListingUseCases,
    @ApplicationContext private val context: Context,
) : ViewModel() {
    var getListingsResponse by mutableStateOf<ListingsResponse>(Response.Loading)
        private set
    var listings = mutableListOf<ListingCardItem>()
        private set

    init {
        getListings()
    }

    private fun getListings(limit: Long = 5) = viewModelScope.launch {
        listingUseCases.getListings(limit).collect { response ->
            getListingsResponse = response
            if (getListingsResponse is Response.Success) {
                listings.clear()
                listings.addAll((getListingsResponse as Response.Success<Listings>).data.map {listing: Listing ->
                    val address = if (listing.latitude != null && listing.longitude != null) {
                        getAddressFromLocation(
                            listing.latitude.toFloat(),
                            listing.longitude.toFloat()
                        )
                    } else {
                        "Error: Invalid location"
                    }
                    ListingCardItem(
                        title = listing.title,
                        category = listing.category ?: "",
                        location = address,
                        id = listing.id,
                        date = listing.publishedDate
                    )
                })
            }
        }
    }

    private fun getAddressFromLocation(latitude: Float?, longitude: Float?): String {
        val geocoder = Geocoder(context)
        val lat = latitude?.toDouble() ?: 0.0
        val lng = longitude?.toDouble() ?: 0.0
        val addresses = geocoder.getFromLocation(lat, lng, 1)
        return addresses?.firstOrNull()?.getAddressLine(0) ?: ""
    }
}