package com.capstone.lovemarker.feature.main.splash

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.capstone.lovemarker.core.navigation.Route

fun NavGraphBuilder.splashNavGraph(
    navigateToMap: () -> Unit,
    navigateToLogin: () -> Unit,
) {
    composable<Route.Splash> {
        SplashScreen(
            navigateToMap = navigateToMap,
            navigateToLogin = navigateToLogin
        )
    }
}
