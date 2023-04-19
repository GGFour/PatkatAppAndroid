package com.example.mdp_frontend.ui.screen

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mdp_frontend.domain.model.Response
import com.example.mdp_frontend.ui.components.Listings
import com.example.mdp_frontend.ui.theme.md_theme_light_tertiary
import com.example.mdp_frontend.ui.theme.md_theme_light_tertiaryContainer
import com.example.mdp_frontend.viewmodel.HomeScreenViewModel


@Composable
fun HomeScreen(
    modifier: Modifier,
    onViewCategoriesClick: () -> Unit,
    onViewListingCardClick: (String) -> Unit,
    onCreateListingClick: () -> Unit,
    context: Context,
    viewModel: HomeScreenViewModel = hiltViewModel(),
) {
    //Home Screen body
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column {
            Spacer(modifier = Modifier.height(30.dp))
            SearchBar()
            CreateListing(onCreateListingClick = onCreateListingClick)
            FeaturedItemsHeader(
                onViewAllClick = onViewCategoriesClick,
            )
            when (viewModel.getListingsResponse) {
                is Response.Loading -> {
                    Box(modifier = Modifier.fillMaxSize()) {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }
                }
                is Response.Success -> {
                    Listings(
                        listings = viewModel.listings,
                        onListingCardClick = { listingId ->
                            onViewListingCardClick(listingId)
                        }
                    )
                }
                is Response.Failure -> {
                    Text(text = "Failed to fetch listings :(")
                }
            }
        }
    }
}

@Composable
fun SearchBar(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .height(85.dp)
            .padding(
                horizontal = 15.dp, vertical = 20.dp
            )
            .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(28.dp))
            .clip(RoundedCornerShape(10.dp))
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { /* TODO */ }) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}

@Composable
fun CreateListing(
    onCreateListingClick: () -> Unit,
    color: Color = MaterialTheme.colorScheme.tertiaryContainer
) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(15.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(color)
                .padding(horizontal = 15.dp, vertical = 20.dp)
                .fillMaxWidth()
        ) {
            Column(
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Have a work to do?",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )
                Text(
                    text = "Create a task and get a service!",
                    color = md_theme_light_tertiaryContainer,
                    fontSize = 16.sp
                )
                Button(
                    onClick = { onCreateListingClick() }
                ) {
                    Text(text = "Create")
                }
            }
        }
    }
}

@Composable
fun FeaturedItemsHeader(
    onViewAllClick: () -> Unit,

    ) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 15.dp, vertical = 20.dp
                ),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {


            Text(
                text = "Featured jobs",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(15.dp)
            )
            Text(
                text = "View all",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(15.dp)
                    .clickable(onClick = onViewAllClick)
            )
        }

    }
}

