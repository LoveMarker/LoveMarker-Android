package com.capstone.lovemarker.feature.detail.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.capstone.lovemarker.core.navigation.Route
import com.capstone.lovemarker.feature.detail.DetailRoute

fun NavController.navigateToDetail(memoryId: Int) {
    navigate(Route.Detail(memoryId))
}

fun NavGraphBuilder.detailNavGraph(
    navigateUp: () -> Unit,
    showErrorSnackbar: (Throwable?) -> Unit,
) {
    composable<Route.Detail> { backStackEntry ->
        val route = backStackEntry.toRoute<Route.Detail>()
        DetailRoute(
            memoryId = route.memoryId,
            navigateUp = navigateUp,
            showErrorSnackbar = showErrorSnackbar
        )
    }
}
