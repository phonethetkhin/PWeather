package com.ptk.pweather.ui.screen

import android.util.Log
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.ptk.pweather.R
import com.ptk.pweather.ui.ui_resource.composables.PWeatherButton
import com.ptk.pweather.ui.ui_resource.composables.PWeatherUserInput
import com.ptk.pweather.ui.ui_resource.composables.PWeatherUserInputTrailing
import com.ptk.pweather.ui.ui_resource.navigation.Routes
import com.ptk.pweather.ui.ui_resource.theme.Blue
import com.ptk.pweather.ui.ui_resource.theme.LightGreen
import com.ptk.pweather.ui.ui_states.UserUIStates
import com.ptk.pweather.viewmodel.UserViewModel


@Composable
fun RegisterScreen(
    navController: NavController,
    userViewModel: UserViewModel = hiltViewModel()
) {

    val uiStates by userViewModel.uiStates.collectAsState()
    //Conditions depend upon states changes
    if (uiStates.showLoadingDialog) {

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            val composition by rememberLottieComposition(
                spec = LottieCompositionSpec.RawRes(resId = R.raw.loading_lottie)
            )

            // render the animation
            LottieAnimation(
                modifier = Modifier.size(240.dp),
                composition = composition,
                iterations = LottieConstants.IterateForever // animate forever

            )
        }
    } else if (uiStates.registerSuccess) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Register Successfully", fontSize = 32.sp, color = Color.Black)
            val composition by rememberLottieComposition(
                spec = LottieCompositionSpec.RawRes(resId = R.raw.login_success_lottie)
            )

            val progress by animateLottieCompositionAsState(composition)

            // render the animation
            LottieAnimation(
                modifier = Modifier.size(240.dp),
                composition = composition,

                )
            if (progress == 1.0f) {
                // Animation completes.
                navigateToOtherScreens(navController, Routes.HomeScreen.route)
            }
        }
    } else {
        RegisterScreenContent(uiStates, userViewModel, navController)
    }
    if (uiStates.errorMessage.isNotEmpty()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 64.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            val composition by rememberLottieComposition(
                spec = LottieCompositionSpec.RawRes(resId = R.raw.error_lottie)
            )

            // render the animation
            LottieAnimation(
                modifier = Modifier.size(size = 240.dp),
                composition = composition,
            )

            // render the animation

            Text("${uiStates.errorMessage}")
        }
    }

}


@Composable
fun RegisterScreenContent(
    uiStates: UserUIStates,
    viewModel: UserViewModel,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Surface(color = LightGreen) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(top = 16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.p_w_logo),
                contentDescription = "PWeatherLogo",
                modifier = Modifier
                    .width(150.dp)
                    .height(150.dp)
            )
            Spacer(modifier = Modifier.height(32.dp))

            PWeatherUserInput(
                "Username",
                uiStates.userName,
                uiStates.userNameEmpty,
                uiStates.userNameNotExist,
                viewModel::toggleUserName,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            PWeatherUserInputTrailing(
                "Password",
                uiStates.password,
                uiStates.passwordEmpty,
                uiStates.passwordLengthShort,
                false,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp),
                viewModel::togglePassword
            )
            Spacer(modifier = Modifier.height(8.dp))

            PWeatherUserInputTrailing(
                "Confirm Password",
                uiStates.confirmPassword,
                uiStates.confirmPasswordEmpty,
                false,
                uiStates.passCPassNotSame,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp),
                viewModel::toggleConfirmPassword
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp, end = 32.dp)
            ) {
                Spacer(modifier = Modifier.weight(1F))
                Text("Already have an account?", color = Color.Black, fontSize = 12.sp)
                Text(
                    " Login",
                    color = Blue,
                    fontSize = 12.sp,
                    modifier = Modifier.clickable {
                        navigateToOtherScreens(
                            navController = navController,
                            Routes.LoginScreen.route
                        )
                    })

            }
            Spacer(modifier = Modifier.height(32.dp))
            PWeatherButton(
                text = "Register",
                textColor = Color.White,
                buttonColor = ButtonDefaults.buttonColors(Blue),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 16.dp)
            ) {
                if (uiStates.userName.isEmpty()) {
                    viewModel.userNameEmpty()
                } else if (uiStates.password.isEmpty()) {
                    viewModel.passwordEmpty()
                } else if (uiStates.password.length < 5) {
                    viewModel.passwordLengthShort()
                } else if (uiStates.confirmPassword.isEmpty()) {
                    viewModel.confirmPasswordEmpty()
                } else if (uiStates.password != uiStates.confirmPassword) {
                    viewModel.passCPassNotSame()
                } else {
                    viewModel.checkUserNameExist(uiStates.userName, uiStates.password)
                }
            }
        }
    }
}

//functions


