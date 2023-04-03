package com.example.mdp_frontend.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mdp_frontend.R
import com.example.mdp_frontend.model.CategoryBoxItem
import com.example.mdp_frontend.ui.theme.md_theme_light_tertiaryContainer


@Composable
fun CategoryBox(categoryBoxItem: CategoryBoxItem,
                modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier
            .size(150.dp)
            .background(
                color = md_theme_light_tertiaryContainer,
                shape = RoundedCornerShape(20.dp)
            )
            .clip(RoundedCornerShape(20.dp))
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = categoryBoxItem.imageResId),
                contentDescription = null,
                modifier = Modifier.size(100.dp)
            )
            Text(
                text = categoryBoxItem.name,
                style = typography.titleSmall,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

@Composable
@Preview
fun BoxPreview(){
    CategoryBox(categoryBoxItem = CategoryBoxItem(
        name = "Category Name",
        imageResId = R.drawable.demo
    ) )
}