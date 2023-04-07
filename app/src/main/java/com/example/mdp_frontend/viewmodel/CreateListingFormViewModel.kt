package com.example.mdp_frontend.viewmodel

import androidx.lifecycle.ViewModel
import com.example.mdp_frontend.domain.model.Listing
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CreateListingFormViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(Listing())
    val uiState: StateFlow<Listing> = _uiState.asStateFlow()

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
            Listing()
        }
    }
}