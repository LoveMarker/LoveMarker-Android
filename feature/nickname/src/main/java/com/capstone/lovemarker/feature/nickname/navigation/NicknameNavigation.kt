package com.capstone.lovemarker.feature.nickname.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.capstone.lovemarker.core.navigation.Route
import com.capstone.lovemarker.feature.nickname.NicknameRoute

fun NavController.navigateToNickname() {
    navigate(Route.Nickname)
}

fun NavGraphBuilder.nicknameNavGraph(
    pervRoute: Route,
    navigateUp: () -> Unit, // from MyPage
    navigateToMatching: () -> Unit, // from Login
    showErrorSnackbar: (Throwable?) -> Unit
) {
    composable<Route.Nickname> {
        NicknameRoute(
            prevRoute = pervRoute,
            navigateUp = navigateUp,
            navigateToMatching = navigateToMatching,
            showErrorSnackbar = showErrorSnackbar
        )
    }
}