package com.example.mdp_frontend.ui.screen.subscreen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.example.mdp_frontend.domain.model.Listing
import com.example.mdp_frontend.domain.model.ListingState
import com.example.mdp_frontend.domain.model.Response
import com.example.mdp_frontend.domain.model.User
import com.example.mdp_frontend.ui.components.SubscreenHeader


@Composable
fun ListingDetailScreen(
    listingId: String?,
    onNavUp: () -> Unit,
    viewModel: ListingDetailViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val reactionFlow = viewModel.reactionFlow.collectAsState()

    LaunchedEffect(listingId) {
        viewModel.loadListing(listingId)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        when (val response = viewModel.listingResponse) {
            is Response.Failure -> Text(text = response.e.toString())
            is Response.Success<Listing> -> {
                val listing = response.data
                SubscreenHeader(title = "Listing Details", onNavUp = onNavUp) {
                    ListingViewItself(listing = listing)
                }
                Spacer(modifier = Modifier.height(16.dp))
                StateTransitionHell(
                    isOwner = viewModel.isOwner,
                    isAssignee = viewModel.isAssignee,
                    listing = listing,
                    onReactClicked = { viewModel.reactOnListing(listing) },
                    onAcceptClicked = { viewModel.acceptRequest(listing) },
                    onRejectClicked = { viewModel.rejectRequest(listing) },
                    onDoneClicked = { viewModel.finishListing(listing) },
                )
            }
            is Response.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            }
        }
    }
    reactionFlow.value.let {
        when (it) {
            is Response.Loading -> {}
            is Response.Failure -> {
                it.e.printStackTrace()
                Toast.makeText(context, "Failed to perform the action #<", Toast.LENGTH_LONG).show()
            }
            is Response.Success -> {
                Toast.makeText(context, it.data.toString(), Toast.LENGTH_LONG).show()
                viewModel.loadListing(listingId)
            }
            null -> {}
        }
    }
}


@Composable
fun StateTransitionHell(
    isOwner: Boolean,
    isAssignee: Boolean,
    listing: Listing,
    onReactClicked: () -> Unit,
    onAcceptClicked: () -> Unit,
    onRejectClicked: () -> Unit,
    onDoneClicked: () -> Unit,
) {
    if (isOwner) {
        when (listing.state) {
            ListingState.Active -> {}
            ListingState.Noticed -> {
                AcceptRejectButtons(
                    onAcceptClicked = onAcceptClicked,
                    onRejectClicked = onRejectClicked,
                    assignee = listing.assignee,
                )
            }
            ListingState.WIP -> {
                FinishListingButton(onDoneClicked)
            }
            ListingState.Finished -> {
                Text("The work is done - Stay with us")
            }
            ListingState.Abandoned -> {}
        }

    } else {
        when (listing.state) {
            ListingState.Active -> {
                ReactionButtons(
                    onReactClicked = onReactClicked,
                    onContactClicked = { /*TODO*/ },
                )
            }
            ListingState.Noticed -> {
                if (isAssignee) {
                    Text("Work request has been sent to publisher, so we're waiting for his response")
                } else {
                    Text("Sorry, this one was reserved, keep on trying")
                }
            }
            ListingState.WIP -> {
                if (isAssignee) {
                    Text("Publisher has accepted the request - work hard, hbbi!")
                } else {
                    Text("Sorry, this one was reserved, keep on trying")
                }
            }
            ListingState.Finished -> {
                Text("The work is done - Don't sit down, it's time to dig another one")
            }
            ListingState.Abandoned -> {}
        }
    }
}

@Composable
private fun FinishListingButton(
    onFinishClicked: () -> Unit,
) {
    var confirmation by remember { mutableStateOf(false) }
    Column(modifier = Modifier.fillMaxWidth()) {
        if (confirmation) {
            Text("Are you sure that work is done?")
            Row(modifier = Modifier.fillMaxWidth()) {
                Button(
                    onClick = onFinishClicked,
                ) {
                    Text(text = "YES")
                }
                Button(
                    onClick = { confirmation = false },
                ) {
                    Text(text = "NO")
                }
            }
        } else {
            Text("The work is in progress - smash this button, once it is done")
            Button(
                onClick = { confirmation = true },
            ) {
                Text(text = "Work is done")
            }
        }
    }
}

@Composable
private fun ReactionButtons(
    onReactClicked: () -> Unit,
    onContactClicked: () -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()
    ) {
        Button(
            onClick = onReactClicked,
        ) {
            Icon(imageVector = Icons.Default.Favorite, contentDescription = "React")
            Text(text = "React")
        }
        Button(
            onClick = onContactClicked
        ) {
            Icon(
                imageVector = Icons.Default.Email, contentDescription = "Contact"
            )
            Text(
                text = "Contact"
            )
        }
    }
}

@Composable
private fun AcceptRejectButtons(
    onAcceptClicked: () -> Unit,
    onRejectClicked: () -> Unit,
    assignee: User?,
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text("${assignee?.name ?: "WHY DA FUK ASSIGNEE IS NULL AND WE SHOW THIS?!"} wants to do the job")
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = onAcceptClicked,
            ) {
                Icon(imageVector = Icons.Outlined.CheckCircle, contentDescription = "React")
                Text(text = "Accept")
            }
            Button(
                onClick = onRejectClicked,
            ) {
                Icon(
                    imageVector = Icons.Outlined.Cancel, contentDescription = "Contact"
                )
                Text(
                    text = "Reject"
                )
            }
        }
    }
}

@Composable
fun ListingViewItself(listing: Listing) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
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
            text = listing.title, style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = listing.description, style = TextStyle(fontSize = 16.sp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter = rememberAsyncImagePainter(model = listing.publisher?.imageUrl),
                    contentDescription = "Publisher picture",
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                )
                Text(
                    text = listing.publisher?.name ?: "", style = TextStyle(fontSize = 12.sp)
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
    }
}

/*
@Composable
@Preview
fun ListingDetailScreenPreview() {

    ListingDetailScreen(
        Listing(
            id = "1",
    pictureUrl = "https://picsum.photos/200",
    title = "Sample Listing",
    description = "This is a sample listing for preview purposes." +
            "senectus et netus et malesuada fames ac turpis egestas. " +
            "Vestibulum tortor quam, feugiat vitae, ultricies eget, " +
            "tempor sit amet, ante. Donec eu libero sit amet quam egestas semper." +
            "Aenean ultricies mi vitae est",
    publisher = User(
        imageUrl = "https://picsum.photos/40",
        name = "John Doe",
    ),
    rating = 4,
    price = 99.99
    )
    )
}

 */