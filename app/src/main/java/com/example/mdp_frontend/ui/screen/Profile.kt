package com.example.mdp_frontend.ui.screen


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mdp_frontend.ui.theme.md_theme_dark_background
import com.example.mdp_frontend.ui.theme.md_theme_light_background

@Composable
fun ProfileScreen(
    modifier: Modifier,
    onPersonalInfoChecked: () -> Unit,
    onNotificationsChecked: () -> Unit,
    onMyServicesChecked: () -> Unit,
    onMyTasksChecked: () -> Unit,
) {
    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .background(if (isSystemInDarkTheme()) md_theme_dark_background else md_theme_light_background),
        contentPadding = PaddingValues(top = 16.dp)) {
        item {
            ProfileHeader(name = "John Doe")
        }

        item {
            ProfileMenuItem(text = "Personal Info", onClick = onPersonalInfoChecked)
            ProfileMenuItem(text = "Notifications", onClick = onNotificationsChecked)
            ProfileMenuItem(text = "My Services", onClick = onMyServicesChecked)
            ProfileMenuItem(text = "My Tasks", onClick = onMyTasksChecked)
        }
    }
}

@Composable
fun ProfileHeader(name: String) {
    Text(
        text = name,
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(vertical = 16.dp, horizontal = 8.dp)
    )
}

@Composable
fun ProfileMenuItem(text: String, onClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .clickable(onClick = onClick)
    ) {
        IconButton(
            onClick = onClick,
            modifier = Modifier.width(24.dp)
        ) {
            Icon(
                imageVector = when (text) {
                    "Personal Info" -> Icons.Default.Person
                    "Notifications" -> Icons.Default.Notifications
                    "My Services" -> Icons.Default.Build
                    "My Tasks" -> Icons.Default.List
                    else -> Icons.Default.Error // handle unknown menu items gracefully
                },
                contentDescription = null
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Box(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = text,
                //fontWeight = FontWeight.Bold
            )

        }

        Icon(
            imageVector = Icons.Default.KeyboardArrowRight,
            contentDescription = null,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}


/*
@Composable
@Preview
fun ProfilePreview() {
    ProfileScreen(modifier = Modifier.
    background(md_theme_light_background))
}

*/