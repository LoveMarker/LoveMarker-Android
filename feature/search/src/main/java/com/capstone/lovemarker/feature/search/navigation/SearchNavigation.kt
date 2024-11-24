package com.capstone.lovemarker.feature.search.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.capstone.lovemarker.core.navigation.UploadRoute
import com.capstone.lovemarker.feature.search.SearchRoute

fun NavController.navigateToPlaceSearch() {
    navigate(UploadRoute.PlaceSearch)
}

fun NavGraphBuilder.searchNavGraph(
    navigateUp: () -> Unit,
) {
    composable<UploadRoute.PlaceSearch> {
        SearchRoute(
            navigateUp = navigateUp
        )
    }
}
