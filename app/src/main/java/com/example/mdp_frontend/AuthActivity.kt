package com.example.mdp_frontend

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mdp_frontend.model.AuthScreenItems
import com.example.mdp_frontend.ui.screen.LoginScreen
import com.example.mdp_frontend.ui.screen.Splash
import com.example.mdp_frontend.ui.screen.authentication.RegisterScreen
import com.example.mdp_frontend.ui.theme.MDPfrontendTheme
import com.example.mdp_frontend.viewmodel.RegistrationViewModel

class AuthActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MDPfrontendTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AuthScreensNavigation()
                }
            }
        }
    }
}

@Composable
fun AuthScreensNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = AuthScreenItems.Splash.route) {
            composable(route = AuthScreenItems.Register.route) {
                RegisterScreen(navController = navController, viewModel = viewModel())
            }
            composable(route = AuthScreenItems.Login.route) {
                LoginScreen(navController = navController , viewModel = viewModel())
            }
            composable(route = AuthScreenItems.Splash.route) {
                Splash(navController = navController)
            }
    }

}
