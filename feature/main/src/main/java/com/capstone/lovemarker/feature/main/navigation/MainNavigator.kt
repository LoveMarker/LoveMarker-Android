package com.capstone.lovemarker.feature.main.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.capstone.lovemarker.core.navigation.MainTabRoute
import com.capstone.lovemarker.core.navigation.MatchingRoute
import com.capstone.lovemarker.core.navigation.Route
import com.capstone.lovemarker.feature.matching.navigation.navigateToMatching
import com.capstone.lovemarker.feature.matching.navigation.navigateToReceiver
import com.capstone.lovemarker.feature.matching.navigation.navigateToSender
import com.capstone.lovemarker.feature.nickname.navigation.navigateToNickname

class MainNavigator(
    val navController: NavHostController,
) {
    private val currentDestination: NavDestination?
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination

    val startDestination = MatchingRoute.Sender

    fun navigateToNickname(navOptions: NavOptions) {
        navController.navigateToNickname(navOptions)
    }

    fun navigateToMatching() {
        navController.navigateToMatching()
    }

    fun navigateToSender() {
        navController.navigateToSender()
    }

    fun navigateToReceiver() {
        navController.navigateToReceiver()
    }

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
}

@Composable
internal fun rememberMainNavigator(
    navController: NavHostController = rememberNavController(),
): MainNavigator = remember(navController) {
    MainNavigator(navController)
}
