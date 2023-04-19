package com.example.mdp_frontend.ui.screen.subscreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mdp_frontend.domain.model.Listing
import com.example.mdp_frontend.domain.model.Response
import com.example.mdp_frontend.domain.model.User
import com.example.mdp_frontend.domain.repository.ListingActionResponse
import com.example.mdp_frontend.domain.use_case.listing.ListingUseCases
import com.example.mdp_frontend.domain.use_case.user.UserUseCases
import com.google.firebase.firestore.FirebaseFirestoreException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListingDetailViewModel @Inject constructor(
    private val listingUseCases: ListingUseCases,
    private val userUseCases: UserUseCases,
) : ViewModel() {
    var listingResponse by mutableStateOf<Response<Listing>>(Response.Loading)

    private val _reactionFlow = MutableStateFlow<ListingActionResponse?>(null)
    val reactionFlow: StateFlow<ListingActionResponse?> = _reactionFlow

    var isOwner by mutableStateOf(false)
    var isAssignee by mutableStateOf(false)

    fun loadListing(listingId: String?) {
        viewModelScope.launch {
            listingResponse = try {
                val listing = listingUseCases.getListingById(listingId)
                if (listing != null) {
                    isOwner = listing.publisher?.uid == userUseCases.currentUser()?.uid
                    isAssignee = listing.assignee?.uid == userUseCases.currentUser()?.uid
                    Response.Success(listing)
                } else {
                    Response.Failure(
                        FirebaseFirestoreException(
                            "Failed to retrieve listing",
                            FirebaseFirestoreException.Code.UNKNOWN
                        )
                    )
                }
            } catch (e: FirebaseFirestoreException) {
                Response.Failure(e)
            }
        }
    }

    fun reactOnListing(listing: Listing) = viewModelScope.launch {
        val user = User(
            uid = userUseCases.currentUser()?.uid ?: "",
            name = userUseCases.currentUser()?.displayName ?: "",
            imageUrl = userUseCases.currentUser()?.photoUrl.toString(),
        )
        _reactionFlow.value =
            listingUseCases.reactOnListing(listing.id, user)
    }

    fun acceptRequest(listing: Listing) = viewModelScope.launch {
        _reactionFlow.value =
            listingUseCases.acceptCallBack(listing.id)
    }

    fun rejectRequest(listing: Listing) = viewModelScope.launch {
        _reactionFlow.value =
            listingUseCases.rejectCallBack(listing.id)
    }

    fun finishListing(listing: Listing) = viewModelScope.launch {
        _reactionFlow.value =
            listingUseCases.finishListing(listing.id)
    }
}