package com.example.mdp_frontend.domain.use_case.listing

import com.example.mdp_frontend.domain.model.User
import com.example.mdp_frontend.domain.repository.ListingRepository

class ReactOnListing(
    val repo: ListingRepository,
) {
    suspend operator fun invoke(id: String, user: User) = repo.callBackTheListing(id, user)
}