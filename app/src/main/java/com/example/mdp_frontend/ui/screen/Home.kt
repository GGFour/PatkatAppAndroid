package com.example.mdp_frontend.ui.screen

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.mdp_frontend.domain.use_case.getListings
import com.example.mdp_frontend.domain.use_case.Listings
import com.example.mdp_frontend.model.ListingCardItem
import com.example.mdp_frontend.ui.theme.*




@Composable
fun HomeScreen(
    modifier: Modifier,
    onViewCategoriesClick: () -> Unit,
    onViewListingCardClick: () -> Unit,
    onCreateListingClick: () -> Unit,
    context: Context
) {
    val listings = remember { mutableStateOf<List<ListingCardItem>>(emptyList()) }
    val isLoading = remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        getListings(context, limit = 5) { listingItems ->
            listings.value = listingItems
            isLoading.value = false
        }
    }

    //Home Screen body
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        if (isLoading.value) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else {
            Column {
                Spacer(modifier = Modifier.height(30.dp))
                SearchBar()
                /*CreateProfile(onCreateProfileClick = { /*TODO*/ })*/
                CreateListing(onCreateListingClick = onCreateListingClick)
                FeaturedItemsHeader(
                    onViewAllClick = onViewCategoriesClick,
                )
                Listings(
                    listings = listings.value,
                    onListingCardClick = onViewListingCardClick
                )
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
            .background(Color.LightGray, RoundedCornerShape(28.dp))
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
                )
            }
        }
    }
}

@Composable
fun CreateListing(onCreateListingClick: () -> Unit,
                  color: Color = md_theme_light_tertiary
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
        ){
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
                    onClick = {onCreateListingClick()}
                ){
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

