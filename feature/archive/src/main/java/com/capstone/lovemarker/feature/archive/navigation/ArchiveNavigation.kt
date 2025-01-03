package com.capstone.lovemarker.feature.archive.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.capstone.lovemarker.core.navigation.MainTabRoute
import com.capstone.lovemarker.feature.archive.ArchiveRoute

fun NavController.navigateToArchive(navOptions: NavOptions? = null) {
    navigate(MainTabRoute.Archive, navOptions)
}

fun NavGraphBuilder.archiveNavGraph(
    innerPadding: PaddingValues,
    navigateToDetail: (Int) -> Unit,
    navigateToMatching: () -> Unit,
    showErrorSnackbar: (Throwable?) -> Unit,
) {
    composable<MainTabRoute.Archive> {
        ArchiveRoute(
            innerPadding = innerPadding,
            navigateToDetail = navigateToDetail,
            navigateToMatching = navigateToMatching,
            showErrorSnackbar = showErrorSnackbar
        )
    }
}
