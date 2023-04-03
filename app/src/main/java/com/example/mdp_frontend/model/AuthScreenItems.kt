package com.example.mdp_frontend.model

sealed class AuthScreenItems(val route: String) {
    object Register : AuthScreenItems("register")
    object Login : AuthScreenItems("login")
    object Splash : AuthScreenItems("splash")
}
