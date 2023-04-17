package com.example.mdp_frontend.domain.use_case.category

import com.example.mdp_frontend.domain.repository.CategoryRepository

class GetCategories(
    private val repo: CategoryRepository
) {
    operator fun invoke() = repo.getCategories()
}