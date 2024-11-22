package com.capstone.lovemarker.feature.upload.navigation

import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.capstone.lovemarker.core.navigation.UploadRoute
import com.capstone.lovemarker.feature.upload.content.ContentRoute
import com.capstone.lovemarker.feature.upload.photo.PhotoRoute
import com.capstone.lovemarker.feature.upload.search.PlaceSearchRoute

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
    navigateToPlaceSearch: () -> Unit,
    getBackStackEntryFromPhoto: () -> NavBackStackEntry,
) {
    composable<UploadRoute.Photo> {
        PhotoRoute(
            navigateUp = navigateUp,
            navigateToContent = navigateToContent
        )
    }
    composable<UploadRoute.Content> { backStackEntry ->
        val backStackEntryFromPhoto = remember(backStackEntry) {
            getBackStackEntryFromPhoto()
        }

        ContentRoute(
            navigateUp = navigateUp,
            navigateToPlaceSearch = navigateToPlaceSearch,
            viewModel = hiltViewModel(backStackEntryFromPhoto)
        )
    }
    composable<UploadRoute.PlaceSearch> {
        PlaceSearchRoute(

        )
    }
}
