package com.example.mdp_frontend.ui.authentication

import android.content.ContentValues
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mdp_frontend.R
import com.example.mdp_frontend.domain.model.Resource
import com.example.mdp_frontend.model.RegistrationFormEvent
import com.example.mdp_frontend.ui.authentication.components.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


@Composable
fun RegisterScreen(
    onNavTextBtnClicked: () -> Unit,
    onRegisterSuccess: () -> Unit,
    fViewModel : AuthViewModel?,
    viewModel: RegistrationViewModel = viewModel(),

    ) {
    val state = viewModel.state
    val registerFlow = fViewModel?.registerFlow?.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(
                color = Color.Transparent,
            )
    ) {
        val context = LocalContext.current
        /* LaunchedEffect(key1 = context) {
            viewModel.validationEvent.collect { event ->
                when (event) {
                    is RegistrationViewModel.ValidationEvent.Success -> {
                        Toast.makeText(
                            context, "Register Success", Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        } */
        Box(
            modifier = Modifier.align(Alignment.Center),
        ) {

            Image(
                painter = painterResource(id = R.drawable.user_reg),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .height(180.dp)
                    .fillMaxWidth(),
            )
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(modifier = Modifier.height(30.dp))
                Text(
                    text = "Create An Account",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top = 130.dp)
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.primary,
                )

                Spacer(modifier = Modifier.height(8.dp))
                AuthNameInput(
                    value = state.name,
                    onValueChange = { viewModel.onEvent(RegistrationFormEvent.NameChanged(it)) },
                    validationResult = state.nameValidationResult,
                )

                Spacer(modifier = Modifier.padding(3.dp))
                AuthEmailInput(
                    value = state.email,
                    onValueChange = { viewModel.onEvent(RegistrationFormEvent.EmailChanged(it)) },
                    validationResult = state.emailValidationResult,
                )

                Spacer(modifier = Modifier.padding(3.dp))
                AuthPasswordInput(
                    value = state.password,
                    onValueChange = { viewModel.onEvent(RegistrationFormEvent.PasswordChanged(it)) },
                    validationResult = state.passwordValidationResult
                )

                Spacer(modifier = Modifier.padding(3.dp))
                AuthPasswordConfirmInput(
                    value = state.repeatedPassword,
                    onValueChange = {
                        viewModel.onEvent(
                            RegistrationFormEvent.RepeatedPasswordChanged(
                                it
                            )
                        )
                    },
                    validationResult = state.repeatedPasswordValidationResult,
                )

                Spacer(modifier = Modifier.padding(10.dp))
                AuthGradientButton(
                    text = "Create An Account",
                    onClick = {
                        viewModel.onEvent(RegistrationFormEvent.Register)
                        fViewModel?.register(
                            email = state.email,
                            password = state.password,
                            name = state.name)
                    },
                    /*onClick = {
                        viewModel.onEvent(RegistrationFormEvent.Register)
                        if (viewModel.state.isDataValid) {
                            fViewModel.registerUser(state.email, state.password)
                        }
                    }
                    */

                )

                Spacer(modifier = Modifier.padding(10.dp))
                AuthTextButton(
                    text = "Sign In",
                    onClick = onNavTextBtnClicked,
                )

                Spacer(modifier = Modifier.padding(40.dp))
            }
        }

        registerFlow?.value?.let {
            when (it) {
                is Resource.Error -> {
                    val context = LocalContext.current
                    Toast.makeText(context, it.exception.message, Toast.LENGTH_LONG).show()
                }
                Resource.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(50.dp)
                            .align(Alignment.Center),
                    )
                }
                is Resource.Success -> {
                    LaunchedEffect(Unit){
                        onRegisterSuccess()

                        // Add code here to store user data in Firestore
                        val user = hashMapOf(
                            "name" to state.name,
                            "email" to state.email
                        )

                        val db = Firebase.firestore
                        db.collection("users")
                            .add(user)
                            .addOnSuccessListener { documentReference ->
                                Log.d(ContentValues.TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                            }
                            .addOnFailureListener { e ->
                                Log.w(ContentValues.TAG, "Error adding document", e)
                            }
                    }
                }
            }
        }
    }
}
