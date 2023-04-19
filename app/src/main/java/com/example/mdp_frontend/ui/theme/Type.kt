package com.example.mdp_frontend.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.unit.sp
import com.example.mdp_frontend.R

private val Montserrat = FontFamily(
        Font(R.font.montserrat_light, FontWeight.W300),
        Font(R.font.montserrat_bold, FontWeight.W700),
        Font(R.font.montserrat_extrabold, FontWeight.W800),
        Font(R.font.montserrat_regular, FontWeight.W400),
        Font(R.font.montserrat_semibold, FontWeight.W600),
        Font(R.font.montserrat_thin, FontWeight.W100),
        Font(R.font.montserrat_medium, FontWeight.W500)
)

// Set of Material typography styles to start with
val MontserratTypography = Typography(
       displayLarge = TextStyle(
               fontFamily = Montserrat,
               fontWeight = FontWeight.W700,
               fontSize = 30.sp
        ),
        displayMedium = TextStyle(
                fontFamily = Montserrat,
                fontWeight = FontWeight.W500,
                fontSize = 22.sp
        ),
        displaySmall = TextStyle(
                fontFamily = Montserrat,
                fontWeight = FontWeight.W400,
                fontSize = 16.sp
        ),

        /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)