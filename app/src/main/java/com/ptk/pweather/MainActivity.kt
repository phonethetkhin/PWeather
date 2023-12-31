package com.ptk.pweather

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.ptk.pweather.ui.ui_resource.navigation.NavGraph
import com.ptk.pweather.ui.ui_resource.theme.WeatherAPITheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherAPITheme {
                MainComposable()
            }
        }
    }

    @Composable
    fun MainComposable() {
        val navController = rememberNavController()

        NavGraph(
            navController
        )
    }
}
