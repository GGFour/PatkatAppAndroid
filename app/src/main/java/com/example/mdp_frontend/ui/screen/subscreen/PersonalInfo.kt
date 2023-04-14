package com.example.mdp_frontend.ui.screen.subscreen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mdp_frontend.model.TopBarItem
import com.example.mdp_frontend.ui.components.TopBar
import com.example.mdp_frontend.viewmodel.AuthViewModel


@Composable
fun PersonalInfo(viewModel: AuthViewModel = hiltViewModel(),onNavUp: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(16.dp)
            .padding(top = 56.dp),
            horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Column{
            Row {
                Text(text ="Name" )

                Text(text =viewModel?.currentUser?.displayName?:"")
            }

            Row() {
                Text(text = "Email")

                Text(text =viewModel?.currentUser?.email?:"")
            }
            // logout button
                Button(
                    onClick = {
                        viewModel?.logout()
                        // navigate to login screen
                        // TODO navigate to login screen or launch AuthActivity
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                ) {
                    Text(text = "Logout")
                }
        }
    }
}