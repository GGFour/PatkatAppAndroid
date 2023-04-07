package com.example.mdp_frontend.ui.authentication

import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mdp_frontend.model.RegistrationFormEvent
import com.example.mdp_frontend.model.RegistrationFormState
import com.example.mdp_frontend.model.ValidationResult
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class RegistrationViewModel : ViewModel() {
    var state by mutableStateOf(RegistrationFormState())

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvent = validationEventChannel.receiveAsFlow()

    private fun validateName(name: String): ValidationResult {
        if (name.isEmpty()) {
            return ValidationResult(
                successful = false, errorMessage = "Name cannot be empty"
            )
        }
        return ValidationResult(
            successful = true,
        )
    }

    private fun validateEmail(email: String): ValidationResult {
        if (email.isEmpty()) {
            return ValidationResult(
                successful = false, errorMessage = "Email cannot be empty"
            )
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return ValidationResult(
                successful = false, errorMessage = "That's not a valid email"
            )
        }
        return ValidationResult(
            successful = true,
        )
    }

    private fun validatePassword(password: String): ValidationResult {
        if (password.length < 8) {
            return ValidationResult(
                successful = false, errorMessage = "Password must be at least 8 characters"
            )
        }
        val containsLetterAndDigits =
            password.any { it.isDigit() } && password.any { it.isLetter() }
        if (!containsLetterAndDigits) {
            return ValidationResult(
                successful = false,
                errorMessage = "Password needs to contain at least one letter and one digit"
            )
        }
        return ValidationResult(
            successful = true,
        )
    }

    private fun validateRepeatedPassword(repeatedPassword: String): ValidationResult {
        if (state.password != repeatedPassword) {
            return ValidationResult(
                successful = false, errorMessage = "Passwords do not match"
            )
        }
        return ValidationResult(
            successful = true,
        )
    }

    fun onEvent(event: RegistrationFormEvent) {
        when (event) {
            is RegistrationFormEvent.EmailChanged -> {
                val emailValidationResult = validateEmail(event.email)
                state = state.copy(
                    emailValidationResult = emailValidationResult,
                    email = event.email,
                )
            }
            is RegistrationFormEvent.PasswordChanged -> {
                val passwordValidationResult = validatePassword(event.password)
                state = state.copy(
                    passwordValidationResult = passwordValidationResult,
                    password = event.password,
                )
            }
            is RegistrationFormEvent.RepeatedPasswordChanged -> {
                val repeatedPasswordValidationResult =
                    validateRepeatedPassword(event.repeatedPassword)
                state = state.copy(
                    repeatedPasswordValidationResult = repeatedPasswordValidationResult,
                    repeatedPassword = event.repeatedPassword,
                )
            }
            is RegistrationFormEvent.NameChanged -> {
                val nameValidationResult = validateName(event.name)
                state = state.copy(
                    nameValidationResult = nameValidationResult,
                    name = event.name,
                )
            }

            is RegistrationFormEvent.Register -> {
                submitData()
            }
        }
    }

    private fun submitData() {
        val emailValidationResult = validateEmail(state.email)
        val passwordValidationResult = validatePassword(state.password)
        val repeatedPasswordValidationResult = validateRepeatedPassword(state.repeatedPassword)
        val nameValidationResult = validateName(state.name)
        val hasError = listOf(
            emailValidationResult,
            passwordValidationResult,
            repeatedPasswordValidationResult,
            nameValidationResult
        ).any { !it.successful }

        if (hasError) {
            state = state.copy(
                emailValidationResult = emailValidationResult,
                passwordValidationResult = passwordValidationResult,
                repeatedPasswordValidationResult = repeatedPasswordValidationResult,
                nameValidationResult = nameValidationResult,
                isDataValid = !hasError,
            )
            return
        }
        viewModelScope.launch {
            validationEventChannel.send(ValidationEvent.Success)
        }
    }

    sealed class ValidationEvent {
        object Success : ValidationEvent()
    }
}