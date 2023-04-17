package com.example.mdp_frontend.ui.all_categories

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mdp_frontend.domain.model.Response
import com.example.mdp_frontend.domain.repository.CategoriesResponse
import com.example.mdp_frontend.domain.use_case.category.CategoriesUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllCategoriesViewModel @Inject constructor(
    private val useCases: CategoriesUseCases
): ViewModel() {
    var categoriesResponse by mutableStateOf<CategoriesResponse>(Response.Loading)
        private set

    init {
        getCategories()
    }

    private fun getCategories() = viewModelScope.launch {
        useCases.getCategories().collect {response ->
            categoriesResponse = response
        }
    }

}