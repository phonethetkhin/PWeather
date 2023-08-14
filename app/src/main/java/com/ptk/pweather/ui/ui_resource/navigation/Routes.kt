package com.ptk.pweather.ui.ui_resource.navigation

sealed class Routes(val route: String) {
     object SplashScreen : Routes("/splash_screen")
     object RegisterScreen : Routes("/register_screen")
     object LoginScreen : Routes("/login_screen")
     object HomeScreen : Routes("/home_screen")
     object SearchScreen : Routes("/search_screen")
     object AstronomyScreen : Routes("/astronomy_screen")
     object SportScreen : Routes("/sport_screen")

}