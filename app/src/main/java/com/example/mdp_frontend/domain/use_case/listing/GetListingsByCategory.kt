package com.example.mdp_frontend.domain.use_case.listing


import com.example.mdp_frontend.domain.repository.ListingRepository
import javax.inject.Inject

data class GetListingsByCategory @Inject constructor(
    private val repo: ListingRepository
) {
    operator fun invoke(
        category: String,
        limit: Long
    ) = repo.getListingsFromFirestore(category, limit)
}