package com.example.mdp_frontend.viewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mdp_frontend.model.SigninFormEvent
import com.example.mdp_frontend.model.SigninFormState
import com.example.mdp_frontend.model.ValidationResult
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class SigninViewModel: ViewModel() {
    var state by mutableStateOf(SigninFormState())
    private val validationEventChannel = Channel<SigninValidationEvent>()
    val signinValidationEvent = validationEventChannel.receiveAsFlow()

    private fun validateNameOrEmail(nameOrEmail: String): ValidationResult {
        if (nameOrEmail.isEmpty()) {
            return ValidationResult(
                successful = false,
                errorMessage = "Name or email cannot be empty"
            )
        }
        return ValidationResult(
            successful = true,
        )
    }

    private fun validatePassword(password: String): ValidationResult {
        if (password.length < 8) {
            return ValidationResult(
                successful = false,
                errorMessage = "Password must be at least 8 characters"
            )
        }
        val containsLetterAndDigits = password.any{it.isDigit()} && password.any{it.isLetter()}
        if (!containsLetterAndDigits) {
            return ValidationResult(
                successful = false,
                errorMessage = "Password must contain at least one letter and one digit"
            )
        }
        return ValidationResult(
            successful = true,
        )
    }

    fun onEvent(event: SigninFormEvent) {
        when(event) {
            is SigninFormEvent.NameOrEmailChanged -> {
                state = state.copy(
                    nameOrEmail = event.nameOrEmail,
                    nameOrEmailValidationResult = validateNameOrEmail(event.nameOrEmail)
                )
            }
            is SigninFormEvent.PasswordChanged -> {
                state = state.copy(
                    password = event.password,
                    passwordValidationResult = validatePassword(event.password)
                )
            }
            is SigninFormEvent.Signin -> {
                LoginSubmitData()
            }
        }

    }
    private fun LoginSubmitData(){
        val nameOrEmailValidationResult = validateNameOrEmail(state.nameOrEmail)
        val passwordValidationResult = validatePassword(state.password)
        val hasError = listOf(
            nameOrEmailValidationResult,
            passwordValidationResult,
        ).any { !it.successful }


        if(hasError) {
            state = state.copy(
                nameOrEmailValidationResult = nameOrEmailValidationResult,
                passwordValidationResult = passwordValidationResult,
                isDataValid = !hasError,
            )
            return
        }



        }
    sealed class SigninValidationEvent{
        object Success: SigninValidationEvent()
    }

    }

