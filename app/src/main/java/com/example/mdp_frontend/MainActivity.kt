package com.example.mdp_frontend

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Chat
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import com.example.mdp_frontend.model.NavTabItem
import com.example.mdp_frontend.ui.components.BottomNavigationBar
import com.example.mdp_frontend.ui.screen.ChatScreen
import com.example.mdp_frontend.ui.screen.HomeScreen
import com.example.mdp_frontend.ui.screen.MapScreen
import com.example.mdp_frontend.ui.screen.ProfileScreen
import com.example.mdp_frontend.ui.theme.MDPfrontendTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MDPfrontendTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    App()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App() {
    val items = listOf(
        NavTabItem("Home", "home", Icons.Outlined.Home),
        NavTabItem("Map", "map", Icons.Outlined.Map),
        NavTabItem("Chat", "chat", Icons.Outlined.Chat),
        NavTabItem("Profile", "profile", Icons.Filled.Person),
    )
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNavigationBar(
            items = items,
            modifier = Modifier,
            navController = navController,
            onItemClick = {
                navController.navigate(it.route)
            }
        )},
    ) {
        Navigation(navController, modifier = Modifier.padding(paddingValues = it))
    }
}

@Composable
fun Navigation(navController: NavHostController, modifier: Modifier) {
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(modifier)
        }
        composable("map") {
            MapScreen(modifier)
        }
        composable("chat") {
            ChatScreen(modifier)
        }
        composable("profile") {
            ProfileScreen(modifier)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MDPfrontendTheme {
        App()
    }
}