package com.example.mdp_frontend.data.repository

import com.example.mdp_frontend.domain.model.Listing
import com.example.mdp_frontend.domain.model.Response
import com.example.mdp_frontend.domain.repository.AddListingResponse
import com.example.mdp_frontend.domain.repository.ListingRepository
import com.example.mdp_frontend.domain.repository.ListingsResponse
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ListingRepositoryImpl @Inject constructor(
    private val listingRef: CollectionReference
    ): ListingRepository {
//    companion object {
//        @Volatile
//        private var instance: ListingRepositoryImpl? = null
//
//        fun getInstance(
//            listingRef: CollectionReference
//        ) =
//            instance ?: synchronized(this) {
//                instance ?: ListingRepositoryImpl(listingRef).also { instance = it }
//            }
//    }

    override fun getListingsFromFirestore(): Flow<ListingsResponse> = callbackFlow {
        val snapshotListener = listingRef.orderBy("title").addSnapshotListener { snapshot, e ->
            val listingsResponse = if (snapshot != null) {
                val listings = snapshot.toObjects(Listing::class.java)
                Response.Success(listings)
            } else {
                Response.Failure(e)
            } as ListingsResponse
            trySend(listingsResponse)
        }
        awaitClose {
            snapshotListener.remove()
        }
    }

    override suspend fun addListingToFirestore(listing: Listing): AddListingResponse {
        return try {
            val id = listingRef.document().id
            val listingWithId = listing.copy(id = id)
            listingRef.document(id).set(listingWithId)
            Response.Success(true)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }
}