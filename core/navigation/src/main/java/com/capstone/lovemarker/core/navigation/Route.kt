package com.capstone.lovemarker.core.navigation

import kotlinx.serialization.Serializable

sealed interface Route {
    /**
     * 초기 설치: Login -> Nickname -> Matching -> Main (Map)
     * 로그아웃: Login -> Main (Map)
     * */
    @Serializable
    data object Login : Route

    @Serializable
    data object Nickname : Route

    @Serializable
    data object Detail : Route
}

sealed interface MatchingRoute: Route {
    @Serializable
    data object Home : MatchingRoute

    @Serializable
    data object Sender : MatchingRoute

    @Serializable
    data object Receiver : MatchingRoute
}

sealed interface UploadRoute: Route {
    @Serializable
    data object Photo : UploadRoute

    @Serializable
    data object Content : UploadRoute

    @Serializable
    data object PlaceSearch : UploadRoute
}

sealed interface MainTabRoute : Route {
    @Serializable
    data object Map : MainTabRoute

    @Serializable
    data object Archive : MainTabRoute

    @Serializable
    data object MyPage : MainTabRoute
}