package com.capstone.lovemarker.feature.mypage.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.capstone.lovemarker.core.navigation.MainTabRoute
import com.capstone.lovemarker.feature.mypage.MyPageRoute

fun NavController.navigateToMyPage(nickname: String? = null, navOptions: NavOptions? = null) {
    navigate(MainTabRoute.MyPage(nickname), navOptions)
}

fun NavGraphBuilder.myPageNavGraph(
    innerPadding: PaddingValues,
    navigateToMatching: () -> Unit,
    navigateToNickname: () -> Unit,
    showErrorSnackbar: (Throwable?) -> Unit,
) {
    composable<MainTabRoute.MyPage> { backStackEntry ->
        val route = backStackEntry.toRoute<MainTabRoute.MyPage>()
        MyPageRoute(
            innerPadding = innerPadding,
            navigateToMatching = navigateToMatching,
            navigateToNickname = navigateToNickname,
            showErrorSnackbar = showErrorSnackbar,
            modifiedNickname = route.nickname
        )
    }
}
