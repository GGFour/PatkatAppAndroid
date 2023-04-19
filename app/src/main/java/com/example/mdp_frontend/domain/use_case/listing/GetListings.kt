package com.example.mdp_frontend.domain.use_case.listing

import com.example.mdp_frontend.domain.repository.ListingRepository

class GetListings(
    private val repo: ListingRepository,
) {
    operator fun invoke(
        limit: Long?,
    ) = repo.getListingsFromFirestore(limit)
    operator fun invoke(
        category: String,
        limit: Long?,
    ) = repo.getListingsFromFirestore(category, limit)
}