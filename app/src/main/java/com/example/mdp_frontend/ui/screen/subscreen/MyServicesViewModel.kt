package com.example.mdp_frontend.ui.screen.subscreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mdp_frontend.domain.model.Response
import com.example.mdp_frontend.domain.repository.ListingRepository
import com.example.mdp_frontend.domain.use_case.user.UserUseCases
import com.example.mdp_frontend.model.TaskServiceRowItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyServicesViewModel @Inject constructor(
    private val userUseCases: UserUseCases,
    private val listingRepository: ListingRepository
) : ViewModel() {
    var servicesResponse by mutableStateOf<Response<List<TaskServiceRowItem>>>(Response.Loading)

    init {
        loadServices()
    }

    fun loadServices() {
        viewModelScope.launch {
            servicesResponse = try {
                val currentUser = userUseCases.currentUser()
                val listings = listingRepository.getListingsFromFirestore().first()
                if (listings is Response.Success) {
                    val userServices = listings.data.filter { it.publisher?.uid == currentUser?.uid }
                    val services = userServices.map { listing ->
                        TaskServiceRowItem(
                            name = listing.title,
                            status = listing.state,
                            price = listing.price.toInt()
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