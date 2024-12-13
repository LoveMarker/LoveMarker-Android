package com.capstone.lovemarker.feature.myfeed.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.capstone.lovemarker.core.navigation.Route
import com.capstone.lovemarker.feature.myfeed.MyFeedRoute

fun NavController.navigateToMyFeed() {
    navigate(Route.MyFeed)
}

fun NavGraphBuilder.myFeedNavGraph(
    navigateUp: () -> Unit,
    navigateToDetail: (Int) -> Unit,
    showErrorSnackbar: (Throwable?) -> Unit,
) {
    composable<Route.MyFeed> {
        MyFeedRoute(
            navigateUp = navigateUp,
            navigateToDetail = navigateToDetail,
            showErrorSnackbar = showErrorSnackbar
        )
    }
}
