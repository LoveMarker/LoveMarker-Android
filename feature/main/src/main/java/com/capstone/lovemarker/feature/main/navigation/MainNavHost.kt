package com.capstone.lovemarker.feature.main.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.navOptions
import com.capstone.lovemarker.core.navigation.MainTabRoute
import com.capstone.lovemarker.core.navigation.MatchingRoute
import com.capstone.lovemarker.core.navigation.Route
import com.capstone.lovemarker.core.navigation.UploadRoute
import com.capstone.lovemarker.feature.archive.navigation.archiveNavGraph
import com.capstone.lovemarker.feature.detail.navigation.detailNavGraph
import com.capstone.lovemarker.feature.login.navigation.loginNavGraph
import com.capstone.lovemarker.feature.matching.navigation.matchingNavGraph
import com.capstone.lovemarker.feature.main.splash.splashNavGraph
import com.capstone.lovemarker.feature.map.navigation.mapNavGraph
import com.capstone.lovemarker.feature.matching.navigation.matchingNavGraph
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
//    Timber.tag("recomposition").d("Main Nav Host")

    NavHost(
        navController = navigator.navController,
        startDestination = navigator.startDestination,
        modifier = modifier
    ) {
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
            navigateToMyPage = { nickname ->
                navigator.navigateToMyPage(
                    nickname = nickname,
                    navOptions = navOptionsPopUpTo<Route.Nickname>()
                )
            },
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
            },
            navigateToMatching = {
                navigator.navigateToMatching()
            }
        )
        archiveNavGraph(
            innerPadding = innerPadding,
            navigateToDetail = { memoryId ->
                navigator.navigateToDetail(memoryId)
            },
            navigateToMatching = {
                navigator.navigateToMatching()
            },
            showErrorSnackbar = showErrorSnackbar
        )
        myPageNavGraph(
            innerPadding = innerPadding,
            navigateToMatching = { navigator.navigateToMatching() },
            navigateToNickname = {
                navigator.navigateToNickname(
                    prevRouteName = "mypage"
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
                navigator.navController.getBackStackEntry(UploadRoute.Photo)
            },
            showErrorSnackbar = showErrorSnackbar,
        )
        searchNavGraph(
            navigateUp = { navigator.navigateUpIfNotHome() },
            navigateToContent = { place ->
                navigator.navigateToContent(
                    place = place,
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
