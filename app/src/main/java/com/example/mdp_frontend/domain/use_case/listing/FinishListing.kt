package com.example.mdp_frontend.domain.use_case.listing

import com.example.mdp_frontend.domain.repository.ListingRepository

class FinishListing (
    private val repo: ListingRepository
) {
    suspend operator fun invoke(id: String) = repo.finishListing(id)
}