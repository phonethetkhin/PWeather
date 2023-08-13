package com.ptk.pweather.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ptk.pweather.R
import com.ptk.pweather.ui.ui_resource.composables.PWeatherButton
import com.ptk.pweather.ui.ui_resource.composables.PWeatherUserInput
import com.ptk.pweather.ui.ui_resource.composables.PWeatherUserInputTrailing
import com.ptk.pweather.ui.ui_resource.theme.Blue
import com.ptk.pweather.ui.ui_resource.theme.LightGreen


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
    Surface(color = LightGreen) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.p_w_logo),
                contentDescription = "PWeatherLogo",
                modifier = Modifier
                    .padding(top = 16.dp)
                    .width(150.dp)
                    .height(150.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            PWeatherButton(
                text = "Sign In With Google",
                textColor = Color.Black,
                buttonColor = ButtonDefaults.buttonColors(Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {

            }
            
            Spacer(modifier = Modifier.height(32.dp))
            PWeatherUserInput(
                "Username",
                true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            PWeatherUserInputTrailing(
                "Password",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp)
            )
            Spacer(modifier = Modifier.height(32.dp))
            PWeatherButton(
                text = "Login",
                textColor = Color.White,
                buttonColor = ButtonDefaults.buttonColors(Blue),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp)
            ) {

            }
        }
    }
}
