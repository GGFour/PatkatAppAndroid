package com.example.mdp_frontend.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mdp_frontend.domain.model.ListingState
import com.example.mdp_frontend.model.TaskServiceRowItem

@Composable
fun TaskServiceRow(service: TaskServiceRowItem) {
    val colorScheme = MaterialTheme.colorScheme
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Icon and status name
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f)
        ) {
            Icon(
                imageVector = when (service.status) {
                    ListingState.Active -> Icons.Filled.CheckCircle
                    ListingState.WIP -> Icons.Filled.Schedule
                    ListingState.Finished -> Icons.Filled.Cancel
                    ListingState.Noticed -> Icons.Filled.Info
                    ListingState.Abandoned -> Icons.Filled.Warning
                },
                contentDescription = null,
                tint = when (service.status) {
                    ListingState.Active -> colorScheme.primary
                    ListingState.WIP -> colorScheme.secondary
                    ListingState.Finished -> colorScheme.error
                    ListingState.Noticed -> colorScheme.primary
                    ListingState.Abandoned -> colorScheme.error
                }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = service.status.name,
                style = MaterialTheme.typography.bodySmall,
                color = colorScheme.onSurfaceVariant,
            )
        }
        // Name of service
        Text(
            text = service.name,
            style = MaterialTheme.typography.bodyMedium,
            color = colorScheme.onSurface,
            modifier = Modifier.weight(1f)
        )
        // Price and duration of service
        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = "\$${service.price}",
                style = MaterialTheme.typography.bodyMedium,
                color = colorScheme.onSurface,
            )
        }
    }
}

@Preview
@Composable
fun TaskServiceRowPreview() {
    val service = TaskServiceRowItem(
        name = "Example Service",
        status = ListingState.Active,
        price = 100
    )
    TaskServiceRow(service = service)
}