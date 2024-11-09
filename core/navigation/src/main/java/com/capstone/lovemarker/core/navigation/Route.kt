package com.capstone.lovemarker.core.navigation

import kotlinx.serialization.Serializable

sealed interface Route {
    @Serializable
    data object Login : Route

    @Serializable
    data object Nickname : Route

    @Serializable
    data object Matching : Route

    @Serializable
    data object Upload : Route

    @Serializable
    data object Detail : Route
}

sealed interface MainTabRoute : Route {
    @Serializable
    data object Map : MainTabRoute

    @Serializable
    data object Archive : MainTabRoute

    @Serializable
    data object MyPage : MainTabRoute
}
