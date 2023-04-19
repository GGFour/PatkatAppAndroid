package com.example.mdp_frontend.ui.create_listing

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mdp_frontend.domain.model.Listing
import com.example.mdp_frontend.domain.model.Response
import com.example.mdp_frontend.domain.model.User
import com.example.mdp_frontend.domain.repository.AddListingResponse
import com.example.mdp_frontend.domain.repository.CategoriesResponse
import com.example.mdp_frontend.domain.use_case.category.CategoriesUseCases
import com.example.mdp_frontend.domain.use_case.listing.ListingUseCases
import com.example.mdp_frontend.domain.use_case.user.UserUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateListingFormViewModel @Inject constructor(
    private val listingUseCases: ListingUseCases,
    private val categoriesUseCases: CategoriesUseCases,
    private val userUseCases: UserUseCases,
) : ViewModel() {
    private val _uiState = MutableStateFlow(Listing())
    val uiState: StateFlow<Listing> = _uiState.asStateFlow()
    var addListingResponse by mutableStateOf<AddListingResponse>(Response.Success(false))
        private set

    var categoriesResponse by mutableStateOf<CategoriesResponse>(Response.Loading)
        private set

    init {
        getCategories()
        getCurrentUser()
    }

    private fun getCategories() = viewModelScope.launch {
        categoriesUseCases.getCategories().collect { response ->
            categoriesResponse = response
        }
    }

    private fun getCurrentUser() {
        val user = userUseCases.currentUser()
        _uiState.update { currentState ->
            currentState.copy(
                publisher = User(
                    name = user?.displayName ?: "",
                    imageUrl = user?.photoUrl.toString() ?: "",
                    uid = user?.uid ?: "",
                )
            )
        }
    }

    fun updateTitle(title: String) {
        _uiState.update { currentState ->
            currentState.copy(title = title)
        }
    }

    fun updateDescription(description: String) {
        _uiState.update { currentState ->
            currentState.copy(description = description)
        }
    }

    fun updatePrice(price: String) {
        _uiState.update { currentState ->
            currentState.copy(priceStr = price, price = price.toDoubleOrNull() ?: 0.0)
        }
    }

    fun updateLatitude(lat: Double) {
        _uiState.update { currentState ->
            currentState.copy(
                latitude = lat,
            )
        }
    }

    fun updateLongitude(long: Double) {
        _uiState.update { currentState ->
            currentState.copy(
                longitude = long,
            )
        }
    }

    fun updateImageUri(newUri: Uri?) {
        _uiState.update { currentState ->
            currentState.copy(
                pictureUri = newUri.toString(),
            )
        }
    }

    fun updateCategory(newCategory: String?) {
        _uiState.update { currentState ->
            currentState.copy(
                category = newCategory,
            )
        }
    }

    fun addListing() = viewModelScope.launch {
        addListingResponse = Response.Loading
        getCurrentUser()
        addListingResponse = listingUseCases.addListing(uiState.value)
    }

    fun reset() {
        _uiState.update { currentState ->
            Listing()
        }
    }
}