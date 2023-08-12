package com.example.weatherapi.ui.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.weatherapi.R
import com.example.weatherapi.model.dto.response.SearchResponseModel
import com.example.weatherapi.ui.ui_resource.composables.DetailItemText
import com.example.weatherapi.ui.ui_resource.composables.ListHeader
import com.example.weatherapi.ui.ui_resource.composables.ListItemText
import com.example.weatherapi.ui.ui_resource.composables.PWeatherButton
import com.example.weatherapi.ui.ui_resource.composables.PWeatherUserInput
import com.example.weatherapi.ui.ui_resource.theme.Blue
import com.example.weatherapi.ui.ui_resource.theme.LightGreen
import com.example.weatherapi.ui.ui_states.SearchUIStates
import com.example.weatherapi.viewmodel.SearchViewModel

//UIs
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SearchScreen(
    navController: NavController,
    searchViewModel: SearchViewModel = hiltViewModel()
) {
    val uiStates by searchViewModel.uiStates.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = LightGreen),
                title = {
                    Text(
                        "Search City", color = Color.Black, fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = null,
                            tint = Color.Black
                        )
                    }
                },
            )
        }
    ) {
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
        } else {
            SearchScreenContent(it.calculateTopPadding().value, uiStates, searchViewModel)
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
        if (uiStates.showAlertDialog) {
            SearchFullScreenDialog(showDialog = uiStates.showAlertDialog, uiStates.clickedItem) {
                searchViewModel.showDetailDialog(false)
            }
        }
    }
}

@Composable
fun SearchScreenContent(
    topMargin: Float,
    uiStates: SearchUIStates,
    searchViewModel: SearchViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LightGreen)
            .padding(top = topMargin.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(16.dp)) {
            PWeatherUserInput(
                uiStates.cityName,
                uiStates.cityNameEmpty,
                searchViewModel::toggleCityName
            )
            Spacer(modifier = Modifier.width(16.dp))
            PWeatherButton(
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .height(55.dp),
                text = "Search",
                textColor = Color.White,
                buttonColor = ButtonDefaults.buttonColors(Blue)
            ) {
                if (uiStates.cityName.isNotEmpty()) {
                    searchViewModel.getSearchList(uiStates.cityName)
                } else {
                    searchViewModel.cityNameEmpty()
                }

            }

        }
        Divider(
            modifier = Modifier
                .height(4.dp)
                .padding(start = 16.dp, end = 16.dp), color = Blue
        )

        Spacer(modifier = Modifier.height(8.dp))
        if (uiStates.searchList.isNotEmpty()) {
            ListHeader("Name")
            SearchList(uiStates, searchViewModel)
        }
    }


}

@Composable
fun ColumnScope.SearchList(uiStates: SearchUIStates, searchViewModel: SearchViewModel) {

    LazyColumn(
        modifier = Modifier.weight(1F),
        contentPadding = PaddingValues(top = 8.dp, start = 16.dp, end = 16.dp)
    ) {
        items(uiStates.searchList) { item ->
            SearchListItem(item, searchViewModel)
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}

@Composable
fun SearchListItem(searchItem: SearchResponseModel, searchViewModel: SearchViewModel) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Blue,

            ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            ListItemText("${searchItem.name}", modifier = Modifier.weight(1F))
            ListItemText("${searchItem.country}", modifier = Modifier.weight(1F))
            Icon(
                painter = painterResource(id = R.drawable.baseline_info_24),
                contentDescription = "InfoIconListItem",
                tint = Color.White,
                modifier = Modifier.clickable { searchViewModel.showDetailDialog(true, searchItem) }
            )
        }
    }
}

@Composable
fun SearchFullScreenDialog(
    showDialog: Boolean,
    clickedItem: SearchResponseModel?,
    onClose: () -> Unit
) {
    if (showDialog) {
        Dialog(onDismissRequest = onClose) {
            clickedItem?.let {
                SearchFullScreenDialogContent(clickedItem, onClose)
            }
        }
    }
}

@Composable
fun SearchFullScreenDialogContent(clickedItem: SearchResponseModel, onClose: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LightGreen)
            .padding(top = 16.dp, bottom = 16.dp)
    ) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = Blue,

                ),
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp)
                .weight(1F)
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically,

                    ) {
                    Card(
                        shape = RoundedCornerShape(50.dp),
                        modifier = Modifier.padding(8.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.Red,
                        )
                    ) {
                        Icon(
                            modifier = Modifier
                                .size(32.dp)
                                .clickable {
                                    onClose.invoke()
                                },
                            painter = painterResource(id = R.drawable.baseline_close_24),
                            tint = Color.White,
                            contentDescription = "CloseIconDialog",
                        )
                    }

                }
                DetailItemText("Name", "${clickedItem.name}")
                DetailItemText("Region", "${clickedItem.region}")
                DetailItemText("Country", "${clickedItem.country}")
                DetailItemText("Latitude", "${clickedItem.lat}")
                DetailItemText("Longitude", "${clickedItem.lon}")
            }
        }
    }

}



