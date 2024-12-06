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

fun NavController.navigateToPhoto() {
    navigate(UploadRoute.Photo)
}

fun NavController.navigateToContent(
    // todo: search 화면으로부터 데이터 받아오기 (map recomposition 유의)
) {
    navigate(UploadRoute.Content)
}

fun NavGraphBuilder.uploadNavGraph(
    navigateUp: () -> Unit,
    navigateToContent: () -> Unit,
    navigateToPlaceSearch: () -> Unit,
    navigateToMap: (Int) -> Unit,
    showErrorSnackbar: (Throwable?) -> Unit,
    getBackStackEntryFromPhoto: () -> NavBackStackEntry,
) {
    composable<UploadRoute.Photo> {
        PhotoRoute(
            navigateUp = navigateUp,
            navigateToContent = navigateToContent
        )
    }
    composable<UploadRoute.Content>(
//        typeMap = mapOf(
//            typeOf<SearchPlace?>() to serializableNavType<SearchPlace?>(isNullableAllowed = true)
//        )
    ) { backStackEntry ->
        val backStackEntryFromPhoto = remember(backStackEntry) {
            getBackStackEntryFromPhoto()
        }

        ContentRoute(
            navigateUp = navigateUp,
            navigateToPlaceSearch = navigateToPlaceSearch,
            navigateToMap = navigateToMap,
            showErrorSnackbar = showErrorSnackbar,
            viewModel = hiltViewModel(backStackEntryFromPhoto)
        )
    }
}
