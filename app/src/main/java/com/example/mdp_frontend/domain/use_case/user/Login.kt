package com.example.mdp_frontend.domain.use_case.user

import com.example.mdp_frontend.domain.repository.AuthRepository

class Login(
    private val repo: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String) = repo.login(email, password)
}