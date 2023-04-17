package com.example.mdp_frontend.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.mdp_frontend.domain.model.Category
import com.example.mdp_frontend.ui.theme.md_theme_light_tertiaryContainer


@Composable
fun CategoryBox(category: Category,
                modifier: Modifier = Modifier,
                onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(150.dp)
            .background(
                color = md_theme_light_tertiaryContainer,
                shape = RoundedCornerShape(20.dp)
            )
            .clip(RoundedCornerShape(20.dp))
            .clickable(onClick = onClick)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AsyncImage(
                model = if (category.imageResId != null) painterResource(id = category.imageResId) else null,
                contentDescription = null,
                modifier = Modifier.size(100.dp)
            )
            Text(
                text = category.name,
                style = typography.titleSmall,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

/*
@Composable
@Preview
fun BoxPreview(){
    CategoryBox(categoryBoxItem = CategoryBoxItem(
        name = "Category Name",
        imageResId = R.drawable.demo
    ) )
}

 */