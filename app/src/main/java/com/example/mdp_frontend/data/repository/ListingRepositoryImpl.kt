package com.example.mdp_frontend.data.repository

import com.example.mdp_frontend.depin.ListingCollectionReference
import com.example.mdp_frontend.domain.model.Listing
import com.example.mdp_frontend.domain.model.ListingState
import com.example.mdp_frontend.domain.model.Response
import com.example.mdp_frontend.domain.model.User
import com.example.mdp_frontend.domain.repository.AddListingResponse
import com.example.mdp_frontend.domain.repository.ListingActionResponse
import com.example.mdp_frontend.domain.repository.ListingRepository
import com.example.mdp_frontend.domain.repository.ListingsResponse
import com.google.firebase.Timestamp
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

/*
* It is really awful to put ui messages to data layer, but i am lazy to write xtra code*/
@Singleton
class ListingRepositoryImpl @Inject constructor(
    @ListingCollectionReference private val listingRef: CollectionReference
) : ListingRepository {

    override fun getListingsFromFirestore(
        limit: Long?,
    ): Flow<ListingsResponse> = callbackFlow {
        var query = listingRef.orderBy("publishedDate", Query.Direction.DESCENDING)
        if (limit != null) {
            query = query.limit(limit)
        }

        val snapshotListener = query.addSnapshotListener { snapshot, e ->
            val listingsResponse = if (snapshot != null) {
                val listings = snapshot.toObjects(Listing::class.java)
                Response.Success(listings)
            } else {
                e?.let { Response.Failure(it) }
            } as ListingsResponse
            trySend(listingsResponse)
        }
        awaitClose {
            snapshotListener.remove()
        }
    }

    override fun getListingsFromFirestore(
        category: String,
        limit: Long?,
    ): Flow<ListingsResponse> = callbackFlow {
        var query = listingRef.orderBy("publishedDate", Query.Direction.DESCENDING)
            .where(Filter.equalTo("category", category))
        if (limit != null) {
            query = query.limit(limit)
        }

        val snapshotListener = query.addSnapshotListener { snapshot, e ->
            val listingsResponse = if (snapshot != null) {
                val listings = snapshot.toObjects(Listing::class.java)
                Response.Success(listings)
            } else {
                e?.let { Response.Failure(it) }
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
            val listingWithId = listing.copy(
                id = id,
                publishedDate = Timestamp.now(),
                pictureUri = null,
            )
            listingRef.document(id).set(listingWithId).await()
            Response.Success(true)
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    override suspend fun getListingById(id: String): Listing {
        val document = listingRef.document(id).get().await()
        return document.toObject(Listing::class.java)!!
    }

    override suspend fun callBackTheListing(id: String, user: User): ListingActionResponse {
        return try {
            val current = listingRef.document(id).get().await().toObject(Listing::class.java)
            if (current == null || current.state != ListingState.Active) {
                throw Exception("You can't react to this listing!")
            }
            val updated = current.copy(
                state = ListingState.Noticed,
                assignee = user,
            )
            listingRef.document(id).set(updated).await()
            Response.Success("Callback was sent to publisher")
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    override suspend fun acceptCallBack(id: String): ListingActionResponse {
        return try {
            val current = listingRef.document(id).get().await().toObject(Listing::class.java)
            if (current == null || current.state != ListingState.Noticed) {
                throw Exception("You can't accept the worker to this listing!")
            }
            listingRef.document(id).update("state", ListingState.WIP).await()
            Response.Success("Worker has been accepted")
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    override suspend fun rejectCallBack(id: String): ListingActionResponse {
        return try {
            val current = listingRef.document(id).get().await().toObject(Listing::class.java)
            if (current == null || current.state != ListingState.Noticed) {
                throw Exception("You can't reject the worker to this listing!")
            }
            listingRef.document(id).update("state", ListingState.Active).await()
            Response.Success("Worker was rejected")
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    override suspend fun finishListing(id: String): ListingActionResponse {
        return try {
            val current = listingRef.document(id).get().await().toObject(Listing::class.java)
            if (current == null || current.state != ListingState.WIP) {
                throw Exception("You can't finish the work on this listing!")
            }
            listingRef.document(id).update("state", ListingState.Finished).await()
            Response.Success("Contract was finished")
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }
}