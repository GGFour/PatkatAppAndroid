package com.example.mdp_frontend.ui.screen

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.runtime.*
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mdp_frontend.R
import kotlinx.coroutines.delay
import com.example.mdp_frontend.ui.theme.md_theme_dark_background
import com.example.mdp_frontend.ui.theme.md_theme_light_background

@Composable
fun Splash(onAnimationFinish: () -> Unit) {
    var startAnimation by remember { mutableStateOf(false) }
    val alphaAnim = animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(
            durationMillis = 1500
        )
    )

    LaunchedEffect(key1 = true) {
        startAnimation = true
        delay(1500)
        onAnimationFinish()
    }
    SplashScreen(alpha = alphaAnim.value)
}

@Composable
fun SplashScreen(alpha: Float) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Box(modifier = Modifier
//            .background(if (isSystemInDarkTheme()) md_theme_dark_background else md_theme_light_background)
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                modifier = Modifier
                    .width(192.dp)
                    .height(54.dp)
                    .alpha(alpha = alpha),
                contentDescription = "Logo"
            )
        }
    }
}


@Composable
@Preview
fun SplashScreenPreview(){
   SplashScreen(alpha = 1f)
}




