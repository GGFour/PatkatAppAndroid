package com.example.mdp_frontend.ui.screen.subscreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun MyServices(services: List<Service>) {
    val colorScheme = MaterialTheme.colorScheme
    LazyColumn {
        items(services) { service ->
            ServiceListItem(service = service)
            Divider(color = colorScheme.onSurfaceVariant)
        }
    }
}

@Composable
fun ServiceListItem(service: Service) {
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
                    Status.Active -> Icons.Filled.CheckCircle
                    Status.InProgress -> Icons.Filled.Schedule
                    Status.Finished -> Icons.Filled.Cancel
                },
                contentDescription = null,
                tint = when (service.status) {
                    Status.Active -> colorScheme.primary
                    Status.InProgress -> colorScheme.secondary
                    Status.Finished -> colorScheme.error
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
@Composable
@Preview
fun myServicesPreview (){
    MyServices(services=  listOf(
        Service(name = "House cleaning", status = Status.Active, price = 50, ),
        Service(name = "Dog walking", status = Status.InProgress, price = 10, ),
        Service(name = "Car wash", status = Status.Finished, price = 20,),
        Service(name = "Lawn mowing", status = Status.Active, price = 15, ),
        Service(name = "Grocery delivery", status = Status.InProgress, price = 25,)
    ))

}