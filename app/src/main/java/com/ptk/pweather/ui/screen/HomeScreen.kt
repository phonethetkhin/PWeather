package com.ptk.pweather.ui.screen

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.ptk.pweather.R
import com.ptk.pweather.ui.ui_resource.navigation.Routes
import com.ptk.pweather.ui.ui_resource.theme.Blue
import com.ptk.pweather.ui.ui_resource.theme.LightGreen
import com.ptk.pweather.viewmodel.UserViewModel
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp


//UIs
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    userViewModel: UserViewModel = hiltViewModel()
) {
    val uiStates by userViewModel.uiStates.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Blue),
                title = {
                    Text(
                        "Home",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.ssp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 50.sdp),
                        textAlign = TextAlign.Center
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        userViewModel.showAlertDialog(true)

                    }, modifier = Modifier.padding(start = 8.sdp)) {
                        Icon(
                            imageVector = Icons.Filled.Logout,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(24.sdp)
                        )
                    }
                }
            )
        }
    ) {
        HomeScreenContent(navController, it.calculateTopPadding().value)
        if (uiStates.showAlertDialog) {
            ShowAlertDialog(userViewModel, navController)
        }
    }

}

@Composable
fun HomeScreenContent(navController: NavController, topMargin: Float) {
    val configuration = LocalConfiguration.current
    when (configuration.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = LightGreen)
                    .padding(top = topMargin.dp),
            ) {
                HomeCardItem(R.drawable.search, "Home Search Card", "Search", true) {
                    navigateToOtherScreens(navController, Routes.SearchScreen.route)
                }
                HomeCardItem(R.drawable.astronomy, "Home Astronomy Card", "Astronomy", true) {
                    navigateToOtherScreens(navController, Routes.AstronomyScreen.route)

                }
                HomeCardItem(R.drawable.football, "Home Football Card", "Sport", true) {
                    navigateToOtherScreens(navController, Routes.SportScreen.route)

                }
            }
        }

        else -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = LightGreen)
                    .padding(start = 80.sdp, end = 80.sdp, top = topMargin.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                HomeCardItem(R.drawable.search, "Home Search Card", "Search") {
                    navigateToOtherScreens(navController, Routes.SearchScreen.route)
                }
                HomeCardItem(R.drawable.astronomy, "Home Astronomy Card", "Astronomy") {
                    navigateToOtherScreens(navController, Routes.AstronomyScreen.route)

                }
                HomeCardItem(R.drawable.football, "Home Football Card", "Sport") {
                    navigateToOtherScreens(navController, Routes.SportScreen.route)

                }
            }
        }
    }

}


@Composable
fun HomeCardItem(
    resourceId: Int,
    contentDesc: String,
    text: String,
    landscape: Boolean = false,
    func: () -> Unit
) {
    Box(
        modifier = if (landscape)
            Modifier
                .background(Blue, RoundedCornerShape(32.sdp))
                .clip(RoundedCornerShape(32.dp))
                .clickable(onClick = func
                )

        else Modifier
            .fillMaxWidth()
            .background(Blue, RoundedCornerShape(32.sdp))
            .clip(RoundedCornerShape(32.dp))
            .clickable(onClick = func)
    ) {
        Column(
            modifier = if (landscape)
                Modifier
                    .padding(32.sdp)
            else
                Modifier
                    .fillMaxWidth()
                    .padding(16.sdp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(id = resourceId),
                contentDescription = contentDesc,
                modifier = Modifier.size(64.sdp),
                colorResource(id = R.color.white)
            )

            Spacer(modifier = Modifier.height(8.sdp))

            Text(
                text = text,
                fontSize = 16.ssp, color = Color.White
            )
        }
    }
}

@Composable
fun ShowAlertDialog(userViewModel: UserViewModel, navController: NavController) {
    Dialog(onDismissRequest = { userViewModel.showAlertDialog(false) }) {
        AlertDialogContent(userViewModel = userViewModel, navController)
    }
}

//Layout
@Composable
fun AlertDialogContent(
    userViewModel: UserViewModel,
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    Card(
        //shape = MaterialTheme.shapes.medium,
        shape = RoundedCornerShape(16.sdp),
        // modifier = modifier.size(280.dp, 240.dp)
        modifier = Modifier.padding(10.sdp, 5.sdp, 10.sdp, 10.sdp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.sdp
        )
    ) {
        Column(
            modifier
                .background(Color.White)
        ) {

            //.......................................................................
            Image(
                imageVector = Icons.Filled.Warning,
                contentDescription = null, // decorative
                contentScale = ContentScale.Fit,
                colorFilter = ColorFilter.tint(
                    color = Blue
                ),
                modifier = Modifier
                    .padding(top = 35.sdp)
                    .height(70.sdp)
                    .fillMaxWidth(),

                )



            Column(modifier = Modifier.padding(16.sdp)) {
                Text(
                    text = "Are you sure you want to logout?",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top = 5.sdp)
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.titleLarge,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
            //.......................................................................
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 10.sdp)
                    .background(Blue),
                horizontalArrangement = Arrangement.SpaceAround
            ) {

                androidx.compose.material3.TextButton(onClick = {
                    userViewModel.showAlertDialog(false)
                }) {

                    Text(
                        "Cancel",
                        fontSize = 12.ssp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier.padding(top = 5.sdp, bottom = 5.sdp)
                    )
                }
                val context = LocalContext.current
                androidx.compose.material3.TextButton(onClick = {
                    GoogleSignIn.getClient(context, GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .signOut()
                    userViewModel.showAlertDialog(false)
                    navigateToOtherScreens(navController = navController, Routes.LoginScreen.route)
                }) {
                    Text(
                        "Logout",
                        fontSize = 12.ssp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White,
                        modifier = Modifier.padding(top = 5.sdp, bottom = 5.sdp)
                    )
                }
            }
        }
    }
}

// functions
fun navigateToOtherScreens(navController: NavController, routeName: String) {
    navController.navigate(routeName)
}

