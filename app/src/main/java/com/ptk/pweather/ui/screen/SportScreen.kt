package com.ptk.pweather.ui.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.ptk.pweather.R
import com.ptk.pweather.model.dto.response.SportResponseItem
import com.ptk.pweather.ui.ui_resource.composables.DetailItemText
import com.ptk.pweather.ui.ui_resource.composables.ListHeader
import com.ptk.pweather.ui.ui_resource.composables.ListItemText
import com.ptk.pweather.ui.ui_resource.composables.PWeatherButton
import com.ptk.pweather.ui.ui_resource.composables.PWeatherUserInput
import com.ptk.pweather.ui.ui_resource.theme.Blue
import com.ptk.pweather.ui.ui_resource.theme.LightGreen
import com.ptk.pweather.ui.ui_states.SportUIStates
import com.ptk.pweather.util.getConvertDate
import com.ptk.pweather.viewmodel.SportViewModel
import ir.kaaveh.sdpcompose.sdp
import ir.kaaveh.sdpcompose.ssp

//UIs
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SportScreen(
    navController: NavController,
    sportViewModel: SportViewModel = hiltViewModel()

) {
    val uiStates by sportViewModel.uiStates.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = LightGreen),
                title = {
                    Text(
                        "Search Tournament by City",
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.ssp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = null,
                            tint = Color.Black,
                            modifier = Modifier.size(24.sdp)
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
                    modifier = Modifier.size(240.sdp),
                    composition = composition,
                    iterations = LottieConstants.IterateForever // animate forever

                )
            }
        } else {
            SportScreenContent(it.calculateTopPadding().value, uiStates, sportViewModel)
        }
        if (uiStates.errorMessage.isNotEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 64.sdp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                val composition by rememberLottieComposition(
                    spec = LottieCompositionSpec.RawRes(resId = R.raw.error_lottie)
                )

                // render the animation
                LottieAnimation(
                    modifier = Modifier.size(size = 240.sdp),
                    composition = composition,
                )

                // render the animation

                Text("${uiStates.errorMessage}")
            }
        }
        if (uiStates.showAlertDialog) {
            SportFullScreenDialog(showDialog = uiStates.showAlertDialog, uiStates.clickedItem) {
                sportViewModel.showDetailDialog(false)
            }
        }
    }
}

