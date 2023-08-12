package com.example.weatherapi.ui.ui_resource.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.weatherapi.ui.screen.AstronomyScreen
import com.example.weatherapi.ui.screen.HomeScreen
import com.example.weatherapi.ui.screen.LoginScreen
import com.example.weatherapi.ui.screen.SearchScreen
//import com.example.weatherapi.ui.screen.SearchScreen
import com.example.weatherapi.ui.screen.SplashScreen
import com.example.weatherapi.ui.screen.SportScreen

@Composable
fun NavGraph(
    navController: NavHostController,
) {
    NavHost(navController = navController, startDestination = Routes.HomeScreen.route) {
        composable(route = Routes.SplashScreen.route) {
            SplashScreen(navController)
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