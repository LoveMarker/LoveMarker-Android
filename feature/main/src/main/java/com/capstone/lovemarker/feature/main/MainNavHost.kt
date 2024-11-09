package com.capstone.lovemarker.feature.main

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.capstone.lovemarker.feature.login.navigation.loginNavGraph

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
        loginNavGraph(
            navigateToNickname = {
//                navigator.navigateToNickname(
//                    navOptions = navOptions {
//                        popUpTo<Route.Login> {
//                            inclusive = true
//                        }
//                        launchSingleTop = true
//                    }
//                )
            },
            showErrorSnackbar = showErrorSnackbar
        )
    }
}