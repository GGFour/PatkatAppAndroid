@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.mdp_frontend.ui.authentication

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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mdp_frontend.R
import com.example.mdp_frontend.model.RegistrationFormEvent
import com.example.mdp_frontend.ui.authentication.components.*


@Composable
fun RegisterScreen(onNavTextBtnClicked: () -> Unit, viewModel: RegistrationViewModel = viewModel()) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(
                color = Color.Transparent,
            )
    ) {
        val context = LocalContext.current
        LaunchedEffect(key1 = context) {
            viewModel.validationEvent.collect { event ->
                when (event) {
                    is RegistrationViewModel.ValidationEvent.Success -> {
                        Toast.makeText(
                            context, "Register Success", Toast.LENGTH_LONG
                        ).show()

                    }
                }

            }
        }
        Box(
            modifier = Modifier
                .align(Alignment.Center),
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

                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                //.........................Spacer
                Spacer(modifier = Modifier.height(30.dp))

                //.........................Text: title
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
                AuthNameInput(viewModel = viewModel)


                Spacer(modifier = Modifier.padding(3.dp))
                AuthEmailInput(viewModel = viewModel)

                Spacer(modifier = Modifier.padding(3.dp))
                AuthPasswordInput(viewModel = viewModel)

                Spacer(modifier = Modifier.padding(3.dp))
                AuthPasswordConfirmInput(viewModel = viewModel)


                val gradientColor = listOf(Color(0xFF484BF1), Color(0xFF673AB7))
                val cornerRadius = 16.dp

                Spacer(modifier = Modifier.padding(10.dp))

                GradientButton(
                    onClick = { viewModel.onEvent(RegistrationFormEvent.Register) },
                    gradientColors = gradientColor,
                    cornerRadius = cornerRadius,
                    nameButton = "Create An Account",
                    roundedCornerShape = RoundedCornerShape(topStart = 30.dp, bottomEnd = 30.dp),
                )

                Spacer(modifier = Modifier.padding(10.dp))
                TextButton(onClick = onNavTextBtnClicked) {
                    Text(
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

    Button(

        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 32.dp, end = 32.dp), onClick = onClick,

        contentPadding = PaddingValues(), colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent
        ), shape = RoundedCornerShape(cornerRadius)
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
            Text(
                text = nameButton, fontSize = 20.sp, color = Color.White
            )
        }
    }
}
