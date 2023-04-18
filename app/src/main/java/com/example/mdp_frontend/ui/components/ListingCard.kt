package com.example.mdp_frontend.ui.components

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.mdp_frontend.model.ListingCardItem
import com.google.firebase.Timestamp
import kotlinx.coroutines.delay

@Composable
fun ListingCard(
    listing: ListingCardItem,
    onClick: (String) -> Unit
)
{

    //recomposing the ListingCard function every minute,
    // updating the relative time string to reflect the current time

    val currentTime = remember { mutableStateOf(System.currentTimeMillis()) }
    LaunchedEffect(Unit) {
        while (true) {
            delay(60000L)
            Log.d("ListingCard", "Recomposing ListingCard")

            currentTime.value = System.currentTimeMillis()
        }
    }
    Log.d("ListingCard", "Listing date: ${listing.date?.toDate()}")
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = { onClick(listing.id) }),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 5.dp
        )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Text(
                    text = listing.title,
                    fontWeight = FontWeight.Bold
                )
                Text(text = listing.category)
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val relativeTimeString = listing.date?.let { formatRelativeTime(it) }
                    if (relativeTimeString != null) {
                        Text(text = relativeTimeString,
                            modifier = Modifier.weight(1f))
                    }
                    Text(text = listing.location,
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.End
                    )
                }
            }
        }
    }
}


// formatting the timestamp as a relative time! smart stuff.
fun formatRelativeTime(timestamp: Timestamp): String {
    val now = System.currentTimeMillis()
    val diff = now - timestamp.toDate().time

    val seconds = diff / 1000
    val minutes = seconds / 60
    val hours = minutes / 60
    val days = hours / 24

    return when {
        minutes < 1 -> "just now"
        minutes < 60 -> "$minutes min ago"
        hours < 24 -> "$hours hour${if (hours > 1) "s" else ""} ago"
        else -> "$days day${if (days > 1) "s" else ""} ago"
    }
}
