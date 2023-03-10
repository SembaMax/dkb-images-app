package com.semba.dkbimages.main

import androidx.compose.runtime.Composable
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navOptions
import com.semba.dkbimages.design.navigation.ScreenDestination
import com.semba.dkbimages.core.NetworkMonitor
import com.semba.dkbimages.feature.detailsceen.ui.detailRoute
import com.semba.dkbimages.feature.detailsceen.ui.navigateToDetailScreen
import com.semba.dkbimages.feature.homescreen.ui.homeRoute
import com.semba.dkbimages.feature.homescreen.ui.navigateToHomeScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class AppState(
    val navController: NavHostController,
    coroutineScope: CoroutineScope,
    networkMonitor: NetworkMonitor
) {
    val currentNavDestination: NavDestination?
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination

    val currentScreenDestination: ScreenDestination
        @Composable get() = when (currentNavDestination?.route) {
            homeRoute -> ScreenDestination.HOME
            detailRoute -> ScreenDestination.DETAIL
            else -> ScreenDestination.HOME
        }

    val isOffline = networkMonitor.isOnline
        .map(Boolean::not)
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = false
        )

    fun navigateToScreenDestination(
        screenDestination: ScreenDestination,
        args: Map<String, String>
    ) {
        val topLevelOptions = navOptions {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }

            // Avoid multiple copies of the same destination by re-selecting the same item
            launchSingleTop = true
            // Restore state when re-selecting a previously selected item
            restoreState = true

            when (screenDestination) {
                ScreenDestination.HOME -> navController.navigateToHomeScreen()
                ScreenDestination.DETAIL -> navController.navigateToDetailScreen(args)
            }
        }
    }

    fun onBackClick() {
        navController.popBackStack()
    }
}