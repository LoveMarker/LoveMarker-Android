package com.capstone.lovemarker.feature.upload.navigation

import android.net.Uri
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.capstone.lovemarker.core.navigation.UploadRoute
import com.capstone.lovemarker.feature.upload.photo.PhotoRoute
import kotlinx.collections.immutable.PersistentList

fun NavController.navigateToPhoto() {
    navigate(UploadRoute.Photo)
}

fun NavController.navigateToContent(navOptions: NavOptions) {
    navigate(UploadRoute.Content, navOptions)
}

fun NavController.navigateToPlaceSearch() {
    navigate(UploadRoute.PlaceSearch)
}

fun NavGraphBuilder.uploadNavGraph(
    navigateUp: () -> Unit,
    navigateToContent: (PersistentList<Uri>) -> Unit,
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
