package com.semba.dkbimages.feature.homescreen.ui

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.semba.dkbimages.compose.navigation.ScreenDestination

const val homeRoute = "home_screen_route"

fun NavController.navigateToHomeScreen(navOptions: NavOptions? = null) {
    this.navigate(homeRoute, navOptions)
}

fun NavGraphBuilder.homeScreen(navigateTo: (screenDestination: ScreenDestination, args: Map<String, String>) -> Unit) {
    composable(route = homeRoute) {
        HomeScreen(navigateTo)
    }
}