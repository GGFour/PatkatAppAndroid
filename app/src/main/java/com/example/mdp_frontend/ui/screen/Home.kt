package com.example.mdp_frontend.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.mdp_frontend.model.ListingCardItem
import com.example.mdp_frontend.ui.components.ListingCard
import com.example.mdp_frontend.ui.theme.*



@Composable
fun HomeScreen(
    modifier: Modifier,
    onViewCategoriesClick: () -> Unit,
    onViewListingCardClick: () -> Unit
) {
    //Home Screen body
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column {
            Spacer(modifier = Modifier.height(30.dp))
            SearchBar()
            CreateProfile(onCreateProfileClick = { /*TODO*/ })
            FeaturedItems(

                //sample test, will be change once we have the database set up
                 sampleListings = listOf(
                    ListingCardItem(
                        title = "Backyard cleaning",
                        category = "Household",
                        location = "Itäkeskus, Hel",
                        date = "2023-04-03"
                            ),
                    ListingCardItem(
                        title = "Grocery shopping",
                        category = "Errands",
                        location = "Kamppi, Hel",
                        date = "2023-04-04"
                    ),
                    ListingCardItem(
                        title = "Dog walking",
                        category = "Pet care",
                        location = "Kallio, Hel",
                        date = "2023-04-05"
                    )
                ),

                        onViewAllClick = onViewCategoriesClick,
                        onListingCardClick = onViewListingCardClick


            )

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
fun CreateProfile(onCreateProfileClick: () -> Unit,
    color: Color = md_theme_light_tertiary
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
                text = "Start working!",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )
            Text(
                text = "Create a profile and start earning!",
                color = md_theme_light_tertiaryContainer,
                fontSize = 16.sp
            )
            Button(
                onClick = {onCreateProfileClick()}
            ){
                Text(text = "Create")
            }
        }
    }
}



@Composable
fun FeaturedItems(
    sampleListings: List<ListingCardItem>,
    onViewAllClick: () -> Unit,
    onListingCardClick: () -> Unit,

) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth()
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

        LazyColumn(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(sampleListings) { listing ->
                ListingCard(
                    listing,
                    onClick = onListingCardClick
                )
            }
        }

    }
}

