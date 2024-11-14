package com.capstone.lovemarker.feature.matching.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.capstone.lovemarker.core.navigation.Matching
import com.capstone.lovemarker.feature.matching.home.MatchingScreen

fun NavController.navigateToMatching() {
    navigate(Matching.Home)
}

fun NavController.navigateToSender() {
    navigate(Matching.Sender)
}

fun NavController.navigateToReceiver() {
    navigate(Matching.Receiver)
}

fun NavGraphBuilder.matchingNavGraph(
    navigateToSender: () -> Unit,
    navigateToReceiver: () -> Unit,
    // todo: sender, receiver navigation
) {
    composable<Matching.Home> {
        MatchingScreen(
            onCreateButtonClick = navigateToSender,
            onInputButtonClick = navigateToReceiver,
        )
    }
    // todo: sender, receiver 화면
}
