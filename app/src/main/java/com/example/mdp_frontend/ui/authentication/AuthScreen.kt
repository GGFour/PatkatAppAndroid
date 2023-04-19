package com.example.mdp_frontend.ui.authentication

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mdp_frontend.ui.screen.Splash

enum class AuthScreenItems {
    Splash, Login, Register,
}

@Composable
fun AuthScreensNavigation(
    onAuthSuccess: () -> Unit,
    onAuthenticated: () -> Unit,
    viewModel: AuthViewModel = hiltViewModel(),
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = AuthScreenItems.Splash.name,
    ) {
        composable(route = AuthScreenItems.Register.name) {
            RegisterScreen(
                onNavTextBtnClicked = {
                    navController.navigateUp()
                },
                onRegisterSuccess = onAuthSuccess,
                fViewModel = viewModel,
            )
        }
        composable(route = AuthScreenItems.Login.name) {
            LoginScreen(
                onNavTextBtnClicked = {
                    navController.navigate(AuthScreenItems.Register.name)
                },
                onLoginSuccess = onAuthSuccess,
                fViewModel = viewModel,
            )
        }
        composable(route = AuthScreenItems.Splash.name) {
            Splash(onAnimationFinish = {
                if (viewModel.authenticated) {
                    onAuthenticated()
                } else {
                    navController.popBackStack()
                    navController.navigate(AuthScreenItems.Login.name)
                }
            })
        }
    }
}
