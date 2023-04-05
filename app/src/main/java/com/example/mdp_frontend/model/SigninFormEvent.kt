package com.example.mdp_frontend.model

sealed class SigninFormEvent {
    data class NameOrEmailChanged(val nameOrEmail: String): SigninFormEvent()
    data class PasswordChanged(val password: String): SigninFormEvent()
    object Signin: SigninFormEvent()
}
