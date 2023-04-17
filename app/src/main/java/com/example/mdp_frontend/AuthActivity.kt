package com.example.mdp_frontend

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.mdp_frontend.ui.authentication.AuthScreensNavigation
import com.example.mdp_frontend.ui.theme.MDPfrontendTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MDPfrontendTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    AuthScreensNavigation(
                        onAuthenticated = { startMainActivity(this) },
                        onAuthSuccess = { startMainActivity(this) },
                    )
                }
            }
        }
    }
}

fun startMainActivity(current: Activity) {
    val intent = Intent(current, MainActivity::class.java)
    current.startActivity(intent)
}


@Preview(showBackground = true)
@Composable
fun AuthPreview() {
    MDPfrontendTheme {
        AuthScreensNavigation(
            onAuthSuccess = {},
            onAuthenticated = {},
        )
    }
}
