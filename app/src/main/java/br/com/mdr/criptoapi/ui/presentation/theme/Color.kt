package br.com.mdr.criptoapi.ui.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val ShimmerLightGray = Color(0xFFF1F1F1)
val ShimmerMediumGray = Color(0xFFA0A0A0)
val ShimmerDarkGray = Color(0xFF1D1D1D)

val PrimaryColor = Color(0xFF18191A)
val SecondaryColor = Color(0xFFC56C54)
val TertiaryColor = Color(0xFF232325)

val ShimmerColor
    @Composable
    get() = if (isSystemInDarkTheme()) ShimmerDarkGray else ShimmerLightGray

val ShimmerMediumColor
    @Composable
    get() = if (isSystemInDarkTheme()) ShimmerDarkGray else ShimmerMediumGray
