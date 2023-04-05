package com.example.mdp_frontend.model

sealed class RegistrationFormEvent {
    data class NameChanged(val name: String): RegistrationFormEvent()
    data class EmailChanged(val email: String): RegistrationFormEvent()
    data class PasswordChanged(val password: String): RegistrationFormEvent()
    data class RepeatedPasswordChanged(val repeatedPassword: String): RegistrationFormEvent()
    object Register: RegistrationFormEvent()
}
