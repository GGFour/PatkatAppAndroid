package com.example.mdp_frontend.domain.use_case.listing

import com.example.mdp_frontend.domain.model.Listing
import com.example.mdp_frontend.domain.repository.ListingRepository
import javax.inject.Inject

data class GetListingById @Inject constructor(
    private val listingRepository: ListingRepository
) {

    suspend operator fun invoke(
        listingId: String?): Listing? {
        return listingId?.let { listingRepository.getListingById(it) }
    }
}