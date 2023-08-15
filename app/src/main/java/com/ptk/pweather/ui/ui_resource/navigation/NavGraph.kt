package com.ptk.pweather.ui.ui_resource.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ptk.pweather.ui.screen.AstronomyScreen
import com.ptk.pweather.ui.screen.HomeScreen
import com.ptk.pweather.ui.screen.LoginScreen
import com.ptk.pweather.ui.screen.RegisterScreen
import com.ptk.pweather.ui.screen.SearchScreen
//import com.example.weatherapi.ui.screen.SearchScreen
import com.ptk.pweather.ui.screen.SplashScreen
import com.ptk.pweather.ui.screen.SportScreen

@Composable
fun NavGraph(
    navController: NavHostController,
) {
    NavHost(navController = navController, startDestination = Routes.HomeScreen.route) {
        composable(route = Routes.SplashScreen.route) {
            SplashScreen(navController)
        }
        composable(route = Routes.RegisterScreen.route) {
            RegisterScreen(navController)
        }
        composable(route = Routes.LoginScreen.route) {
            LoginScreen(navController)
        }
        composable(route = Routes.HomeScreen.route) {
            HomeScreen(navController)
        }
        composable(route = Routes.SearchScreen.route) {
            SearchScreen(navController)
        }
        composable(route = Routes.AstronomyScreen.route) {
            AstronomyScreen(navController)
        }
        composable(route = Routes.SportScreen.route) {
            SportScreen(navController)
        }

    }
}