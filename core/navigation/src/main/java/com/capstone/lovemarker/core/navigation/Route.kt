package com.capstone.lovemarker.core.navigation

import kotlinx.serialization.Serializable

sealed interface Route {
    @Serializable
    data object Login : Route

    @Serializable
    data object Nickname : Route

    @Serializable
    data object Detail : Route
}

sealed interface Matching: Route {
    @Serializable
    data object Home : Matching

    @Serializable
    data object Sender : Matching

    @Serializable
    data object Receiver : Matching
}

sealed interface Upload: Route {
    @Serializable
    data object Photo : Upload

    @Serializable
    data object Content : Upload

    @Serializable
    data object PlaceSearch : Upload
}

sealed interface MainTabRoute : Route {
    @Serializable
    data object Map : MainTabRoute

    @Serializable
    data object Archive : MainTabRoute

    @Serializable
    data object MyPage : MainTabRoute
}