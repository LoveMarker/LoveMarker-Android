package com.capstone.lovemarker.feature.matching.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.capstone.lovemarker.core.navigation.MatchingRoute
import com.capstone.lovemarker.feature.matching.home.MatchingScreen
import com.capstone.lovemarker.feature.matching.receiver.ReceiverScreen
import com.capstone.lovemarker.feature.matching.sender.SenderScreen

fun NavController.navigateToMatching(navOptions: NavOptions) {
    navigate(MatchingRoute.Home, navOptions)
}

fun NavController.navigateToSender() {
    navigate(MatchingRoute.Sender)
}

fun NavController.navigateToReceiver() {
    navigate(MatchingRoute.Receiver)
}

fun NavGraphBuilder.matchingNavGraph(
    navigateToSender: () -> Unit,
    navigateToReceiver: () -> Unit,
    navigateUp: () -> Unit,
    navigateToMap: () -> Unit,
) {
    composable<MatchingRoute.Home> {
        MatchingScreen(
            navigateToSender = navigateToSender,
            navigateToReceiver = navigateToReceiver,
        )
    }
    composable<MatchingRoute.Sender> {
        SenderScreen(navigateUp = navigateUp)
    }
    composable<MatchingRoute.Receiver> {
        ReceiverScreen(
            navigateUp = navigateUp,
            navigateToMap = navigateToMap,
        )
    }
}
