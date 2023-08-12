package com.example.weatherapi.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.weatherapi.R
import com.example.weatherapi.ui.ui_resource.navigation.Routes
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
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painterResource(R.drawable.p_w_logo),
            "",
            modifier = modifier
                .height(300.dp)
                .width(300.dp)
        )
        LaunchedEffect(Unit) {
            delay(1500L)

            navController.navigate(Routes.HomeScreen.route) {
                navController.currentDestination?.let {
                    popUpTo(it.id) {
                        inclusive = true
                    }
                }
            }
        }
    }
}


