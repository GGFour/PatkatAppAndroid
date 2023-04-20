package com.example.mdp_frontend.domain.use_case.listing

import com.example.mdp_frontend.domain.model.ListingState
import com.example.mdp_frontend.domain.repository.ListingRepository

class GetListings(
    private val repo: ListingRepository,
) {
    operator fun invoke(
        state: ListingState? = null,
        limit: Long? = null,
    ) = repo.getListingsFromFirestore(state,limit)

    operator fun invoke(
        category: String,
        limit: Long? = null,
    ) = repo.getListingsFromFirestore(category, limit)
}