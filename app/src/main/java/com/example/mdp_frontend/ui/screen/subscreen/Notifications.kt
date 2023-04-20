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
import com.example.mdp_frontend.ui.components.SubscreenHeader

@Composable
fun Notifications(
    onNavUp: () -> Unit,
    viewModel: NotificationsViewModel = hiltViewModel()
) {
    val notifications by viewModel.notifications.collectAsState()

    SubscreenHeader(title = "My Notifications", onNavUp = onNavUp) {
        LazyColumn {
            items(notifications) { notification ->
                RequestNotificationBox(
                    jobRequest = notification,
                    onAcceptClick = { viewModel.acceptRequest(notification) },
                    onDeclineClick = { viewModel.declineRequest(notification) }
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}
