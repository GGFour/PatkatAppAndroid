package com.example.mdp_frontend.domain.repository

import com.example.mdp_frontend.domain.model.Category
import com.example.mdp_frontend.domain.model.Response
import kotlinx.coroutines.flow.Flow

typealias Categories = List<Category>
typealias CategoriesResponse = Response<Categories>

interface CategoryRepository {
    fun getCategories(): Flow<CategoriesResponse>
}