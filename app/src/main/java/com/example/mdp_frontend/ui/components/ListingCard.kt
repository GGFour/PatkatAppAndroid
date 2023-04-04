package com.example.mdp_frontend.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mdp_frontend.model.ListingCardItem

@Composable
fun ListingCard(Listing: ListingCardItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
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
                    text = Listing.title,
                    fontWeight = FontWeight.Bold
                )
                Text(text = Listing.category)
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = Listing.date,
                        modifier = Modifier.weight(1f))
                    Text(text = Listing.location,
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.End
                    )
                }
            }
        }
    }
}

@Composable
@Preview
fun ListingCardPreview(){
    ListingCard(Listing = ListingCardItem(
        title = "Backyard cleaning",
        category = "Household",
        location = "itäkeskus, Hel",
        date = "2023-04-03"
    ) )
}