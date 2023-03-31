package com.example.mdp_frontend.model

data class RegistrationFormState(
    val name: String = "",
    val nameValidationResult: ValidationResult = ValidationResult(),
    val email: String = "",
    val emailValidationResult: ValidationResult = ValidationResult(),
    val password: String = "",
    val passwordValidationResult: ValidationResult = ValidationResult(),
    val repeatedPassword: String = "",
    val repeatedPasswordValidationResult: ValidationResult = ValidationResult(),
    val isDataValid: Boolean = false,
)
