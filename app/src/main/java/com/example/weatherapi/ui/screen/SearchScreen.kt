package com.example.weatherapi.ui.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.weatherapi.model.dto.response.SearchResponseModel
import com.example.weatherapi.ui.ui_resource.composables.ListHeader
import com.example.weatherapi.ui.ui_resource.composables.ListItemText
import com.example.weatherapi.ui.ui_resource.composables.PWeatherButton
import com.example.weatherapi.ui.ui_resource.composables.PWeatherUserInput
import com.example.weatherapi.ui.ui_resource.theme.Blue
import com.example.weatherapi.ui.ui_resource.theme.LightGreen
import com.example.weatherapi.ui.ui_states.SearchUIStates
import com.example.weatherapi.viewmodel.SearchViewModel
import com.ptk.pWeather.R

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
        SearchScreenContent(it.calculateTopPadding().value, uiStates, searchViewModel)
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
            PWeatherUserInput()
            Spacer(modifier = Modifier.width(16.dp))
            PWeatherButton(
                modifier = Modifier
                    .height(55.dp),
                text = "Search",
                textColor = Color.White,
                buttonColor = ButtonDefaults.buttonColors(Blue)
            ) {
                searchViewModel.getSearchList("London")
            }
        }
        Divider(
            modifier = Modifier
                .height(4.dp)
                .padding(start = 16.dp, end = 16.dp), color = Blue
        )
        Surface(color = Color.White, modifier = Modifier.padding(16.dp)) {
            Column() {
                ListHeader("Name")
                SearchList(uiStates)
            }
        }
    }
}

@Composable
fun ColumnScope.SearchList(uiStates: SearchUIStates) {

    LazyColumn(
        modifier = Modifier.weight(1F),
        contentPadding = PaddingValues(top = 8.dp)
    ) {
        items(uiStates.searchList) { item ->
            SearchListItem(item)
            Spacer(modifier = Modifier.height(2.dp))
        }
    }
}

@Composable
fun SearchListItem(searchItem: SearchResponseModel) {
    Row(
        Modifier
            .fillMaxWidth()
            .background(Blue)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        ListItemText("${searchItem.name}")
        ListItemText("${searchItem.country}")
        Icon(
            painter = painterResource(id = R.drawable.baseline_info_24),
            contentDescription = "InfoIconListItem",
            tint = Color.White
        )
    }
}


