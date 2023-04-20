package com.example.mdp_frontend.ui.screen.subscreen

import android.widget.Toast
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mdp_frontend.domain.model.Response
import com.example.mdp_frontend.ui.components.RequestNotificationBox
import com.example.mdp_frontend.ui.components.SubscreenHeader

@Composable
fun Notifications(
    onNavUp: () -> Unit,
    viewModel: NotificationsViewModel = hiltViewModel()
) {
    val actionChannel = viewModel.actionChannel.collectAsState()
    val context = LocalContext.current
    SubscreenHeader(title = "My Notifications", onNavUp = onNavUp) {
        LazyColumn {
            items(viewModel.notifications) { notification ->
                RequestNotificationBox(
                    jobRequest = notification,
                    onAcceptClick = { viewModel.acceptRequest(notification) },
                    onDeclineClick = { viewModel.declineRequest(notification) }
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
    when (actionChannel.value) {
        is Response.Failure -> {
            Toast.makeText(context, "Failed to send an action", Toast.LENGTH_SHORT).show()
        }
        Response.Loading -> {}
        is Response.Success -> {
            Toast.makeText(
                context,
                (actionChannel.value as Response.Success<String>).data,
                Toast.LENGTH_SHORT
            ).show()
            viewModel.getNotifications()
        }
        null -> {}
    }
}
