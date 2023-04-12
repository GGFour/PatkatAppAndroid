package com.example.mdp_frontend.domain

import android.content.Context
import com.example.mdp_frontend.data.repository.ListingRepositoryImpl
import com.example.mdp_frontend.domain.repository.ListingRepository
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


interface AppContainer {
    val listingRepository: ListingRepository
}

class AppContainerImpl(private val applicationContext: Context): AppContainer {
    override val listingRepository: ListingRepository by lazy {
        ListingRepositoryImpl(Firebase.firestore.collection("listings"))
    }
}