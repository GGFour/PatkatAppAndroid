package com.example.mdp_frontend.ui.screen.subscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mdp_frontend.domain.model.ListingState
import com.example.mdp_frontend.domain.model.Response
import com.example.mdp_frontend.domain.repository.ListingRepository
import com.example.mdp_frontend.model.RequestNotificationBoxItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationsViewModel @Inject constructor(
    private val listingRepository: ListingRepository
): ViewModel() {
    val notifications = MutableStateFlow<List<RequestNotificationBoxItem>>(emptyList())

    init {
        getNotifications()
    }

    private fun getNotifications() = viewModelScope.launch {
        val listingsResponse = listingRepository.getListingsFromFirestore(state = ListingState.Noticed).first()
        if (listingsResponse is Response.Success) {
            val listings = listingsResponse.data
            val notifications = listings.map { listing ->
                RequestNotificationBoxItem(
                    listingId = listing.id,
                    // taskerPicture = ,
                    taskerName = listing.publisher?.name ?: "",
                    taskerRating = 4.5f,
                    requestTime = "",
                    serviceName = listing.title
                )
            }
            this@NotificationsViewModel.notifications.value = notifications
        }
    }

    fun acceptRequest(notification: RequestNotificationBoxItem) {
        // call repository function to accept request
        viewModelScope.launch {
            val response = listingRepository.acceptCallBack(notification.listingId)
            // handle response
        }
    }

    fun declineRequest(notification: RequestNotificationBoxItem) {
        // call repository function to decline request
        viewModelScope.launch {
            val response = listingRepository.rejectCallBack(notification.listingId)
            // handle response
        }
    }
}