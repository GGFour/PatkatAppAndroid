package com.example.mdp_frontend.model

data class SigninFormState(
    val nameOrEmail: String = "",
    val nameOrEmailValidationResult: ValidationResult = ValidationResult(),
    val password: String = "",
    val passwordValidationResult: ValidationResult = ValidationResult(),
    val isDataValid: Boolean = false,
)
