package com.example.mdp_frontend.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.mdp_frontend.ui.components.features
import com.example.mdp_frontend.ui.theme.*



@Composable
fun HomeScreen(
    modifier: Modifier,
    onViewCategoriesClick: () -> Unit
) {
    //Home Screen body
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        //Contents and modifiers of Home Screen body
        //All functions are called inside this column
        Column {
            SearchBar()
            CreateProfile(onCreateProfileClick = { /*TODO*/ })
            FeaturedItems(
                features = listOf(
                    features(
                        title = "Kaupassa käynti",
                        color = md_theme_light_tertiaryContainer
                    ),
                    features(
                        title = "Koiran hoitajaa etsitään!",
                        color = md_theme_light_onSurface
                    ),
                    features(
                        title = "Auraus apua tarvitaan!",
                        color = md_theme_dark_onSurface
                    ),
                    features(
                        title = "Haravointi hommaa!",
                        color = md_theme_dark_tertiary
                    ),
                    features(
                        title = "Muuttoapu",
                        color = md_theme_dark_onError
                    )
                ),
                        onViewAllClick = onViewCategoriesClick
            )
            SmallCategories(
                chips = listOf("Valokuvaus", "Eläinhoito", "Puutarhat", "Huolto", "Ikkunoiden pesu", "Remontti")
            )
        }
    }
}

@Composable
fun SmallCategories (
    chips: List<String>
): Unit {
    var selectedChipIndex by remember {
        mutableStateOf(0)
    }
    return LazyRow() {
        items(chips.size) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .padding(start = 15.dp, top = 15.dp, bottom = 15.dp)
                    .clickable {
                        selectedChipIndex = it
                    }
                    .clip(RoundedCornerShape(10.dp))
                    .background(
                        if (selectedChipIndex == it) Color.DarkGray
                        else Color.LightGray
                    )
                    .padding(15.dp)
            ){
                Text(text = chips[it], color = Color.White)
            }
        }
    }
}

@Composable
fun SearchBar(modifier: Modifier = Modifier) {
    var query by remember { mutableStateOf(TextFieldValue()) }
    Box(
        modifier = modifier
            .height(56.dp)
            .background(Color.White)
            .padding(horizontal = 16.dp, vertical = 8.dp),
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
    features: List<features>,
    onViewAllClick: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
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
            modifier = Modifier.padding(15.dp)
            .clickable(onClick = onViewAllClick)
        )
        LazyVerticalGrid(
            contentPadding = PaddingValues(start = 7.5.dp, end = 7.5.dp, bottom = 100.dp),
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxHeight()
        ) {
            items(features.size) {
                FeatureItem(feature = features[it])
            }
        }
    }
}

@Composable
fun FeatureItem(
    feature: features
) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(15.dp)
                .aspectRatio(1f)
                .clip(RoundedCornerShape(10.dp))
                .background(md_theme_dark_inversePrimary)
        ) {
            Text(
                text = feature.title,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                lineHeight = 26.sp,
                modifier = Modifier.align(Alignment.TopStart)
                    .padding(15.dp)
            )
            Text(
                text = "Apply",
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .clickable {
                        // Handle the click
                    }
                    .align(Alignment.BottomEnd)
                    .padding(vertical = 6.dp, horizontal = 6.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(md_theme_dark_inverseSurface)
            )
        }
    }