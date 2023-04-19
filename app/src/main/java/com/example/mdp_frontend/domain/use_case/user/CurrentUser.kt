package com.example.mdp_frontend.domain.use_case.user

import com.example.mdp_frontend.domain.repository.AuthRepository

class CurrentUser(
    private val repo: AuthRepository
) {
    operator fun invoke() = repo.currentUser
}