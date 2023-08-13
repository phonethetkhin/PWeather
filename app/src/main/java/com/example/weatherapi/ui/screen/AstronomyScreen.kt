package com.example.weatherapi.ui.screen

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.widget.DatePicker
import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.weatherapi.R
import com.example.weatherapi.ui.ui_resource.composables.DetailItemText
import com.example.weatherapi.ui.ui_resource.composables.PWeatherButton
import com.example.weatherapi.ui.ui_resource.composables.PWeatherUserInput
import com.example.weatherapi.ui.ui_resource.composables.PWeatherUserInputTrailing
import com.example.weatherapi.ui.ui_resource.theme.Blue
import com.example.weatherapi.ui.ui_resource.theme.LightGreen
import com.example.weatherapi.ui.ui_states.AstronomyUIStates
import com.example.weatherapi.util.Constants
import com.example.weatherapi.util.calculateDistanceFromYangon
import com.example.weatherapi.util.calculateTimeDifference
import com.example.weatherapi.util.getConvertDate
import com.example.weatherapi.util.notNullString
import com.example.weatherapi.viewmodel.AstronomyViewModel
import java.util.Calendar
import java.util.Date

lateinit var mDatePickerDialog: DatePickerDialog

//UIs
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AstronomyScreen(
    navController: NavController,
    astronomyViewModel: AstronomyViewModel = hiltViewModel()

) {
    val uiStates by astronomyViewModel.uiStates.collectAsState()

    val context = LocalContext.current
    LaunchedEffect(key1 = "") {
        initializeDatePicker(context, uiStates, astronomyViewModel)
    }
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = LightGreen),
                title = {
                    Text(
                        "Astronomy", color = Color.Black, fontWeight = FontWeight.Bold,
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
            AstronomyScreenContent(it.calculateTopPadding().value, uiStates, astronomyViewModel)
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
}

@Composable
fun AstronomyScreenContent(
    topMargin: Float,
    uiStates: AstronomyUIStates,
    astronomyViewModel: AstronomyViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(LightGreen)
            .padding(top = topMargin.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(top = 16.dp, end = 16.dp, start = 16.dp)
        ) {
            PWeatherUserInput(
                uiStates.cityName,
                uiStates.cityNameEmpty,
                astronomyViewModel::toggleCityName
            )
            Spacer(modifier = Modifier.width(16.dp))
            PWeatherUserInputTrailing(
                uiStates.dateName,
                uiStates.dateEmpty,
            ) {
                mDatePickerDialog.show()
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        PWeatherButton(
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
                .padding(start = 16.dp, end = 16.dp),
            text = "Search",
            textColor = Color.White,
            buttonColor = ButtonDefaults.buttonColors(Blue)
        ) {
            if (uiStates.cityName.isEmpty()) {
                astronomyViewModel.cityNameEmpty()
            } else {
                if (uiStates.dateName.isEmpty()) {
                    astronomyViewModel.dateNameEmpty()
                } else {
                    astronomyViewModel.getAstronomy(uiStates.cityName, uiStates.dateName)
                }
            }

        }
        Spacer(modifier = Modifier.height(16.dp))
        Divider(
            modifier = Modifier
                .height(4.dp)
                .padding(start = 16.dp, end = 16.dp), color = Blue
        )
        Spacer(modifier = Modifier.height(16.dp))

        if (uiStates.astronomyResponse != null) {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = Blue,

                    ),
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp)
            ) {
                uiStates.astronomyResponse.location?.let {
                    val distanceFromYangon = calculateDistanceFromYangon(
                        Constants.YANGON_LAT,
                        Constants.YANGON_LON,
                        uiStates.astronomyResponse.location.lat!!,
                        uiStates.astronomyResponse.location.lon!!
                    )
                    DetailItemText(
                        "Region",
                        uiStates.astronomyResponse.location.region.notNullString()
                    )
                    DetailItemText(
                        "Country",
                        uiStates.astronomyResponse.location.country.notNullString()
                    )
                    DetailItemText(
                        "Distance",
                        if (distanceFromYangon != null) "$distanceFromYangon meters" else "-"
                    )
                    DetailItemText(
                        "LocalTime",
                        uiStates.astronomyResponse.location.localtime?.getConvertDate(
                            "yyyy-MM-dd HH:mm",
                            "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
                        ).notNullString()

                    )
                    // 2023-08-13 15:54
                    //yyyy-MM-dd
                }
                uiStates.astronomyResponse.astronomy?.astro?.let {

                    val timeDiffSunRiseMoonRise =
                        calculateTimeDifference(it.sunrise!!, it.moonrise!!).notNullString()
                    val timeDiffSunSetMoonSet =
                        calculateTimeDifference(it.sunset!!, it.moonset!!).notNullString()

                    DetailItemText(
                        "Sunrise",
                        uiStates.astronomyResponse.astronomy.astro.sunrise.notNullString()
                    )
                    DetailItemText(
                        "Sunset",
                        uiStates.astronomyResponse.astronomy.astro.sunset.notNullString()
                    )
                    DetailItemText(
                        "Moonrise",
                        uiStates.astronomyResponse.astronomy.astro.moonrise.notNullString()
                    )
                    DetailItemText(
                        "Moonset",
                        uiStates.astronomyResponse.astronomy.astro.moonset.notNullString()
                    )

                    DetailItemText(
                        "Sunrise & Moonrise Diff",
                        timeDiffSunRiseMoonRise
                    )
                    DetailItemText(
                        "Sunset & Moonset Diff",
                        timeDiffSunSetMoonSet
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

    }
}


//functions
private fun initializeDatePicker(
    context: Context,
    uiStates: AstronomyUIStates,
    astronomyViewModel: AstronomyViewModel
) {
    val mYear: Int
    val mMonth: Int
    val mDay: Int

    // Initializing a Calendar
    val mCalendar = Calendar.getInstance()

    // Fetching current year, month and day
    mYear = mCalendar.get(Calendar.YEAR)
    mMonth = mCalendar.get(Calendar.MONTH)
    mDay = mCalendar.get(Calendar.DAY_OF_MONTH)

    mCalendar.time = Date()

    // Declaring a string value to
    // store date in string format

    // Declaring DatePickerDialog and setting
    // initial values as current values (present year, month and day)
    mDatePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            astronomyViewModel.setDate("$mDayOfMonth/${mMonth + 1}/$mYear")
        }, mYear, mMonth, mDay
    )

}



