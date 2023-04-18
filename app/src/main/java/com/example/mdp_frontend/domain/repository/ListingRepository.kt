package com.example.mdp_frontend.domain.repository

import com.example.mdp_frontend.domain.model.Listing
import com.example.mdp_frontend.domain.model.Response
import kotlinx.coroutines.flow.Flow

typealias Listings = List<Listing>
typealias ListingsResponse = Response<Listings>
typealias AddListingResponse = Response<Boolean>

interface ListingRepository {
    fun getListingsFromFirestore(): Flow<ListingsResponse>

    suspend fun addListingToFirestore(listing: Listing): AddListingResponse

    //suspend fun getListingById(listingId: String?): Listing?
    suspend fun getListingById(listingId: String): Listing
}