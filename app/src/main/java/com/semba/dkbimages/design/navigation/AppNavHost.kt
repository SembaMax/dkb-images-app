package com.semba.dkbimages.design.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.semba.dkbimages.feature.detailsceen.ui.detailScreen
import com.semba.dkbimages.feature.homescreen.ui.homeRoute
import com.semba.dkbimages.feature.homescreen.ui.homeScreen

@Composable
fun AppNavHost (
    navController: NavHostController,
    onBackClick: () -> Unit,
    navigateTo: (screenDestination: ScreenDestination, args: Map<String, String>) -> Unit,
    modifier: Modifier = Modifier,
    startDestination: String = homeRoute
)
{
    NavHost(navController = navController, startDestination = startDestination, modifier = modifier) {
        homeScreen(navigateTo)
        detailScreen()
    }
}