package com.capstone.lovemarker.feature.main.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.navOptions
import com.capstone.lovemarker.core.navigation.MatchingRoute
import com.capstone.lovemarker.core.navigation.Route
import com.capstone.lovemarker.core.navigation.UploadRoute
import com.capstone.lovemarker.feature.archive.navigation.archiveNavGraph
import com.capstone.lovemarker.feature.login.navigation.loginNavGraph
import com.capstone.lovemarker.feature.main.splash.splashNavGraph
import com.capstone.lovemarker.feature.map.navigation.mapNavGraph
import com.capstone.lovemarker.feature.matching.navigation.matchingNavGraph
import com.capstone.lovemarker.feature.mypage.navigation.myPageNavGraph
import com.capstone.lovemarker.feature.nickname.navigation.nicknameNavGraph
import com.capstone.lovemarker.feature.search.navigation.searchNavGraph
import com.capstone.lovemarker.feature.upload.navigation.uploadNavGraph

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
            navigateToMatching = {
                navigator.navigateToMatching(
                    navOptions = navOptionsPopUpTo<Route.Nickname>()
                )
            },
            showErrorSnackbar = showErrorSnackbar
        )
        matchingNavGraph(
            navigateToSender = { navigator.navigateToSender() },
            navigateToReceiver = { navigator.navigateToReceiver() },
            navigateUp = { navigator.navigateUpIfNotHome() },
            navigateToMap = {
                navigator.navigateToMap(
                    navOptions = navOptionsPopUpTo<MatchingRoute.Home>()
                )
            },
            showErrorSnackbar = showErrorSnackbar
        )
        mapNavGraph(
            innerPadding = innerPadding,
            navigateToPhoto = {
                navigator.navigateToPhoto()
            }
        )
        archiveNavGraph(
            innerPadding = innerPadding
        )
        myPageNavGraph(
            innerPadding = innerPadding
        )
        uploadNavGraph(
            navigateUp = { navigator.navigateUpIfNotHome() },
            navigateToContent = {
                navigator.navigateToContent()
            },
            getBackStackEntryFromPhoto = {
                navigator.navController.getBackStackEntry(UploadRoute.Photo)
            }
        )
        searchNavGraph(
            navigateUp = {
                navigator.navigateToPlaceSearch()
            }
        )
    }
}

private inline fun <reified T : Route> navOptionsPopUpTo() = navOptions {
    popUpTo<T> {
        inclusive = true
    }
    launchSingleTop = true
}

private inline fun <reified T: Route> navOptionsSaveState() = navOptions {
    popUpTo<T> {
        inclusive = false
        saveState = true
    }
    launchSingleTop = true
    restoreState = true
}
