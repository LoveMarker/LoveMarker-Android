package com.capstone.lovemarker.feature.login.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.capstone.lovemarker.core.navigation.Route
import com.capstone.lovemarker.feature.login.LoginRoute

fun NavController.navigateLogin() {
    navigate(Route.Login)
}

fun NavGraphBuilder.loginNavGraph(
    navigateToNickname: () -> Unit,
    showErrorSnackbar: (Throwable?) -> Unit,
) {
    composable<Route.Login> {
        LoginRoute(
            navigateToNickname = navigateToNickname,
            showErrorSnackbar = showErrorSnackbar
        )
    }
}
