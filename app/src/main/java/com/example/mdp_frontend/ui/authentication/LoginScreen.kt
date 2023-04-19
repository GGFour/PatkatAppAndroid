@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.mdp_frontend.ui.authentication

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
import com.example.mdp_frontend.model.SigninFormEvent
import com.example.mdp_frontend.ui.authentication.components.AuthEmailInput
import com.example.mdp_frontend.ui.authentication.components.AuthGradientButton
import com.example.mdp_frontend.ui.authentication.components.AuthPasswordInput
import com.example.mdp_frontend.ui.authentication.components.AuthTextButton

@Composable
fun LoginScreen(
    onNavTextBtnClicked: () -> Unit,
    onLoginSuccess: () -> Unit,
    viewModel: SignInViewModel = viewModel(),
    fViewModel : AuthViewModel?

) {
    val state = viewModel.state
    val loginFlow = fViewModel?.loginFlow?.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(
                color = Color.Transparent,
            )
    ) {
        Box(
            modifier = Modifier.align(Alignment.Center),
        ) {
            Image(
                painter = painterResource(id = R.drawable.user_sign_in),
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
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(50.dp))
                Text(
                    text = "Sign In",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top = 130.dp)
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.primary,
                )

                Spacer(modifier = Modifier.height(8.dp))
                AuthEmailInput(
                    value = state.nameOrEmail,
                    onValueChange = { viewModel.onEvent(SigninFormEvent.NameOrEmailChanged(it)) },
                    validationResult = state.nameOrEmailValidationResult,
                )

                Spacer(modifier = Modifier.padding(3.dp))
                AuthPasswordInput(
                    value = state.password,
                    onValueChange = { viewModel.onEvent(SigninFormEvent.PasswordChanged(it)) },
                    validationResult = state.passwordValidationResult,
                )

                Spacer(modifier = Modifier.padding(10.dp))
                AuthGradientButton(
                    text = "Login",
                    onClick = {
                        viewModel.onEvent(SigninFormEvent.Signin)
                        fViewModel?.login(state.nameOrEmail, state.password)
                    },
                )

                Spacer(modifier = Modifier.padding(10.dp))
                AuthTextButton(
                    text = "CreateAnAccount",
                    onClick = onNavTextBtnClicked,
                )

                Spacer(modifier = Modifier.padding(30.dp))
            }
        }
        loginFlow?.value?.let {
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
                    LaunchedEffect(Unit){onLoginSuccess()}
                }
            }
        }
    }
}
