package com.capstone.lovemarker.feature.main.navigation

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import com.capstone.lovemarker.core.navigation.MainTabRoute
import com.capstone.lovemarker.core.navigation.Route
import com.capstone.lovemarker.feature.main.R

enum class MainTab(
    @DrawableRes val iconResId: Int,
    val contentDescription: String,
    val route: MainTabRoute,
) {
    MAP(
        iconResId = R.drawable.ic_map,
        contentDescription = "Map",
        route = MainTabRoute.Map
    ),
    ARCHIVE(
        iconResId = R.drawable.ic_archive,
        contentDescription = "Archive",
        route = MainTabRoute.Archive
    ),
    MY_PAGE(
        iconResId = R.drawable.ic_mypage,
        contentDescription = "MyPage",
        route = MainTabRoute.MyPage
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
