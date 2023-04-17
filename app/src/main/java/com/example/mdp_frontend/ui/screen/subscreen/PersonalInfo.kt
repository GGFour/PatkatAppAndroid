package com.example.mdp_frontend.ui.screen.subscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ExitToApp
import androidx.compose.material.icons.outlined.MailOutline
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.example.mdp_frontend.model.TopBarItem
import com.example.mdp_frontend.ui.components.TopBar
import com.example.mdp_frontend.viewmodel.AuthViewModel


@Composable
fun PersonalInfo(viewModel: AuthViewModel = hiltViewModel(),onNavUp: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally

    ) {
        TopBar(model = TopBarItem(
            title = "Personal Info ",
            onNavUpPressed = onNavUp
        )
        )
        Spacer(modifier = Modifier.height(16.dp))
        // Use CircleAvatar to display the profile picture with a border and a placeholder image
        Image(
            painter = rememberImagePainter(viewModel?.currentUser?.photoUrl),
            contentDescription = "Profile picture",
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .border(2.dp, MaterialTheme.colorScheme.onSurface, CircleShape)
        )
        // Use Spacer for vertical spacing
        Spacer(modifier = Modifier.height(16.dp))

        // Use Text with a large font size and bold style to display the user name
        Text(
            text = viewModel?.currentUser?.displayName ?: "",
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
        )
        // Use Icon with a mail outline to display the user email
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Outlined.MailOutline,
                contentDescription = "Email",
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = viewModel?.currentUser?.email ?: "",
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        Divider()
        Button(
            onClick = {
                viewModel?.logout()
                // navigate to login screen
                // TODO navigate to login screen or launch AuthActivity
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            // Use OutlinedButton for a different style
            colors = ButtonDefaults.outlinedButtonColors()
        ) {
            Icon(
                imageVector = Icons.Outlined.ExitToApp,
                contentDescription = "Logout"
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Logout")
        }
    }
}