@Composable
fun SportScreenContent(
    topMargin: Float,
    sportUIStates: SportUIStates,
    sportViewModel: SportViewModel
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(LightGreen)
            .padding(top = topMargin.dp)
    ) {
        item {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(16.sdp)
            ) {
                PWeatherUserInput(
                    sportUIStates.cityName,
                    sportUIStates.cityNameEmpty,
                    sportViewModel::toggleCityName
                )
                Spacer(modifier = Modifier.width(16.sdp))
                PWeatherButton(
                    modifier = Modifier
                        .padding(bottom = 8.sdp),
                    text = "Search",
                    textColor = Color.White,
                    buttonColor = ButtonDefaults.buttonColors(Blue)
                ) {
                    if (sportUIStates.cityName.isNotEmpty()) {
                        sportViewModel.getSportList(sportUIStates.cityName)
                    } else {
                        sportViewModel.cityNameEmpty()
                    }
                }
            }
        }
        item {
            Divider(
                modifier = Modifier
                    .height(4.sdp)
                    .padding(start = 16.sdp, end = 16.sdp), color = Blue
            )
        }
        if (sportUIStates.sportResponseModel != null) {

            if (sportUIStates.sportResponseModel.football.isNotEmpty()) {
                item {

                    Text(
                        "Football",
                        fontSize = 16.ssp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        textDecoration = TextDecoration.Underline,
                        modifier = Modifier
                            .padding(start = 16.sdp, top = 16.sdp, bottom = 16.sdp, end = 128.sdp)
                    )
                }

                item { ListHeader(titleName = "Tour Name") }
                item { Spacer(modifier = Modifier.height(8.sdp)) }
                items(sportUIStates.sportResponseModel!!.football) { item ->
                    SportListItem(item, sportViewModel)
                    Spacer(modifier = Modifier.height(4.sdp))
                }

            } else {
                item {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            "Football",
                            fontSize = 16.ssp,
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            textDecoration = TextDecoration.Underline,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.sdp)
                        )
                        Text(
                            "(There is no relevant data!)",
                            fontSize = 16.ssp,
                            color = Color.Red,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.sdp)
                        )
                    }
                }
            }

            if (sportUIStates.sportResponseModel.cricket.isNotEmpty()) {
                item {
                    Text(
                        "Cricket",
                        fontSize = 16.ssp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        textDecoration = TextDecoration.Underline,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.sdp)
                    )
                }
                item {
                    ListHeader(titleName = "Tour Name")
                }
                items(sportUIStates.sportResponseModel!!.cricket) { item ->
                    SportListItem(item, sportViewModel)
                    Spacer(modifier = Modifier.height(4.sdp))
                }
            } else {
                item {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            "Cricket",
                            fontSize = 16.ssp,
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            textDecoration = TextDecoration.Underline,
                            modifier = Modifier
                                .padding(16.sdp)
                        )
                        Text(
                            "(There is no relevant data!)",
                            fontSize = 16.ssp,
                            color = Color.Red,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .padding(top = 16.sdp)
                        )
                    }
                }
            }

            if (sportUIStates.sportResponseModel.golf.isNotEmpty()) {
                item {
                    Text(
                        "Golf",
                        fontSize = 16.ssp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        textDecoration = TextDecoration.Underline,

                        modifier = Modifier

                            .fillMaxWidth()
                            .padding(16.sdp)
                    )
                }
                item {
                    ListHeader(titleName = "Tour Name")
                }
                items(sportUIStates.sportResponseModel!!.golf) { item ->
                    SportListItem(item, sportViewModel)
                    Spacer(modifier = Modifier.height(4.sdp))
                }
            } else {
                item {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            "Golf",
                            fontSize = 16.ssp,
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            textDecoration = TextDecoration.Underline,

                            modifier = Modifier
                                .padding(16.sdp)
                        )
                        Spacer(modifier = Modifier.width(26.sdp))
                        Text(
                            "(There is no relevant data!)",
                            fontSize = 16.ssp,
                            color = Color.Red,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(top = 16.sdp, bottom = 16.sdp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SportListItem(item: SportResponseItem, sportViewModel: SportViewModel) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Blue,

            ), modifier = Modifier.padding(start = 16.sdp, end = 16.sdp)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .background(Blue)
                .padding(8.sdp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            ListItemText("${item.tournament}", modifier = Modifier.weight(1F).padding(start = 8.dp, end = 8.dp))
            ListItemText("${item.country}", modifier = Modifier.weight(1F).padding(start = 8.dp, end = 8.dp))
            Icon(
                painter = painterResource(id = R.drawable.baseline_info_24),
                contentDescription = "InfoIconListItem",
                tint = Color.White,
                modifier = Modifier
                    .size(24.sdp)
                    .clickable { sportViewModel.showDetailDialog(true, item) }

            )
        }
    }
}

@Composable
fun SportFullScreenDialog(
    showDialog: Boolean,
    clickedItem: SportResponseItem?,
    onClose: () -> Unit
) {
    if (showDialog) {
        Dialog(onDismissRequest = onClose) {
            clickedItem?.let {
                SportFullScreenDialogContent(clickedItem, onClose)
            }
        }
    }
}

@Composable
fun SportFullScreenDialogContent(clickedItem: SportResponseItem, onClose: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(LightGreen)
            .padding(top = 16.sdp, bottom = 16.sdp)
    ) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = Blue,

                ),
            modifier = Modifier
                .padding(start = 16.sdp, end = 16.sdp)
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically,

                    ) {
                    Card(
                        shape = RoundedCornerShape(50.sdp),
                        modifier = Modifier.padding(8.sdp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.Red,
                        )
                    ) {
                        Icon(
                            modifier = Modifier
                                .size(32.sdp)
                                .clickable {
                                    onClose.invoke()
                                },
                            painter = painterResource(id = R.drawable.baseline_close_24),
                            tint = Color.White,
                            contentDescription = "CloseIconDialog",
                        )
                    }

                }
                DetailItemText("Tournament", "${clickedItem.tournament}")
                DetailItemText("Stadium", "${clickedItem.stadium}")
                DetailItemText("Country", "${clickedItem.country}")
                DetailItemText(
                    "Date",
                    "${clickedItem.start?.getConvertDate("yyyy-MM-dd", "yyyy/MM/dd")}"
                )
                DetailItemText("Match", "${clickedItem.match}")
            }
        }
    }

}
//functions


