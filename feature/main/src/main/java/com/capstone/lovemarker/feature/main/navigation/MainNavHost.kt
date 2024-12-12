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
import com.capstone.lovemarker.feature.detail.navigation.detailNavGraph
import com.capstone.lovemarker.feature.login.navigation.loginNavGraph
import com.capstone.lovemarker.feature.matching.navigation.matchingNavGraph
import com.capstone.lovemarker.feature.main.splash.splashNavGraph
import com.capstone.lovemarker.feature.map.navigation.mapNavGraph
import com.capstone.lovemarker.feature.mypage.navigation.myPageNavGraph
import com.capstone.lovemarker.feature.nickname.navigation.nicknameNavGraph
import com.capstone.lovemarker.feature.search.navigation.searchNavGraph
import com.capstone.lovemarker.feature.upload.navigation.uploadNavGraph
import timber.log.Timber

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
                    prevRouteName = "login",
                    navOptions = navOptionsPopUpTo<Route.Login>()
                )
            },
            showErrorSnackbar = showErrorSnackbar
        )
        nicknameNavGraph(
            navigateUp = { navigator.navigateUpIfNotHome() },
            navigateToMyPage = { modifiedNickname ->
                navigator.navigateToMyPage(
                    modifiedNickname = modifiedNickname,
                    navOptions = navOptionsPopUpTo<Route.Nickname>()
                )
            },
            navigateToMatching = {
                navigator.navigateToMatching(
                    prevRouteName = "nickname",
                    navOptions = navOptionsPopUpTo<Route.Nickname>()
                )
            },
            showErrorSnackbar = showErrorSnackbar
        )
        matchingNavGraph(
            navigateUp = { navigator.navigateUpIfNotHome() },
            navigateToSender = { prevRouteName ->
                navigator.navigateToSender(prevRouteName)
            },
            navigateToReceiver = { prevRouteName ->
                navigator.navigateToReceiver(prevRouteName)
            },
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
            },
            navigateToDetail = { memoryId ->
                navigator.navigateToDetail(memoryId)
            },
            navigateToMatching = {
                navigator.navigateToMatching(prevRouteName = "map")
            },
            showErrorSnackbar = showErrorSnackbar
        )
        archiveNavGraph(
            innerPadding = innerPadding,
            navigateToDetail = { memoryId ->
                navigator.navigateToDetail(memoryId)
            },
            navigateToMatching = {
                navigator.navigateToMatching(prevRouteName = "archive")
            },
            showErrorSnackbar = showErrorSnackbar
        )
        myPageNavGraph(
            innerPadding = innerPadding,
            navigateToMatching = { navigator.navigateToMatching(prevRouteName = "mypage") },
            navigateToNickname = { nickname ->
                navigator.navigateToNickname(
                    prevRouteName = "mypage",
                    currentNickname = nickname
                )
            },
            showErrorSnackbar = showErrorSnackbar
        )
        uploadNavGraph(
            navigateUp = { navigator.navigateUpIfNotHome() },
            navigateToContent = { navigator.navigateToContent() },
            navigateToPlaceSearch = { navigator.navigateToPlaceSearch() },
            navigateToMap = {
                navigator.navigateToMap(
                    navOptions = navOptionsPopUpTo<UploadRoute.Photo>()
                )
            },
            getBackStackEntryFromPhoto = {
                // to share upload viewmodel between photo and content
                navigator.navController.getBackStackEntry(UploadRoute.Photo)
            },
            showErrorSnackbar = showErrorSnackbar,
        )
        searchNavGraph(
            navigateUp = { navigator.navigateUpIfNotHome() },
            navigateToContent = { place ->
                navigator.navigateToContent(
                    searchPlace = place,
                    navOptions = navOptionsPopUpTo<UploadRoute.PlaceSearch>()
                )
            },
            showErrorSnackbar = showErrorSnackbar
        )
        detailNavGraph(
            navigateUp = {
                navigator.navigateUpIfNotHome()
            },
            showErrorSnackbar = showErrorSnackbar
        )
    }
}

private inline fun <reified T : Route> navOptionsPopUpTo() = navOptions {
    popUpTo<T> {
        inclusive = true
    }
    launchSingleTop = true
}

private inline fun <reified T : Route> navOptionsSaveState() = navOptions {
    popUpTo<T> {
        inclusive = false
        saveState = true
    }
    launchSingleTop = true
    restoreState = true
}
