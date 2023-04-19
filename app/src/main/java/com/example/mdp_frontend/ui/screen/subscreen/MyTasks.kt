package com.example.mdp_frontend.ui.screen.subscreen

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.mdp_frontend.domain.model.ListingState
import com.example.mdp_frontend.model.TaskServiceRowItem
import com.example.mdp_frontend.ui.components.SubscreenHeader
import com.example.mdp_frontend.ui.components.TaskServiceRow


@Composable
fun MyTasks(services: List<TaskServiceRowItem>, onNavUp: () -> Unit) {
    SubscreenHeader(title = "My Tasks", onNavUp = onNavUp) {

        val colorScheme = MaterialTheme.colorScheme
        LazyColumn {
            items(services) { service ->
                TaskServiceRow(service = service)
                Divider(color = colorScheme.onSurfaceVariant)
            }
        }
    }
}

@Preview
@Composable
fun MyTasksPreview() {
    val services = listOf(
        TaskServiceRowItem(
            name = "Example Task 1",
            status = ListingState.Active,
            price = 100
        ),
        TaskServiceRowItem(
            name = "Example Task 2",
            status = ListingState.WIP,
            price = 200
        ),
        TaskServiceRowItem(
            name = "Example Task 3",
            status = ListingState.Finished,
            price = 300
        )
    )
    MyTasks(services = services, onNavUp = {})
}