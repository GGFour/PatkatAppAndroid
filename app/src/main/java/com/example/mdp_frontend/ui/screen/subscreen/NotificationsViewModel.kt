package com.example.mdp_frontend.ui.screen.subscreen

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mdp_frontend.domain.model.ListingState
import com.example.mdp_frontend.domain.model.Response
import com.example.mdp_frontend.domain.repository.ListingActionResponse
import com.example.mdp_frontend.domain.repository.ListingRepository
import com.example.mdp_frontend.domain.use_case.listing.ListingUseCases
import com.example.mdp_frontend.model.RequestNotificationBoxItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationsViewModel @Inject constructor(
    private val listingUseCases: ListingUseCases
): ViewModel() {
    private var _actionChanel = MutableStateFlow<ListingActionResponse?>(null)
    val actionChannel: StateFlow<ListingActionResponse?> = _actionChanel
    var notifications = mutableListOf<RequestNotificationBoxItem>()

    init {
        getNotifications()
    }

    fun getNotifications() = viewModelScope.launch {
        _actionChanel.value = null
        val listingsResponse = listingUseCases.getListings(state = ListingState.Noticed).first()
        if (listingsResponse is Response.Success) {
            notifications.clear()
            val listings = listingsResponse.data
            notifications.addAll(
                listings.map { listing ->
                    RequestNotificationBoxItem(
                        listingId = listing.id,
                        taskerName = listing.publisher?.name ?: "",
                        taskerRating = 4.5f,
                        requestTime = "",
                        serviceName = listing.title
                    )
                }
            )
        }
    }

    fun acceptRequest(notification: RequestNotificationBoxItem) {
        // call repository function to accept request
        viewModelScope.launch {
            _actionChanel.value = listingUseCases.acceptCallBack(notification.listingId)
            // handle response
        }
        getNotifications()
    }

    fun declineRequest(notification: RequestNotificationBoxItem) {
        // call repository function to decline request
        viewModelScope.launch {
            _actionChanel.value = listingUseCases.rejectCallBack(notification.listingId)
            // handle response
        }
        getNotifications()
    }
}