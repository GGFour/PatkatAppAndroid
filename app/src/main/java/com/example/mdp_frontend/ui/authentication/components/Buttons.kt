package com.example.mdp_frontend.ui.authentication.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AuthGradientButton(
    text: String,
    onClick: () -> Unit,
    gradientColors: List<Color> = listOf(Color(0xFF484BF1), Color(0xFF673AB7)),
    cornerRadius: Dp = 16.dp,
    roundedCornerShape: RoundedCornerShape = RoundedCornerShape(topStart = 30.dp, bottomEnd = 30.dp),
) {

    Button(

        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 32.dp, end = 32.dp), onClick = onClick,

        contentPadding = PaddingValues(), colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent
        ), shape = RoundedCornerShape(cornerRadius)
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.horizontalGradient(colors = gradientColors),
                    shape = roundedCornerShape
                )
                .clip(roundedCornerShape)
                /*.background(
                    brush = Brush.linearGradient(colors = gradientColors),
                    shape = RoundedCornerShape(cornerRadius)
                )*/
                .padding(horizontal = 16.dp, vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text, fontSize = 20.sp, color = Color.White
            )
        }
    }
}

@Composable
fun AuthTextButton(text: String, onClick: () -> Unit) {
    TextButton(onClick = onClick) {
        Text(
            text = text,
            letterSpacing = 1.sp,
            style = MaterialTheme.typography.labelLarge
        )
    }
}
