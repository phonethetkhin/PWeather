package com.ptk.pweather.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ptk.pweather.R
import com.ptk.pweather.ui.ui_resource.navigation.Routes
import com.ptk.pweather.ui.ui_resource.theme.Blue
import com.ptk.pweather.ui.ui_resource.theme.LightGreen
import com.ptk.pweather.viewmodel.UserViewModel

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
                        fontSize = 20.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 16.dp),
                        textAlign = TextAlign.Center
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        userViewModel.showAlertDialog(true)

                    }) {
                        Icon(
                            imageVector = Icons.Default.Logout,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier
                                .size(64.dp)
                                .padding(start = 16.dp)
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
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = LightGreen)
            .padding(start = 80.dp, end = 80.dp, top = topMargin.dp),
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


@Composable
fun HomeCardItem(resourceId: Int, contentDesc: String, text: String, func: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Blue, RoundedCornerShape(32.dp))
            .clickable(onClick = func)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(id = resourceId),
                contentDescription = contentDesc,
                modifier = Modifier.size(64.dp),
                colorResource(id = R.color.white)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = text,
                fontSize = 16.sp, color = Color.White
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
        shape = RoundedCornerShape(16.dp),
        // modifier = modifier.size(280.dp, 240.dp)
        modifier = Modifier.padding(10.dp, 5.dp, 10.dp, 10.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
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
                    .padding(top = 35.dp)
                    .height(70.dp)
                    .fillMaxWidth(),

                )

            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Are you sure you want to logout?",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top = 5.dp)
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
                    .padding(top = 10.dp)
                    .background(Blue),
                horizontalArrangement = Arrangement.SpaceAround
            ) {

                androidx.compose.material3.TextButton(onClick = {
                    userViewModel.showAlertDialog(false)
                }) {

                    Text(
                        "Cancel",
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                    )
                }
                androidx.compose.material3.TextButton(onClick = {
                    userViewModel.showAlertDialog(false)
                    navigateToOtherScreens(navController = navController, Routes.LoginScreen.route)
                }) {
                    Text(
                        "Logout",
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White,
                        modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
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

