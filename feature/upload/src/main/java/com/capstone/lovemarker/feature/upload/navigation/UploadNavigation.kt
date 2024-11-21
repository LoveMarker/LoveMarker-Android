package com.capstone.lovemarker.feature.upload.navigation

import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.capstone.lovemarker.core.navigation.UploadRoute
import com.capstone.lovemarker.feature.upload.content.ContentRoute
import com.capstone.lovemarker.feature.upload.photo.PhotoRoute
import kotlinx.collections.immutable.PersistentList

fun NavController.navigateToPhoto() {
    navigate(UploadRoute.Photo)
}

fun NavController.navigateToContent(images: PersistentList<String>) {
    navigate(
        route = UploadRoute.Content(
            images = images
        ),
    )
}

fun NavController.navigateToPlaceSearch() {
    navigate(UploadRoute.PlaceSearch)
}

fun NavGraphBuilder.uploadNavGraph(
    navigateUp: () -> Unit,
    navigateToContent: (PersistentList<String>) -> Unit,
) {
    composable<UploadRoute.Photo> {
        PhotoRoute(
            navigateUp = navigateUp,
            navigateToContent = navigateToContent
        )
    }
    composable<UploadRoute.Content> { navBackStackEntry: NavBackStackEntry ->
        val route = navBackStackEntry.toRoute<UploadRoute.Content>()
        ContentRoute(
            navigateUp = navigateUp,
            images = route.images
        )
    }
    composable<UploadRoute.PlaceSearch> {

    }
}
