package com.example.mdp_frontend.domain.repository

import com.example.mdp_frontend.domain.model.Listing
import com.example.mdp_frontend.domain.model.ListingState
import com.example.mdp_frontend.domain.model.Response
import com.example.mdp_frontend.domain.model.User
import kotlinx.coroutines.flow.Flow

typealias Listings = List<Listing>
typealias ListingsResponse = Response<Listings>
typealias AddListingResponse = Response<Boolean>
typealias ListingActionResponse = Response<String>

interface ListingRepository {
    fun getListingsFromFirestore(
        state: ListingState? = null,
        limit: Long? = null,
    ): Flow<ListingsResponse>

    fun getListingsFromFirestore(
        category: String,
        limit: Long? = null,
    ): Flow<ListingsResponse>

    suspend fun addListingToFirestore(listing: Listing): AddListingResponse

    suspend fun getListingById(id: String): Listing

    suspend fun callBackTheListing(id: String, user: User): ListingActionResponse

    suspend fun acceptCallBack(id: String): ListingActionResponse

    suspend fun rejectCallBack(id: String): ListingActionResponse

    suspend fun finishListing(id: String): ListingActionResponse
}