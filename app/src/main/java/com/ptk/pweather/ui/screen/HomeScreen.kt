package com.ptk.pweather.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ptk.pweather.R
import com.ptk.pweather.ui.ui_resource.navigation.Routes
import com.ptk.pweather.ui.ui_resource.theme.Blue
import com.ptk.pweather.ui.ui_resource.theme.LightGreen

//UIs
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
) {

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
            )
        }
    ) {
        HomeScreenContent(navController, it.calculateTopPadding().value)
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

// functions
fun navigateToOtherScreens(navController: NavController, routeName: String) {
    navController.navigate(routeName)
}