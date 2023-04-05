package com.example.mdp_frontend.ui.screen.subscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.mdp_frontend.model.SingleListingDetail
import com.example.mdp_frontend.model.TopbarItem
import com.example.mdp_frontend.ui.components.TopBar


@Composable
fun ListingDetailScreen(listing: SingleListingDetail) {
    Column {
        TopBar(model = TopbarItem(
            title = "listing detail",
            onBackPressed = { /* do something */ }
            ))
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = listing.pictureUrl),
                contentDescription = "Listing picture",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = listing.title,
                style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = listing.description,
                style = TextStyle(fontSize = 16.sp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(
                        painter = rememberAsyncImagePainter(model = listing.publisherPictureUrl),
                        contentDescription = "Publisher picture",
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                    )
                    Text(
                        text = listing.publisherName,
                        style = TextStyle(fontSize = 12.sp)
                    )
                    Row {
                        repeat(listing.rating) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = "Star",
                                tint = Color.Yellow
                            )
                        }
                    }
                }
                Text(
                    text = "$${listing.price}",
                    style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = { /* react to listing */ }) {
                    Icon(imageVector = Icons.Default.Favorite, contentDescription = "React")
                    Text(text = "React")
                }
                Button(
                    onClick = { /* contact publisher */ }) {
                    Icon(imageVector = Icons.Default.Email, contentDescription = "Contact")
                    Text(text = "Contact")
                }
            }
        }

    }
}

//will be deleted
@Composable
@Preview
fun ListingDetailScreenPreview() {

    ListingDetailScreen(
        SingleListingDetail(
            id = "1",
    pictureUrl = "https://picsum.photos/200",
    title = "Sample Listing",
    description = "This is a sample listing for preview purposes." +
            "senectus et netus et malesuada fames ac turpis egestas. " +
            "Vestibulum tortor quam, feugiat vitae, ultricies eget, " +
            "tempor sit amet, ante. Donec eu libero sit amet quam egestas semper." +
            "Aenean ultricies mi vitae est",
    publisherPictureUrl = "https://picsum.photos/40",
    publisherName = "John Doe",
    rating = 4,
    price = 99.99
    )
    )
}