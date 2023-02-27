package com.semba.dkbimages.feature.detailsceen.ui

import androidx.navigation.*
import androidx.navigation.compose.composable
import com.semba.dkbimages.design.navigation.IMAGE_ID_ARG
import com.semba.dkbimages.design.navigation.withArgs

const val detailRoute = "detail_screen_route/{${IMAGE_ID_ARG}}"

fun NavController.navigateToDetailScreen(args: Map<String, String>, navOptions: NavOptions? = null) {
    this.navigate(detailRoute.withArgs(args), navOptions)
}

private val args = listOf(
    navArgument(IMAGE_ID_ARG) {
        type = NavType.IntType

    },
)

fun NavGraphBuilder.detailScreen() {
    composable(route = detailRoute, arguments = args) { navBackStackEntry ->
        val imageId = navBackStackEntry.arguments?.getInt(IMAGE_ID_ARG) ?: 0
        DetailRoute(imageId = imageId)
    }
}