package com.example.mdp_frontend.ui.create_listing.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mdp_frontend.domain.model.Listing

@Composable
fun ReviewListingScreen(
    listing: Listing,
    onSubmitPressed: () -> Unit,
    onCancelPressed: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.Start,
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text = "Title", style = MaterialTheme.typography.headlineSmall)
            Text(text = listing.title, style = MaterialTheme.typography.bodyMedium)
            Divider(modifier = Modifier.padding(horizontal = 16.dp))
            Text(text = "Description", style = MaterialTheme.typography.headlineSmall)
            Text(text = listing.description, style = MaterialTheme.typography.bodyMedium)
            Divider(modifier = Modifier.padding(horizontal = 16.dp))
            Text(text = "Price", style = MaterialTheme.typography.headlineSmall)
            Text(text = listing.priceStr, style = MaterialTheme.typography.bodyMedium)
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            OutlinedButton(onClick = onCancelPressed) {
                Text(text = "Cancel")
            }
            ElevatedButton(onClick = onSubmitPressed ) {
                Text(text = "Submit")
            }
        }
    }
}
