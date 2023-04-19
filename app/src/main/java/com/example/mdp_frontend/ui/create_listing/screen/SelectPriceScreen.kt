package com.example.mdp_frontend.ui.create_listing.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.EuroSymbol
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun SelectPriceScreen(
    title: String,
    value: String,
    onValueChanged: (String) -> Unit,
    onNextPressed: () -> Unit,
    onCancelPressed: () -> Unit,
) {
    ListingScreenContainer(onNextPressed = onNextPressed, onCancelPressed = onCancelPressed) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            OutlinedTextField(
                value = value,
                onValueChange = onValueChanged,
                label = { Text(title) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Number,
                ),
                textStyle = MaterialTheme.typography.headlineLarge,
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.EuroSymbol,
                        contentDescription = null
                    )
                },
            )
        }
    }
}
