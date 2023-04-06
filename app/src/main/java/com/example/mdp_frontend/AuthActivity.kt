package com.example.mdp_frontend

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mdp_frontend.ui.screen.LoginScreen
import com.example.mdp_frontend.ui.screen.Splash
import com.example.mdp_frontend.ui.screen.authentication.RegisterScreen
import com.example.mdp_frontend.ui.theme.MDPfrontendTheme

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

enum class AuthScreenItems() {
    Splash,
    Login,
    Register,
}

@Composable
fun AuthScreensNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = AuthScreenItems.Splash.name) {
            composable(route = AuthScreenItems.Register.name) {
                RegisterScreen(
                    onNavTextBtnClicked = {
                      navController.navigateUp()
                    },
                    viewModel = viewModel(),
                )
            }
            composable(route = AuthScreenItems.Login.name) {
                LoginScreen(
                    onNavTextBtnClicked = {
                      navController.navigate(AuthScreenItems.Register.name)
                    },
                    viewModel = viewModel(),
                )
            }
            composable(route = AuthScreenItems.Splash.name) {
                Splash(onAnimationFinish = {
                    navController.popBackStack()
                    navController.navigate(AuthScreenItems.Login.name)
                })
            }
    }

}
