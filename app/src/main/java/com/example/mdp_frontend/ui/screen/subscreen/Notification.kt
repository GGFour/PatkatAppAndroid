package com.example.mdp_frontend.ui.screen.subscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mdp_frontend.R
import com.example.mdp_frontend.model.RequestNotificationBoxItem
import com.example.mdp_frontend.model.TopbarItem
import com.example.mdp_frontend.ui.components.RequestNotificationBox
import com.example.mdp_frontend.ui.components.TopBar
import com.example.mdp_frontend.ui.theme.md_theme_light_background

@Composable
fun Notification() {

    //sample data but it will be changed
    val notifications = listOf(
        RequestNotificationBoxItem(
            taskerPicture = ImageBitmap.imageResource(R.drawable.demo),
            taskerName = "John Doe",
            taskerRating = 4.5f,
            requestTime = "10:30 AM",
            serviceName = "Cleaning"
        ),
        RequestNotificationBoxItem(
            taskerPicture = ImageBitmap.imageResource(R.drawable.demo),
            taskerName = "Jane J",
            taskerRating = 4.0f,
            requestTime = "11:00 AM",
            serviceName = "Gardening"
        ),
        RequestNotificationBoxItem(
            taskerPicture = ImageBitmap.imageResource(R.drawable.demo),
            taskerName = "Bob J",
            taskerRating = 3.5f,
            requestTime = "1:30 PM",
            serviceName = "Plumbing"
        )
    )

    Column(modifier = Modifier.
    background(md_theme_light_background).
    padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp)
    ) {
        TopBar(model = TopbarItem(
            title = "notification",
            onBackPressed = { /* do something */ }
        ))

        LazyColumn {
            items(notifications) { notification ->
                RequestNotificationBox(
                    jobRequest = notification,
                    onAcceptClick = { /* do something */ },
                    onDeclineClick = { /* do something */ }
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
@Preview
fun NoPreview(){
    Notification()
}