package com.example.mdp_frontend.domain.use_case.user

data class UserUseCases(
    val currentUser: CurrentUser,
    val login: Login,
    val register: Register,
    val logout: Logout,
)
