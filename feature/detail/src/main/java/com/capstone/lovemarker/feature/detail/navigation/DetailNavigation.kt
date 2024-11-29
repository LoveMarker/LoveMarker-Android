package com.capstone.lovemarker.feature.detail.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.capstone.lovemarker.core.navigation.Route
import com.capstone.lovemarker.feature.detail.DetailRoute

fun NavController.navigateToDetail() {
    navigate(Route.Detail)
}

fun NavGraphBuilder.detailNavGraph(
    navigateUp: () -> Unit,
) {
    composable<Route.Detail> {
        DetailRoute(
            navigateUp = navigateUp
        )
    }
}
