package com.capstone.lovemarker.feature.upload.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.capstone.lovemarker.core.navigation.UploadRoute
import com.capstone.lovemarker.feature.upload.photo.PhotoRoute

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
    navigateToContent: () -> Unit,
) {
    composable<UploadRoute.Photo> {
        PhotoRoute(
            navigateUp = navigateUp,
            navigateToContent = navigateToContent
        )
    }
    composable<UploadRoute.Content> {

    }
    composable<UploadRoute.PlaceSearch> {

    }
}
