package com.capstone.lovemarker.feature.matching.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.capstone.lovemarker.core.navigation.MatchingRoute
import com.capstone.lovemarker.feature.matching.home.MatchingScreen
import com.capstone.lovemarker.feature.matching.receiver.ReceiverScreen
import com.capstone.lovemarker.feature.matching.sender.SenderScreen

fun NavController.navigateToMatching() {
    navigate(MatchingRoute.Home)
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
            onCreateButtonClick = navigateToSender,
            onInputButtonClick = navigateToReceiver,
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
