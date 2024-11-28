package com.capstone.lovemarker.feature.nickname.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.capstone.lovemarker.core.navigation.Route
import com.capstone.lovemarker.feature.nickname.NicknameRoute

fun NavController.navigateToNickname(prevRouteName: String, navOptions: NavOptions? = null) {
    navigate(route = Route.Nickname(prevRouteName), navOptions = navOptions)
}

fun NavGraphBuilder.nicknameNavGraph(
    navigateUp: () -> Unit,
    navigateToMyPage: (String) -> Unit,
    navigateToMatching: () -> Unit,
    showErrorSnackbar: (Throwable?) -> Unit
) {
    composable<Route.Nickname> { backStackEntry ->
        val route = backStackEntry.toRoute<Route.Nickname>()
        NicknameRoute(
            prevRouteName = route.prevRouteName,
            navigateUp = navigateUp,
            navigateToMyPage = navigateToMyPage,
            navigateToMatching = navigateToMatching,
            showErrorSnackbar = showErrorSnackbar
        )
    }
}
