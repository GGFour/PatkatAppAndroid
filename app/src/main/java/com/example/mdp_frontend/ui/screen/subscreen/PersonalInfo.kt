package com.example.mdp_frontend.ui.screen.subscreen

import android.app.Activity
import android.content.Context
import android.content.Intent
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.mdp_frontend.ui.authentication.AuthViewModel
import com.example.mdp_frontend.ui.components.SubscreenHeader
import com.example.mdp_frontend.AuthActivity


@Composable
fun PersonalInfo(viewModel: AuthViewModel = hiltViewModel(),onNavUp: () -> Unit) {
    val context = LocalContext.current

    SubscreenHeader(title = "Personal Info", onNavUp = onNavUp) {
    Spacer(modifier = Modifier.height(16.dp))
        // Use CircleAvatar to display the profile picture with a border and a placeholder image
        AsyncImage(
            // painter = rememberAsyncImagePainter(viewModel?.currentUser?.photoUrl),
            model = "https://sp-images.summitpost.org/947006.jpg?auto=format&fit=max&ixlib=php-2.1.1&q=35&w=1024&s=d877834568578388ef13b78e3cd7ba2b",
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
                // launch AuthActivity
                startAuthActivity(context)
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

fun startAuthActivity(context: Context) {
    val intent = Intent(context, AuthActivity::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
    }
    ContextCompat.startActivity(context, intent, null)
    //disable onBackPressed button
    (context as? Activity)?.finishAffinity()
}
