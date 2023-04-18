package com.example.mdp_frontend.ui.screen.subscreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mdp_frontend.domain.model.Listing
import com.example.mdp_frontend.domain.model.Response
import com.example.mdp_frontend.domain.use_case.listing.GetListingById
import com.google.firebase.firestore.FirebaseFirestoreException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListingDetailViewModel @Inject constructor(
    private val getListingById: GetListingById
) : ViewModel() {
    var listingResponse by mutableStateOf<Response<Listing>>(Response.Loading)

    fun loadListing(listingId: String?) {
        viewModelScope.launch {
            listingResponse = try {
                val listing = getListingById(listingId)
                if (listing != null) {
                    Response.Success(listing)
                } else {
                    Response.Failure(FirebaseFirestoreException("Failed to retrieve listing", FirebaseFirestoreException.Code.UNKNOWN))
                }
            } catch (e: FirebaseFirestoreException) {
                Response.Failure(e)
            }
        }
    }
}