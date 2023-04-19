package com.example.mdp_frontend.ui.screen.subscreen

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mdp_frontend.ui.components.RequestNotificationBox

@Composable
fun Notifications(
    onNavUp: () -> Unit,
    viewModel: NotificationsViewModel = hiltViewModel()
) {
    val notifications by viewModel.notifications.collectAsState()

    //sample data but it will be changed
    /*
    val notifications = listOf(
       RequestNotificationBoxItem(
         //  taskerPicture = ImageBitmap.imageResource(R.drawable.demo),
           taskerName = "John Doe",
           taskerRating = 4.5f,
           requestTime = "10:30 AM",
           serviceName = "Cleaning"
       ),
       RequestNotificationBoxItem(
           //taskerPicture = ImageBitmap.imageResource(R.drawable.demo),
           taskerName = "Jane J",
           taskerRating = 4.0f,
           requestTime = "11:00 AM",
           serviceName = "Gardening"
       ),
       RequestNotificationBoxItem(
          // taskerPicture = ImageBitmap.imageResource(R.drawable.demo),
           taskerName = "Bob J",
           taskerRating = 3.5f,
           requestTime = "1:30 PM",
           serviceName = "Plumbing"
       )
    )
    */
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

/*
@Composable
@Preview
fun NoPreview(){
    Notifications()
}
 */