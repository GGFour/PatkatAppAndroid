package com.example.mdp_frontend.viewmodel

import androidx.lifecycle.ViewModel
import com.example.mdp_frontend.model.ListingUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CreateListingFormViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(ListingUiState())
    val uiState: StateFlow<ListingUiState> = _uiState.asStateFlow()

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
            currentState.copy(priceStr = price, price = price.toIntOrNull() ?: 0)
        }
    }

    fun updateCoordinates(lat: Float, long: Float) {
        _uiState.update { currentState ->
            currentState.copy(
                latitude = lat,
                longitude = long,
            )
        }
    }

    fun reset() {
        _uiState.update { currentState ->
            ListingUiState()
        }
    }
}