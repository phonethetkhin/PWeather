package com.ptk.pweather.ui.screen

import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.ptk.pweather.R
import com.ptk.pweather.ui.ui_resource.composables.GoogleSignInButton
import com.ptk.pweather.ui.ui_resource.composables.PWeatherButton
import com.ptk.pweather.ui.ui_resource.composables.PWeatherUserInput
import com.ptk.pweather.ui.ui_resource.composables.PWeatherUserInputTrailing
import com.ptk.pweather.ui.ui_resource.navigation.Routes
import com.ptk.pweather.ui.ui_resource.theme.Blue
import com.ptk.pweather.ui.ui_resource.theme.LightGreen
import com.ptk.pweather.ui.ui_states.UserUIStates
import com.ptk.pweather.util.AuthResultContract
import com.ptk.pweather.viewmodel.UserViewModel
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

lateinit var authResultLauncher: ManagedActivityResultLauncher<Int, Task<GoogleSignInAccount>?>
const val signInRequestCode = 1

@Composable
fun LoginScreen(
    navController: NavController,
    userViewModel: UserViewModel = hiltViewModel()
) {

    val uiStates by userViewModel.uiStates.collectAsState()


    if (uiStates.showLoadingDialog) {

        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            val composition by rememberLottieComposition(
                spec = LottieCompositionSpec.RawRes(resId = R.raw.loading_lottie)
            )

            // render the animation
            LottieAnimation(
                modifier = Modifier.size(150.sdp),
                composition = composition,
                iterations = LottieConstants.IterateForever // animate forever

            )
        }
    } else if (uiStates.loginSuccess) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Login Successfully", fontSize = 24.ssp, color = Color.Black)
            val composition by rememberLottieComposition(
                spec = LottieCompositionSpec.RawRes(resId = R.raw.login_success_lottie)
            )

            val progress by animateLottieCompositionAsState(composition)

            // render the animation
            LottieAnimation(
                modifier = Modifier.size(150.sdp),
                composition = composition,

                )
            if (progress == 1.0f) {
                // Animation completes.
                navigateToHome(navController)
            }
        }
    } else if (uiStates.errorMessage.isNotEmpty()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 64.sdp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            val composition by rememberLottieComposition(
                spec = LottieCompositionSpec.RawRes(resId = R.raw.locked_lottie)
            )
            val progress by animateLottieCompositionAsState(composition)

            // render the animation
            LottieAnimation(
                modifier = Modifier.size(size = 150.sdp),
                composition = composition,
            )
            if (progress == 1.0f) {
                userViewModel.setErrorMessage("")
            }

            // render the animation

            Text("${uiStates.errorMessage}", color = Color.Red, fontSize = 16.ssp)
        }
    } else if (uiStates.ggSignInErrMsg.isNotEmpty()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 64.sdp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            val composition by rememberLottieComposition(
                spec = LottieCompositionSpec.RawRes(resId = R.raw.locked_lottie)
            )
            val progress by animateLottieCompositionAsState(composition)

            // render the animation
            LottieAnimation(
                modifier = Modifier.size(size = 150.sdp),
                composition = composition,
            )
            if (progress == 1.0f) {
                userViewModel.setGGSignInErrorMessage("")
            }

            // render the animation

            Text("${uiStates.ggSignInErrMsg}", color = Color.Red, fontSize = 16.ssp)
        }
    } else if (uiStates.ggSignInSuccess) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Login Successfully", fontSize = 24.ssp, color = Color.Black)
            val composition by rememberLottieComposition(
                spec = LottieCompositionSpec.RawRes(resId = R.raw.login_success_lottie)
            )

            val progress by animateLottieCompositionAsState(composition)

            // render the animation
            LottieAnimation(
                modifier = Modifier.size(150.sdp),
                composition = composition,

                )
            if (progress == 1.0f) {
                // Animation completes.
                navigateToHome(navController)
            }
        }
    } else {
        LoginScreenContent(uiStates, userViewModel, navController)
    }




    authResultLauncher =
        rememberLauncherForActivityResult(contract = AuthResultContract()) { task ->
            try {
                val account = task?.getResult(ApiException::class.java)
                if (account == null) {
                    userViewModel.setGGSignInErrorMessage("Google Sign In Failed, Account is Null")
                } else {
                    userViewModel.setGGSignInSuccess(true)
                }
            } catch (e: ApiException) {
                userViewModel.setGGSignInErrorMessage("Google Sign In Failed, $e")

            }
        }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreenContent(
    uiStates: UserUIStates,
    userViewModel: UserViewModel,
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    Surface(color = LightGreen) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.p_w_logo),
                contentDescription = "PWeatherLogo",
                modifier = Modifier
                    .padding(top = 16.sdp)
                    .width(150.sdp)
                    .height(150.sdp)
            )
            Spacer(modifier = Modifier.height(16.sdp))
            GoogleSignInButton() {
                authResultLauncher.launch(signInRequestCode)
            }

            Spacer(modifier = Modifier.height(32.sdp))
            PWeatherUserInput(
                "Username",
                uiStates.userName,
                uiStates.userNameEmpty,
                uiStates.userNameNotExist,
                userViewModel::toggleUserName,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.sdp, end = 16.sdp)
            )
            Spacer(modifier = Modifier.height(8.sdp))
            PWeatherUserInputTrailing(
                "Password",
                uiStates.password,
                uiStates.passwordEmpty,
                uiStates.passwordLengthShort,
                false,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.sdp, end = 16.sdp),
                userViewModel::togglePassword
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.sdp, end = 32.sdp)
            ) {
                Spacer(modifier = Modifier.weight(1F))
                Text("Don't have an account yet?", color = Color.Black, fontSize = 12.ssp)
                Text(
                    " Register",
                    color = Blue,
                    fontSize = 12.ssp,
                    modifier = Modifier.clickable {
                        navigateToRegister(navController)
                    })

            }
            Spacer(modifier = Modifier.height(32.sdp))
            PWeatherButton(
                text = "Login",
                textColor = Color.White,
                buttonColor = ButtonDefaults.buttonColors(Blue),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.sdp, start = 16.sdp, end = 16.sdp, bottom = 16.sdp)
            ) {
                if (uiStates.userName.isEmpty()) {
                    userViewModel.userNameEmpty()
                } else if (uiStates.password.isEmpty()) {
                    userViewModel.passwordEmpty()
                } else {
                    userViewModel.login(uiStates.userName, uiStates.password)
                }
            }
        }
    }
}

//functions

fun navigateToHome(navController: NavController) {
    navController.navigate(Routes.HomeScreen.route) {
        navController.currentDestination?.let {
            popUpTo(it.id) {
                inclusive = true
            }
        }
    }

}

fun navigateToRegister(navController: NavController) {
    navController.navigate(Routes.RegisterScreen.route) {
        navController.currentDestination?.let {
            popUpTo(it.id) {
                inclusive = true
            }
        }
    }
}



