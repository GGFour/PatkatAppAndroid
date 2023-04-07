package com.example.mdp_frontend.ui.authentication.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.mdp_frontend.model.ValidationResult

@Composable
fun AuthNameInput(
    value: String,
    onValueChange: (String) -> Unit,
    validationResult: ValidationResult,
) {
    CommonInputField(
        value = value,
        validationResult = validationResult,
        onValueChange = onValueChange,
        title = "Name",
        keyboardType = KeyboardType.Text,
    )
}

@Composable
fun AuthEmailInput(
    value: String,
    onValueChange: (String) -> Unit,
    validationResult: ValidationResult,
) {
    CommonInputField(
        value = value,
        onValueChange = onValueChange,
        validationResult = validationResult,
        title = "Email Address",
        keyboardType = KeyboardType.Email,
    )
}

@Composable
fun AuthPasswordInput(
    value: String,
    onValueChange: (String) -> Unit,
    validationResult: ValidationResult,
) {
    var passwordHidden by rememberSaveable { mutableStateOf(true) }
    CommonInputField(
        value = value,
        onValueChange = onValueChange,
        validationResult = validationResult,
        title = "Enter Password",
        keyboardType = KeyboardType.Password,
        trailingIcon = {
            IconButton(onClick = { passwordHidden = !passwordHidden }) {
                val visibilityIcon =
                    if (passwordHidden) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                val description = if (passwordHidden) "Show password" else "Hide password"
                Icon(imageVector = visibilityIcon, contentDescription = description)
            }
        },
        visualTransformation = if (passwordHidden) PasswordVisualTransformation() else VisualTransformation.None,
    )
}

@Composable
fun AuthPasswordConfirmInput(
    value: String,
    onValueChange: (String) -> Unit,
    validationResult: ValidationResult,
) {
    CommonInputField(
        value = value,
        onValueChange = onValueChange,
        validationResult = validationResult,
        title = "Repeat the Password",
        keyboardType = KeyboardType.Password,
        visualTransformation = PasswordVisualTransformation(),
    )
}

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun CommonInputField(
    value: String,
    onValueChange: (String) -> Unit,
    validationResult: ValidationResult,
    title: String,
    keyboardType: KeyboardType,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    trailingIcon: @Composable (() -> Unit)? = null,
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        isError = !validationResult.successful,
        shape = RoundedCornerShape(topEnd = 12.dp, bottomStart = 12.dp),
        label = {
            Text(
                title,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.labelMedium,
            )
        },
        placeholder = { Text(text = title) },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Next, keyboardType = keyboardType
        ),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.primary
        ),
        singleLine = true,
        modifier = Modifier.fillMaxWidth(0.8f),
        keyboardActions = KeyboardActions(onDone = {
            keyboardController?.hide()
        }),
        visualTransformation = visualTransformation,
        trailingIcon = trailingIcon,
    )
    if (!validationResult.successful) {
        Text(
            text = validationResult.errorMessage,
            color = MaterialTheme.colorScheme.error,
        )
    }
}
