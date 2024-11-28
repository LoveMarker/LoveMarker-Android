package com.capstone.lovemarker.feature.main.navigation

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import com.capstone.lovemarker.core.navigation.MainTabRoute
import com.capstone.lovemarker.core.navigation.Route
import com.capstone.lovemarker.feature.main.R

enum class MainTab(
    @DrawableRes val iconResId: Int,
    val labelText: String,
    val route: MainTabRoute,
) {
    MAP(
        iconResId = R.drawable.ic_map,
        labelText = "지도",
        route = MainTabRoute.Map
    ),
    ARCHIVE(
        iconResId = R.drawable.ic_archive,
        labelText = "아카이브",
        route = MainTabRoute.Archive
    ),
    MY_PAGE(
        iconResId = R.drawable.ic_mypage,
        labelText = "마이페이지",
        route = MainTabRoute.MyPage()
    );

    companion object {
        @Composable
        fun find(predicate: @Composable (MainTabRoute) -> Boolean): MainTab? {
            return entries.find { predicate(it.route) }
        }

        @Composable
        fun contains(predicate: @Composable (Route) -> Boolean): Boolean {
            return entries.map { it.route }.any { predicate(it) }
        }
    }
}
