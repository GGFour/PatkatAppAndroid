package com.example.mdp_frontend.ui.screen.subscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    //retrieving listing info to be rendered in notification box
    private fun getNotifications() = viewModelScope.launch {
        val listingsResponse = listingRepository.getListingsFromFirestore().first()
        if (listingsResponse is Response.Success) {
            val listings = listingsResponse.data
            val notifications = listings.map { listing ->
                RequestNotificationBoxItem(
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
}