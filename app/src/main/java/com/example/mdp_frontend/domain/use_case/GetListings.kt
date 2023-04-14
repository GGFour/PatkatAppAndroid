package com.example.mdp_frontend.domain.use_case

import android.content.Context
import com.example.mdp_frontend.domain.model.Listing
import com.example.mdp_frontend.model.ListingCardItem
import com.example.mdp_frontend.ui.screen.subscreen.getAddressFromLocation
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

fun getListings(
    context: Context,
    limit: Long? = null,
    onListingsRetrieved: (List<ListingCardItem>) -> Unit
) {
    val listings = mutableListOf<ListingCardItem>()
    var query = Firebase.firestore.collection("listings")
        .orderBy("publishedDate", Query.Direction.DESCENDING)
    if (limit != null) {
        query = query.limit(limit)
    }
    query.get()
        .addOnSuccessListener { snapshot ->
            snapshot.documents.mapNotNullTo(listings) { document ->
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
                        date = listing.publishedDate
                    )
                }
            }
            onListingsRetrieved(listings)
        }
}