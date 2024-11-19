package com.capstone.lovemarker.feature.mypage.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.capstone.lovemarker.core.navigation.MainTabRoute

fun NavController.navigateToMyPage(navOptions: NavOptions? = null) {
    navigate(MainTabRoute.MyPage, navOptions)
}

fun NavGraphBuilder.myPageNavGraph(
    innerPadding: PaddingValues,
) {
    composable<MainTabRoute.MyPage> {

    }
}
