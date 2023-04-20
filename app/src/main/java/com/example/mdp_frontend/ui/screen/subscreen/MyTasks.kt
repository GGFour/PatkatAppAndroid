package com.example.mdp_frontend.ui.screen.subscreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mdp_frontend.domain.model.Response
import com.example.mdp_frontend.model.TaskServiceRowItem
import com.example.mdp_frontend.ui.components.SubscreenHeader
import com.example.mdp_frontend.ui.components.TaskServiceRow


@Composable
fun MyTasks(
    onTaskClick: (String) -> Unit,
    onNavUp: () -> Unit,
    viewModel: MyTasksViewModel = hiltViewModel(),
) {
    SubscreenHeader(title = "My Tasks", onNavUp = onNavUp) {

        when (viewModel.servicesResponse) {
            is Response.Loading -> {
                Box(modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            }
            is Response.Success -> {
                LazyColumn {
                    items((viewModel.servicesResponse as Response.Success<List<TaskServiceRowItem>>).data) { service ->
                        TaskServiceRow(service = service, onClick = onTaskClick)
                        Divider(color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }
            is Response.Failure -> {
                Text("Something went wrong :<")
            }
        }
    }
}