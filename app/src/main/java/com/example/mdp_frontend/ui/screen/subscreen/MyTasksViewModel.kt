package com.example.mdp_frontend.ui.screen.subscreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mdp_frontend.domain.model.Response
import com.example.mdp_frontend.domain.use_case.listing.ListingUseCases
import com.example.mdp_frontend.domain.use_case.user.UserUseCases
import com.example.mdp_frontend.model.TaskServiceRowItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyTasksViewModel @Inject constructor(
    private val userUseCases: UserUseCases,
    private val listingUseCases: ListingUseCases,
    ): ViewModel() {
    var servicesResponse by mutableStateOf<Response<List<TaskServiceRowItem>>>(Response.Loading)

    init {
        loadServices()
    }

    fun loadServices() {
        viewModelScope.launch {
            servicesResponse = try {
                val currentUser = userUseCases.currentUser()
                val listings = listingUseCases.getListings().first()
                if (listings is Response.Success) {
                    val userServices = listings.data.filter { it.assignee?.uid == currentUser?.uid }
                    val services = userServices.map { listing ->
                        TaskServiceRowItem(
                            name = listing.title,
                            status = listing.state,
                            price = listing.price.toInt(),
                            id = listing.id
                        )
                    }
                    Response.Success(services)
                } else {
                    Response.Failure(Exception("Failed to retrieve services"))
                }
            } catch (e: Exception) {
                Response.Failure(e)
            }
        }
    }
}