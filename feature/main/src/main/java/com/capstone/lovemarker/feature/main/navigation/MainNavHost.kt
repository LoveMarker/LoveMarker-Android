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
                navigator.navigateToMap()
            },
            navigateToLogin = {
                navigator.navigateToLogin()
            }
        )
        loginNavGraph(
            navigateToMap = {
                navigator.navigateToMap()
            },
            navigateToNickname = {
                navigator.navigateToNickname(
                    navOptions = navOptions {
                        // 로그인 화면을 스택에서 제거
                        popUpTo<Route.Login> {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
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
        mapNavGraph()
    }
}
