package com.ptk.pweather.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.ptk.pweather.R
import com.ptk.pweather.ui.ui_resource.navigation.Routes
import com.ptk.pweather.ui.ui_resource.theme.LightGreen
import ir.kaaveh.sdpcompose.sdp
import kotlinx.coroutines.delay


@Composable
fun SplashScreen(
    navController: NavController
) {
    SplashScreenContent(navController)

}


@Composable
fun SplashScreenContent(
    navController: NavController,
    modifier: Modifier = Modifier,

    ) {
    Column(
        modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(LightGreen),

        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 64.sdp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Image(
                painterResource(R.drawable.p_w_logo),
                "",
                modifier = modifier
                    .height(150.sdp)
                    .width(150.sdp)
            )
            val composition by rememberLottieComposition(
                spec = LottieCompositionSpec.RawRes(resId = R.raw.weather_loading)
            )

            // render the animation
            LottieAnimation(
                modifier = Modifier.size(size = 150.sdp),
                composition = composition,
                iterations = LottieConstants.IterateForever // animate forever
            )


        }


        LaunchedEffect(Unit) {
            delay(2000L)

            navController.navigate(Routes.LoginScreen.route) {
                navController.currentDestination?.let {
                    popUpTo(it.id) {
                        inclusive = true
                    }
                }
            }
        }
    }
}


