package com.capstone.lovemarker.feature.matching.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.capstone.lovemarker.core.navigation.MatchingRoute
import com.capstone.lovemarker.feature.matching.home.MatchingScreen

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
    // todo: sender, receiver navigation
) {
    composable<MatchingRoute.Home> {
        MatchingScreen(
            onCreateButtonClick = navigateToSender,
            onInputButtonClick = navigateToReceiver,
        )
    }
    // todo: sender, receiver 화면
}
