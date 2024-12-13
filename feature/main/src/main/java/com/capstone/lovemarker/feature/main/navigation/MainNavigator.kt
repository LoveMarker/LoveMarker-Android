package com.capstone.lovemarker.feature.main.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.capstone.lovemarker.core.model.SearchPlace
import com.capstone.lovemarker.core.navigation.MainTabRoute
import com.capstone.lovemarker.core.navigation.Route
import com.capstone.lovemarker.feature.archive.navigation.navigateToArchive
import com.capstone.lovemarker.feature.detail.navigation.navigateToDetail
import com.capstone.lovemarker.feature.matching.navigation.navigateToMatching
import com.capstone.lovemarker.feature.matching.navigation.navigateToReceiver
import com.capstone.lovemarker.feature.matching.navigation.navigateToSender
import com.capstone.lovemarker.feature.login.navigation.navigateToLogin
import com.capstone.lovemarker.feature.map.navigation.navigateToMap
import com.capstone.lovemarker.feature.myfeed.navigation.navigateToMyFeed
import com.capstone.lovemarker.feature.mypage.navigation.navigateToMyPage
import com.capstone.lovemarker.feature.nickname.navigation.navigateToNickname
import com.capstone.lovemarker.feature.search.navigation.navigateToPlaceSearch
import com.capstone.lovemarker.feature.upload.navigation.navigateToContent
import com.capstone.lovemarker.feature.upload.navigation.navigateToPhoto

class MainNavigator(
    val navController: NavHostController,
) {
    private val currentDestination: NavDestination?
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination

    val startDestination = Route.Splash

    val currentTab: MainTab?
        @Composable get() = MainTab.find { tab ->
            currentDestination?.hasRoute(tab::class) == true
        }

    fun navigate(tab: MainTab) {
        val navOptions = navOptions {
            popUpTo<MainTabRoute.Map> {
                inclusive = false
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }

        when (tab) {
            MainTab.MAP -> navController.navigateToMap(navOptions)
            MainTab.ARCHIVE -> navController.navigateToArchive(navOptions)
            MainTab.MY_PAGE -> navController.navigateToMyPage(navOptions = navOptions)
        }
    }

    /**
     * On Boarding
     * */
    fun navigateToLogin(navOptions: NavOptions) {
        navController.navigateToLogin(navOptions)
    }

    fun navigateToNickname(
        prevRouteName: String,
        currentNickname: String? = null,
        navOptions: NavOptions? = null
    ) {
        navController.navigateToNickname(
            prevRouteName = prevRouteName,
            currentNickname = currentNickname,
            navOptions = navOptions
        )
    }

    /**
     * Couple Matching
     * */
    fun navigateToMatching(prevRouteName: String, navOptions: NavOptions? = null) {
        navController.navigateToMatching(prevRouteName, navOptions)
    }

    fun navigateToSender(prevRouteName: String) {
        navController.navigateToSender(prevRouteName)
    }

    fun navigateToReceiver(prevRouteName: String) {
        navController.navigateToReceiver(prevRouteName)
    }

    /**
     * Main Tab
     * */
    fun navigateToMap(navOptions: NavOptions) {
        navController.navigateToMap(navOptions)
    }

    fun navigateToMyPage(modifiedNickname: String, navOptions: NavOptions) {
        navController.navigateToMyPage(modifiedNickname, navOptions)
    }

    fun navigateToMyFeed() {
        navController.navigateToMyFeed()
    }

    fun navigateToDetail(memoryId: Int) {
        navController.navigateToDetail(memoryId)
    }

    /**
     * Upload
     * */
    fun navigateToPhoto() {
        navController.navigateToPhoto()
    }

    fun navigateToContent(
        searchPlace: SearchPlace? = null,
        navOptions: NavOptions? = null
    ) {
        navController.navigateToContent(
            searchPlace = searchPlace,
            navOptions = navOptions
        )
    }

    fun navigateToPlaceSearch() {
        navController.navigateToPlaceSearch()
    }

    /**
     * Navigate Up
     * */
    private fun navigateUp() {
        navController.navigateUp()
    }

    fun navigateUpIfNotHome() {
        if (!isSameCurrentDestination<MainTabRoute.Map>()) {
            navigateUp()
        }
    }

    private inline fun <reified T : Route> isSameCurrentDestination(): Boolean {
        return navController.currentDestination?.hasRoute<T>() == true
    }

    @Composable
    fun shouldShowBottomBar() = MainTab.contains {
        currentDestination?.hasRoute(it::class) == true
    }
}

@Composable
internal fun rememberMainNavigator(
    navController: NavHostController = rememberNavController(),
): MainNavigator = remember(navController) {
    MainNavigator(navController)
}
