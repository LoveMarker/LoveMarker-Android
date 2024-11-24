package com.capstone.lovemarker.feature.upload.navigation

import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.capstone.lovemarker.core.model.SearchPlace
import com.capstone.lovemarker.core.navigation.UploadRoute
import com.capstone.lovemarker.core.navigation.serializableType
import com.capstone.lovemarker.feature.upload.content.ContentRoute
import com.capstone.lovemarker.feature.upload.photo.PhotoRoute
import timber.log.Timber
import kotlin.reflect.typeOf

fun NavController.navigateToPhoto() {
    navigate(UploadRoute.Photo)
}

fun NavController.navigateToContent(searchPlace: SearchPlace? = null) {
    navigate(route = UploadRoute.Content(searchPlace))
}

fun NavGraphBuilder.uploadNavGraph(
    navigateUp: () -> Unit,
    navigateToPlaceSearch: () -> Unit,
    navigateToContent: () -> Unit,
    getBackStackEntryFromPhoto: () -> NavBackStackEntry,
) {
    composable<UploadRoute.Photo> {
        PhotoRoute(
            navigateUp = navigateUp,
            navigateToContent = navigateToContent
        )
    }
    composable<UploadRoute.Content>(
        typeMap = mapOf(
            typeOf<SearchPlace?>() to serializableType<SearchPlace?>(isNullableAllowed = true)
        )
    ) { backStackEntry ->
        val backStackEntryFromPhoto = remember(backStackEntry) {
            getBackStackEntryFromPhoto()
        }

        val content = backStackEntry.toRoute<UploadRoute.Content>()
        Timber.d("place from route: ${content.searchPlace}")

        ContentRoute(
            navigateUp = navigateUp,
            navigateToPlaceSearch = navigateToPlaceSearch,
            searchPlace = content.searchPlace,
            viewModel = hiltViewModel(backStackEntryFromPhoto)
        )
    }
}
