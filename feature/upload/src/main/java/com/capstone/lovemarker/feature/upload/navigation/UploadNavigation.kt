package com.capstone.lovemarker.feature.upload.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.capstone.lovemarker.core.navigation.UploadRoute

fun NavController.navigateToPhoto() {
    navigate(UploadRoute.Photo)
}

fun NavController.navigateToContent() {
    navigate(UploadRoute.Content)
}

fun NavController.navigateToPlaceSearch() {
    navigate(UploadRoute.PlaceSearch)
}

fun NavGraphBuilder.uploadNavGraph(
    navigateUp: () -> Unit,
) {
    composable<UploadRoute.Photo> {

    }
    composable<UploadRoute.Content> {

    }
    composable<UploadRoute.PlaceSearch> {

    }
}
