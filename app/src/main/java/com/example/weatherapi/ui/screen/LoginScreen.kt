package com.example.weatherapi.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.weatherapi.R
import com.example.weatherapi.ui.ui_resource.composables.PWeatherButton
import com.example.weatherapi.ui.ui_resource.composables.PWeatherUserInput


@Composable
fun LoginScreen(
    navController: NavController,
) {


    LoginScreenContent()


}


@Composable
fun LoginScreenContent(
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {

        Column(
            modifier = modifier.weight(1F),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Spacer(modifier = modifier.height(100.dp))
            Image(
                painterResource(R.drawable.ic_launcher_background),
                "",
                contentScale = ContentScale.FillBounds,
                modifier = modifier
                    .height(50.dp)
                    .width(150.dp)
            )
            Text("Material Tracking System", fontSize = 16.sp)
            Text("RFID Tagging", fontSize = 16.sp)

            Spacer(modifier = modifier.height(10.dp))
        }
        PWeatherUserInput(
            value = "asdfasd",
            placeholder = "SAP ID",
            onValueChange = {},
            modifier = modifier
                .height(60.dp)
                .fillMaxWidth()
                .padding(start = 40.dp, end = 40.dp)
        )
        PWeatherButton(
            text = "Log in",
            textColor = Color.White,
            buttonColor = ButtonDefaults.buttonColors(Color.Red),
            buttonClick = {
            }, modifier = modifier
                .fillMaxWidth()
                .padding(top = 8.dp, start = 40.dp, end = 40.dp)
        )
    }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painterResource(R.drawable.ic_launcher_background),
            "",
            contentScale = ContentScale.Fit,
        )
        Text("TAGGING-V1.4.0", fontSize = 16.sp)

    }
}

