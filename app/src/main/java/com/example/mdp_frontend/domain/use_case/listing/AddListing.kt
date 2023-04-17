package com.example.mdp_frontend.domain.use_case.listing

import com.example.mdp_frontend.domain.model.Listing
import com.example.mdp_frontend.domain.repository.ListingRepository

class AddListing (
    private val repo: ListingRepository
        ) {
    suspend operator fun invoke(
        listing: Listing,
    ) = repo.addListingToFirestore(listing)
}