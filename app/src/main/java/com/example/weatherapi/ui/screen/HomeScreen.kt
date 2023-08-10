package com.example.weatherapi.ui.screen

import androidx.compose.foundation.background
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
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.weatherapi.R
import com.example.weatherapi.ui.ui_resource.theme.Blue
import com.example.weatherapi.ui.ui_resource.theme.LightGreen

@Composable
fun HomeScreen(
    navController: NavController,
) {


    HomeScreenContent()


}

@Composable
fun HomeScreenContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = LightGreen)
            .padding(start = 80.dp, end = 80.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        HomeCardItem(R.drawable.search, "Home Search Card", "Search")
        HomeCardItem(R.drawable.astronomy, "Home Astronomy Card", "Astronomy")
        HomeCardItem(R.drawable.football, "Home Football Card", "Football")
    }
}


@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreenContent()
}


@Composable
fun HomeCardItem(resourceId: Int, contentDesc: String, text: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Blue, RoundedCornerShape(32.dp))
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