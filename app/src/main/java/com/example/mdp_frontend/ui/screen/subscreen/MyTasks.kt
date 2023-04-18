package com.example.mdp_frontend.ui.screen.subscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mdp_frontend.model.Status
import com.example.mdp_frontend.model.TaskServiceRowItem
import com.example.mdp_frontend.model.TopBarItem
import com.example.mdp_frontend.ui.components.SubscreenHeader
import com.example.mdp_frontend.ui.components.TaskServiceRow
import com.example.mdp_frontend.ui.components.TopBar
import com.example.mdp_frontend.ui.theme.md_theme_light_background


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
            status = Status.Active,
            price = 100
        ),
        TaskServiceRowItem(
            name = "Example Task 2",
            status = Status.InProgress,
            price = 200
        ),
        TaskServiceRowItem(
            name = "Example Task 3",
            status = Status.Finished,
            price = 300
        )
    )
    MyTasks(services = services, onNavUp = {})
}