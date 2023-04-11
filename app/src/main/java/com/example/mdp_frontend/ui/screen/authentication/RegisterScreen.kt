@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.mdp_frontend.ui.screen.authentication

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
import com.example.mdp_frontend.model.AuthScreenItems
import com.example.mdp_frontend.model.RegistrationFormEvent
import com.example.mdp_frontend.viewmodel.RegistrationViewModel


@Composable
fun RegisterScreen(viewModel: RegistrationViewModel, navController: NavController) {
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
                    .verticalScroll(rememberScrollState())
                ,

                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                //.........................Spacer
                Spacer(modifier = Modifier.height(30.dp))

                //.........................Text: title
                androidx.compose.material3.Text(
                    text = "Create An Account",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top = 130.dp)
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.primary,
                )
                Spacer(modifier = Modifier.height(8.dp))
                RegisterName(viewModel = viewModel)


                Spacer(modifier = Modifier.padding(3.dp))
                RegisterEmail(viewModel = viewModel)

                Spacer(modifier = Modifier.padding(3.dp))
                RegisterPassword(viewModel = viewModel)

                Spacer(modifier = Modifier.padding(3.dp))
                RegisterPasswordConfirm(viewModel = viewModel)


                val gradientColor = listOf(Color(0xFF484BF1), Color(0xFF673AB7))
                val cornerRadius = 16.dp

                Spacer(modifier = Modifier.padding(10.dp))

                GradientButton(
                    onClick = { viewModel.onEvent(RegistrationFormEvent.Register) },
                    gradientColors = gradientColor,
                    cornerRadius = cornerRadius,
                    nameButton = "Create An Account",
                    roundedCornerShape = RoundedCornerShape(topStart = 30.dp,bottomEnd = 30.dp),
                )

                Spacer(modifier = Modifier.padding(10.dp))
                androidx.compose.material3.TextButton(onClick = {
                    navController.navigate(AuthScreenItems.Login.route)


                }) {
                    androidx.compose.material3.Text(
                        text = "Sign In",
                        letterSpacing = 1.sp,
                        style = MaterialTheme.typography.labelLarge
                    )
                }

                Spacer(modifier = Modifier.padding(40.dp))

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
                /*.background(
                    brush = Brush.linearGradient(colors = gradientColors),
                    shape = RoundedCornerShape(cornerRadius)
                )*/
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


// *******name
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RegisterName(viewModel: RegistrationViewModel) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val state = viewModel.state
    val context = LocalContext.current
    LaunchedEffect(key1 = context) {
        viewModel.validationEvent.collect { event ->
            when(event) {
                is RegistrationViewModel.ValidationEvent.Success -> {
                    Toast.makeText(
                        context,
                        "Register Success",
                        Toast.LENGTH_LONG
                    ).show()

                }
            }

        }
    }

    OutlinedTextField(
        value = state.name,
        onValueChange = { viewModel.onEvent(RegistrationFormEvent.NameChanged(it)) },
        isError = !state.nameValidationResult.successful,
        shape = RoundedCornerShape(topEnd =12.dp, bottomStart =12.dp),
        label = {
            Text("Name",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.labelMedium,
            ) },
        placeholder = { Text(text = "Name") },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Next,
            keyboardType = KeyboardType.Text
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
    if(!state.nameValidationResult.successful){
        Text(
            text= state.nameValidationResult.errorMessage,
            color = MaterialTheme.colorScheme.error,
        )
    }
}




//email id
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RegisterEmail(viewModel: RegistrationViewModel) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val state = viewModel.state
    val context = LocalContext.current
    /*LaunchedEffect(key1 = context) {
        viewModel.validationEvent.collect { event ->
            when(event) {
                is AuthViewModel.ValidationEvent.Success -> {
                    Toast.makeText(
                        context,
                        "Register Success",
                        Toast.LENGTH_LONG
                    ).show()

                }
            }

        }
    }*/

    OutlinedTextField(
        value = state.email,
        onValueChange = {
                        viewModel.onEvent(RegistrationFormEvent.EmailChanged(it))
        },
        isError = !state.emailValidationResult.successful,
        shape = RoundedCornerShape(topEnd =12.dp, bottomStart =12.dp),
        label = {
            Text("Email Address",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.labelMedium,
            ) },
        placeholder = { Text(text = "Email Address") },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Next,
            keyboardType = KeyboardType.Email
        )
        ,
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
    if (!state.emailValidationResult.successful){
        Text(
            text = state.emailValidationResult.errorMessage,
            color = MaterialTheme.colorScheme.error,
        )
    }
}

//password
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RegisterPassword(viewModel: RegistrationViewModel) {
    val keyboardController = LocalSoftwareKeyboardController.current

    var passwordHidden by rememberSaveable { mutableStateOf(true) }
    val state = viewModel.state
    val context = LocalContext.current
    /*LaunchedEffect(key1 = context) {
        viewModel.validationEvent.collect { event ->
            when(event) {
                is AuthViewModel.ValidationEvent.Success -> {
                    Toast.makeText(
                        context,
                        "Register Success",
                        Toast.LENGTH_LONG
                    ).show()

                }
            }

        }
    }*/
    OutlinedTextField(
        value = state.password,
        onValueChange = { viewModel.onEvent(RegistrationFormEvent.PasswordChanged(it)) },
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
            imeAction = ImeAction.Next,
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
    if (!state.passwordValidationResult.successful){
        Text(
            text = state.passwordValidationResult.errorMessage,
            color = MaterialTheme.colorScheme.error,
        )
    }
}

//password confirm
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RegisterPasswordConfirm(viewModel: RegistrationViewModel) {
    val keyboardController = LocalSoftwareKeyboardController.current

    var passwordHidden by rememberSaveable { mutableStateOf(true) }

    val state = viewModel.state
    val context = LocalContext.current
    /*LaunchedEffect(key1 = context) {
        viewModel.validationEvent.collect { event ->
            when(event) {
                is AuthViewModel.ValidationEvent.Success -> {
                    Toast.makeText(
                        context,
                        "Register Success",
                        Toast.LENGTH_LONG
                    ).show()

                }
            }

        }
    }*/
    OutlinedTextField(
        value = state.repeatedPassword,
        onValueChange = { viewModel.onEvent(RegistrationFormEvent.RepeatedPasswordChanged(it)) },
        isError = !state.repeatedPasswordValidationResult.successful,
        shape = RoundedCornerShape(topEnd =12.dp, bottomStart =12.dp),
        label = {
            Text("Confirm Password",
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
    if (!state.repeatedPasswordValidationResult.successful){
        Text(
            text = state.repeatedPasswordValidationResult.errorMessage,
            color = MaterialTheme.colorScheme.error,
        )
    }

}