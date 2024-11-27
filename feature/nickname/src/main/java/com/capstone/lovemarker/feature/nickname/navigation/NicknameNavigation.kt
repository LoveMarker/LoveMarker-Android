package com.capstone.lovemarker.feature.nickname.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.capstone.lovemarker.core.navigation.Route
import com.capstone.lovemarker.feature.nickname.NicknameRoute

fun NavController.navigateToNickname(navOptions: NavOptions? = null) {
    navigate(Route.Nickname, navOptions)
}

fun NavGraphBuilder.nicknameNavGraph(
    prevRoute: Route,
    navigateUp: () -> Unit, // from MyPage
    navigateToMatching: () -> Unit, // from Login
    showErrorSnackbar: (Throwable?) -> Unit
) {
    composable<Route.Nickname> {
        NicknameRoute(
            prevRoute = prevRoute,
            navigateUp = navigateUp,
            navigateToMatching = navigateToMatching,
            showErrorSnackbar = showErrorSnackbar
        )
    }
}