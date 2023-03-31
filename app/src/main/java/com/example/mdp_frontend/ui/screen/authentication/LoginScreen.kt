package com.example.mdp_frontend.ui.screen

import android.transition.Visibility
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.mdp_frontend.R
import com.example.mdp_frontend.model.SigninFormEvent
import com.example.mdp_frontend.viewmodel.SigninViewModel
import kotlinx.coroutines.flow.collect

@Composable
fun LoginScreen(modifier: Modifier, navController: NavHostController, viewModel: SigninViewModel) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(
                color = Color.Transparent,
            )
    ) {


        Box(
            modifier = Modifier

                .align(Alignment.BottomCenter),
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
                    .verticalScroll(rememberScrollState())
                ,

                horizontalAlignment = Alignment.CenterHorizontally
            ) {


                Spacer(modifier = Modifier.height(50.dp))


                androidx.compose.material3.Text(
                    text = "Sign In",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top = 130.dp)
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.primary,
                )
                Spacer(modifier = Modifier.height(8.dp))
                SimpleOutlinedTextFieldSample(viewModel = viewModel)

                Spacer(modifier = Modifier.padding(3.dp))
                SimpleOutlinedPasswordTextField(viewModel = viewModel)

                val gradientColor = listOf(Color(0xFF484BF1), Color(0xFF673AB7))
                val cornerRadius = 16.dp


                Spacer(modifier = Modifier.padding(10.dp))

                GradientButton(
                    onClick = {
                        viewModel.onEvent(SigninFormEvent.Signin)
                    },
                    gradientColors = gradientColor,
                    cornerRadius = cornerRadius,
                    nameButton = "Login",
                    roundedCornerShape = RoundedCornerShape(topStart = 30.dp,bottomEnd = 30.dp)
                )

                Spacer(modifier = Modifier.padding(10.dp))
                androidx.compose.material3.TextButton(onClick = {

                    navController.navigate("register")
                    /*{
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }*/

                }) {
                    androidx.compose.material3.Text(
                        text = "Create An Account",
                        letterSpacing = 1.sp,
                        style = MaterialTheme.typography.labelLarge
                    )
                }

                Spacer(modifier = Modifier.padding(30.dp))

            }


        }

    }


}


//...........................................................................
@Composable
private fun GradientButton(
    onClick: () -> Unit,
    gradientColors: List<Color>,
    cornerRadius: Dp,
    nameButton: String,
    roundedCornerShape: RoundedCornerShape
) {

    androidx.compose.material3.Button(

        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 32.dp, end = 32.dp),
        onClick = onClick,

        contentPadding = PaddingValues(),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent
        ),
        shape = RoundedCornerShape(cornerRadius)
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.horizontalGradient(colors = gradientColors),
                    shape = roundedCornerShape
                )
                .clip(roundedCornerShape)

                .padding(horizontal = 16.dp, vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            androidx.compose.material3.Text(
                text = nameButton,
                fontSize = 20.sp,
                color = Color.White
            )
        }
    }
}


//email id
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SimpleOutlinedTextFieldSample(viewModel: SigninViewModel) {
    val keyboardController = LocalSoftwareKeyboardController.current
    //var text by rememberSaveable { mutableStateOf("") }
    val state = viewModel.state
    val context = LocalContext.current

    LaunchedEffect(key1 = context) {
        viewModel.signinValidationEvent.collect() { event ->
            when(event) {
                is SigninViewModel.SigninValidationEvent.Success -> {
                    Toast.makeText(
                        context,
                        "Login Success",
                        Toast.LENGTH_LONG
                    ).show()// do something
                }
            }
        }
    }

    OutlinedTextField(
        value = state.nameOrEmail,
        onValueChange = { viewModel.onEvent(SigninFormEvent.NameOrEmailChanged(it)) },
        isError = !state.nameOrEmailValidationResult.successful,
        shape = RoundedCornerShape(topEnd =12.dp, bottomStart =12.dp),
        label = {
            Text("Name or Email Address",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.labelMedium,
            ) },
        placeholder = { Text(text = "Name or Email Address") },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Next,
            keyboardType = KeyboardType.Email
        ),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.primary),
        singleLine = true,
        modifier = Modifier.fillMaxWidth(0.8f),
        keyboardActions = KeyboardActions(
            onDone = {
                keyboardController?.hide()
                // do something here
            }
        )

    )
    if (!state.nameOrEmailValidationResult.successful) {
        // Error message styling
        Text(
            text = state.nameOrEmailValidationResult.errorMessage,
            color = MaterialTheme.colorScheme.error,
        )
    }
}

//password
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SimpleOutlinedPasswordTextField(viewModel: SigninViewModel) {
    val keyboardController = LocalSoftwareKeyboardController.current
    //var password by rememberSaveable { mutableStateOf("") }
    var passwordHidden by rememberSaveable { mutableStateOf(true) }
    val state = viewModel.state

    OutlinedTextField(
        value = state.password,
        onValueChange = { viewModel.onEvent(SigninFormEvent.PasswordChanged(it)) },
        isError = !state.passwordValidationResult.successful,
        shape = RoundedCornerShape(topEnd =12.dp, bottomStart =12.dp),
        label = {
            Text("Enter Password",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.labelMedium,
            ) },
        visualTransformation =
        if (passwordHidden) PasswordVisualTransformation() else VisualTransformation.None,
        //  keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Password
        ),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.primary),
        trailingIcon = {
            IconButton(onClick = { passwordHidden = !passwordHidden }) {
                val visibilityIcon =
                    if (passwordHidden) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                // Please provide localized description for accessibility services
                val description = if (passwordHidden) "Show password" else "Hide password"
                Icon(imageVector = visibilityIcon, contentDescription = description)
            }
        },
        modifier = Modifier.fillMaxWidth(0.8f),
        keyboardActions = KeyboardActions(
            onDone = {
                keyboardController?.hide()
                // do something here
            }
        )
    )
    if (!state.passwordValidationResult.successful) {
        // Error message styling
        Text(
            text = state.passwordValidationResult.errorMessage,
            color = MaterialTheme.colorScheme.error,
        )
    }
}
