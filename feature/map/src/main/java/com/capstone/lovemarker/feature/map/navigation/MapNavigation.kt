package com.capstone.lovemarker.feature.map.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.capstone.lovemarker.core.navigation.MainTabRoute
import com.capstone.lovemarker.feature.map.MapRoute

fun NavController.navigateToMap(navOptions: NavOptions? = null) {
    navigate(MainTabRoute.Map, navOptions)
}

fun NavGraphBuilder.mapNavGraph() {
    composable<MainTabRoute.Map> {
        MapRoute()
    }
}
