package com.example.mdp_frontend.domain.use_case.listing

data class ListingUseCases (
    val addListing: AddListing,
    val getListingById: GetListingById,
    val getListingsByCategory: GetListingsByCategory,
    val getListings: GetListings,
    val reactOnListing: ReactOnListing,
    val acceptCallBack: AcceptCallBack,
    val rejectCallBack: RejectCallBack,
    val finishListing: FinishListing,
)
