package com.example.mdp_frontend.depin


import com.example.mdp_frontend.data.repository.ListingRepositoryImpl
import com.example.mdp_frontend.domain.repository.ListingRepository
import com.example.mdp_frontend.domain.use_case.AddListing
import com.example.mdp_frontend.domain.use_case.UseCases
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideListingRef() = Firebase.firestore.collection("listings")

    @Provides
    fun provideBooksRepository(
        listingRef: CollectionReference
    ): ListingRepository = ListingRepositoryImpl(listingRef)

    @Provides
    fun provideUseCases(
        repo: ListingRepository
    ) = UseCases(
        addListing = AddListing(repo),
    )
}
