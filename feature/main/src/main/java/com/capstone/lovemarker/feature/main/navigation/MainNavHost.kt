package com.capstone.lovemarker.feature.main.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.navOptions
import com.capstone.lovemarker.core.navigation.Route
import com.capstone.lovemarker.feature.login.navigation.loginNavGraph
import com.capstone.lovemarker.feature.main.splash.splashNavGraph
import com.capstone.lovemarker.feature.map.navigation.mapNavGraph
import com.capstone.lovemarker.feature.nickname.navigation.nicknameNavGraph

@Composable
fun MainNavHost(
    navigator: MainNavigator,
    innerPadding: PaddingValues,
    showErrorSnackbar: (throwable: Throwable?) -> Unit,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navigator.navController,
        startDestination = navigator.startDestination,
        modifier = modifier
    ) {
        splashNavGraph(
            navigateToMap = {
                navigator.navigateToMap(
                    navOptions = navOptionsPopUpTo<Route.Splash>()
                )
            },
            navigateToLogin = {
                navigator.navigateToLogin(
                    navOptions = navOptionsPopUpTo<Route.Splash>()
                )
            }
        )
        loginNavGraph(
            navigateToMap = {
                navigator.navigateToMap(
                    navOptions = navOptionsPopUpTo<Route.Login>()
                )
            },
            navigateToNickname = {
                navigator.navigateToNickname(
                    navOptions = navOptionsPopUpTo<Route.Login>()
                )
            },
            showErrorSnackbar = showErrorSnackbar
        )
        nicknameNavGraph(
            prevRoute = Route.Login,
            navigateUp = { navigator.navigateUpIfNotHome() },
            navigateToMatching = { /* TODO */ },
            showErrorSnackbar = showErrorSnackbar
        )
        mapNavGraph(
            innerPadding = innerPadding
        )
    }
}

private inline fun <reified T : Route> navOptionsPopUpTo() = navOptions {
    popUpTo<T> {
        inclusive = true
    }
    launchSingleTop = true
}
