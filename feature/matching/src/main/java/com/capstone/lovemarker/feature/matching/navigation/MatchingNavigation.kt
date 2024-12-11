package com.capstone.lovemarker.feature.matching.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.capstone.lovemarker.core.navigation.MatchingRoute
import com.capstone.lovemarker.feature.matching.home.MatchingScreen
import com.capstone.lovemarker.feature.matching.receiver.ReceiverRoute
import com.capstone.lovemarker.feature.matching.sender.SenderRoute

fun NavController.navigateToMatching(prevRouteName: String, navOptions: NavOptions? = null) {
    navigate(
        route = MatchingRoute.Home(prevRouteName = prevRouteName),
        navOptions = navOptions
    )
}

fun NavController.navigateToSender(prevRouteName: String) {
    navigate(MatchingRoute.Sender(prevRouteName))
}

fun NavController.navigateToReceiver(prevRouteName: String) {
    navigate(MatchingRoute.Receiver(prevRouteName))
}

fun NavGraphBuilder.matchingNavGraph(
    navigateToSender: (String) -> Unit,
    navigateToReceiver: (String) -> Unit,
    navigateUp: () -> Unit,
    navigateToMap: () -> Unit,
    showErrorSnackbar: (Throwable?) -> Unit,
) {
    composable<MatchingRoute.Home> { backStackEntry ->
        val matchingRoute = backStackEntry.toRoute<MatchingRoute.Home>()
        MatchingScreen(
            navigateToSender = {
                navigateToSender(matchingRoute.prevRouteName)
            },
            navigateToReceiver = {
                navigateToReceiver(matchingRoute.prevRouteName)
            },
        )
    }
    composable<MatchingRoute.Sender> {
        SenderRoute(
            navigateUp = navigateUp,
            navigateToMap = navigateToMap,
            showErrorSnackbar = showErrorSnackbar,
        )
    }
    composable<MatchingRoute.Receiver> {
        ReceiverRoute(
            navigateUp = navigateUp,
            navigateToMap = navigateToMap,
            showErrorSnackbar = showErrorSnackbar
        )
    }
}
