package com.capstone.lovemarker.feature.upload.navigation

import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.capstone.lovemarker.core.navigation.UploadRoute
import com.capstone.lovemarker.feature.upload.content.ContentRoute
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
    navController: NavHostController,
    navigateUp: () -> Unit,
    navigateToContent: () -> Unit,
) {
    composable<UploadRoute.Photo> {
        PhotoRoute(
            navigateUp = navigateUp,
            navigateToContent = navigateToContent
        )
    }
    composable<UploadRoute.Content> { backStackEntry ->
        val prevBackStackEntry = remember(backStackEntry) {
            navController.getBackStackEntry(UploadRoute.Photo)
        }
        ContentRoute(
            navigateUp = navigateUp,
            viewModel = hiltViewModel(prevBackStackEntry)
        )
    }
    composable<UploadRoute.PlaceSearch> {

    }
}
