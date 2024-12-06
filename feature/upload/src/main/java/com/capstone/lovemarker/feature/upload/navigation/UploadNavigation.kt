package com.capstone.lovemarker.feature.upload.navigation

import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.capstone.lovemarker.core.model.SearchPlace
import com.capstone.lovemarker.core.navigation.UploadRoute
import com.capstone.lovemarker.core.navigation.searchPlaceTypeMap
import com.capstone.lovemarker.feature.upload.content.ContentRoute
import com.capstone.lovemarker.feature.upload.photo.PhotoRoute

fun NavController.navigateToPhoto() {
    navigate(UploadRoute.Photo)
}

/**
 * from Photo -> parameters null
 * from PlaceSearch -> parameters non-null
 * */
fun NavController.navigateToContent(
    searchPlace: SearchPlace? = null,
    navOptions: NavOptions? = null
) {
    navigate(
        route = UploadRoute.Content(searchPlace),
        navOptions = navOptions
    )
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
        typeMap = searchPlaceTypeMap // 주의: 리컴포지션 방지를 위해 val로 미리 선언해둠.
    ) { backStackEntry ->
        val backStackEntryFromPhoto = remember(backStackEntry) {
            getBackStackEntryFromPhoto()
        }
        val content = backStackEntry.toRoute<UploadRoute.Content>()

        ContentRoute(
            navigateUp = navigateUp,
            navigateToPlaceSearch = navigateToPlaceSearch,
            navigateToMap = navigateToMap,
            showErrorSnackbar = showErrorSnackbar,
            searchPlace = content.searchPlace,
            viewModel = hiltViewModel(backStackEntryFromPhoto)
        )
    }
}
