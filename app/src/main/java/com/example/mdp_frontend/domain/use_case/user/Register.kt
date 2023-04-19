package com.example.mdp_frontend.domain.use_case.user

import com.example.mdp_frontend.domain.repository.AuthRepository

class Register(
    private val repo: AuthRepository,
) {
    suspend operator fun invoke(name: String, email: String, password: String) =
        repo.register(name, email, password)
}